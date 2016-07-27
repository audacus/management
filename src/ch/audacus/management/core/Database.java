package ch.audacus.management.core;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Database {

	private static final String DATABASE_PATH = "db";
	private static final String DATABASE_NAME = "management";
	private static final String DATABASE_FILE_ENDING = "db";
	private static final String SQL = "sql";
	private static final String SQL_SCHEMA = "schema";
	private static final String SQL_DATA = "data";
	private static final char NAME_SEPARATOR = '-';

	private static final String SELECT = "select * from %s where 1 = 1";
	private static final String SELECT_WHERE = " and (%s);";
	private static final String INSERT = "insert or replace into %s (%s) values (%s);";

	private static Connection connection;

	public static void init() {
		final File path = new File(Database.DATABASE_PATH);
		if (!path.isDirectory()) {
			if (!path.mkdirs()) {
				new Exception("failed to create directory: " + path.getPath()).printStackTrace();
			}
		}
		try {
			final File database = new File(path, new File(Database.DATABASE_NAME).getPath() + "." + Database.DATABASE_FILE_ENDING);
			if (!database.exists()) {
				if (!database.createNewFile()) {
					new Exception("failed to create file: " + database.getPath()).printStackTrace();
				}
			}
			Class.forName("org.sqlite.JDBC");
			Database.connection = DriverManager.getConnection("jdbc:sqlite:" + database.getPath());
			System.out.println("opened connection: " + database.getPath() + "\n");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection get() {
		if (Database.connection == null) {
			Database.init();
			try {
				// try to create a statement -> if fail -> no tables/schema
				final PreparedStatement statement = Database.connection.prepareStatement("SELECT * FROM sqlite_master WHERE type='table';");
				statement.closeOnCompletion();
				final ResultSet result = statement.executeQuery();
				// when no table is in the database -> setup
				if (!result.next()) {
					System.out.println("setup db");
					Database.executeSchema();
					Database.executeData();
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return Database.connection;
	}

	public static ResultSet getByPrimary(final Class<? extends AEntity> clazz, final Integer... primaries) throws SQLException {
		ResultSet result = null;
		try {
			result = Database.getByPrimary(clazz.newInstance(), primaries);
		} catch (final InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ResultSet getByPrimary(final AEntity entity, final Integer... primaries) throws SQLException {
		final List<Field<?>> list = new ArrayList<>();
		for (int i = 0; i < primaries.length; i++) {
			if (entity.primaries.length > i) {
				list.add(new Field<>(entity.primaries[i], primaries[i]));
			}
		}
		return Database.getByFields(entity, list);
	}

	public static ResultSet getByFields(final Class<? extends AEntity> clazz, final List<Field<?>> fields) throws SQLException {
		ResultSet result = null;
		try {
			result = Database.getByFields(clazz.newInstance(), fields);
		} catch (final InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ResultSet getByFields(final AEntity entity, final List<Field<?>> fields) throws SQLException {
		PreparedStatement statement = null;
		// set up select string with table name
		final StringBuilder sql = new StringBuilder(String.format(Database.SELECT, entity.table));
		if (fields != null) {
			// add wheres
			final int fieldssize = fields.size();
			if (fieldssize > 0) {
				// append wheres
				final List<String> wheres = new ArrayList<>();
				StringBuilder where = null;
				// create where strings
				//			for (final Field<?> field : fields) {
				for (final Field<?> field : fields) {
					where = new StringBuilder();
					where.append(field.getName());
					final Object value = field.getValue();
					if (value instanceof String) {
						// string
						where.append(" like '%?%'");
					} else if (value instanceof Integer || value instanceof AEntity) {
						// number
						where.append(" = ?");
					} else {
						// everything else
						where.append(" = '?'");
					}
					wheres.add(where.toString());
				}
				// add wheres to sql
				sql.append(String.format(Database.SELECT_WHERE, String.join(" and ", wheres)));
				// add values
				statement = Database.get().prepareStatement(sql.toString());
				for (int i = 1; i <= fieldssize; i++) {
					final Field<?> field = fields.get(i - 1);
					final Object value = field.getValue();
					if (value instanceof Integer || value instanceof AEntity) {
						// number
						statement.setInt(i, Integer.parseInt(String.valueOf(value)));
					} else {
						// everything else
						statement.setObject(i, value);
					}
				}
			}
		} else {
			// execute select without wheres
			statement = Database.get().prepareStatement(sql.toString());
		}
		statement.closeOnCompletion();
		return statement.executeQuery();
	}

	public static List<? extends AEntity> resultSetToList(final Class<? extends AEntity> clazz, final ResultSet result) throws SQLException {
		List<? extends AEntity> list = null;
		try {
			list = Database.resultSetToList(clazz.newInstance(), result);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<? extends AEntity> resultSetToList(final AEntity entity, final ResultSet result) throws SQLException {
		final List<? extends AEntity> list = new ArrayList<>();
		while (result.next()) {
			list.add(entity.fromResultSet(result));
		}
		return list;
	}

	public static ResultSet persist(final AEntity entity) throws SQLException {
		final Map<String, ? extends Object> map = entity.toPersistMap();
		System.out.println("persist: " + map);
		final List<Object> values = new ArrayList<>(map.values());
		PreparedStatement statement = null;
		// set up insert string with table and fields
		final StringBuilder sql = new StringBuilder(String.format(Database.INSERT,
				new Object[] {
						entity.table,
						map.keySet().stream().collect(Collectors.joining(", ")),
						map.values().stream().map(e -> { return "?"; }).collect(Collectors.joining(", ")) }));

		// add values
		statement = Database.get().prepareStatement(sql.toString());
		statement.closeOnCompletion();

		// do not allow insert of primary with value 0
		for (int i = 1; i <= values.size(); i++) {
			Object value = values.get(i - 1);
			final String field = map.keySet().toArray(new String[0])[i - 1];
			if (value instanceof Integer
					&& value.equals(new Integer(0))
					&& Arrays.stream(entity.primaries).collect(Collectors.toList()).contains(field)) {
				value = null;
			}
			statement.setObject(i, value);
		}
		// execute insert
		statement.executeUpdate();
		// get inserted keys
		final ResultSet keys = statement.getGeneratedKeys();
		final List<Integer> generated = new ArrayList<>();
		while (keys.next()) {
			generated.add(keys.getInt(keys.getMetaData().getColumnLabel(1)));
		}
		final ResultSet inserted = Database.getByPrimary(entity, generated.stream().toArray(Integer[]::new));
		if (inserted.next()) {
			// TODO 2016-02-09: log
		}
		return inserted;
	}

	public static List<ResultSet> persist(final AEntity... entities) throws SQLException {
		return Arrays.stream(entities).map(entity -> {
			ResultSet result = null;
			try {
				result = Database.persist(entity);
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			return result;
		}).collect(Collectors.toList());
	}

	private static void executeSchema() {
		Database.readSqlFile(Database.SQL_SCHEMA);
	}

	private static void executeData() {
		Database.readSqlFile(Database.SQL_DATA);
	}

	private static void readSqlFile(final String type) {
		final File files = new File(Database.DATABASE_PATH, new File(Database.SQL, type).getPath());
		if (!files.isDirectory()) {
			if (!files.mkdirs()) {
				try {
					throw new Exception("failed to create directories: " + files.getPath());
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		Arrays.stream(files
				.listFiles(
						(FilenameFilter) (dir, name) ->
						// condition
						name.startsWith(type + Database.NAME_SEPARATOR + Database.DATABASE_NAME)
						&& name.endsWith("." + Database.SQL)))
		.forEach(file -> Database.executeSql(file));
	}

	private static void executeSql(final File sql) {
		try {
			Database.executeSql(new String(Files.readAllBytes(sql.toPath()), StandardCharsets.UTF_8));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private static void executeSql(final String sql) {
		//		System.out.println("execute: \n" + sql);
		try {
			Database.get().createStatement().executeUpdate(sql);
			System.out.println("sql executed successfully");
		} catch (final SQLException e) {
			System.err.println("sql could not be executed");
			//			e.printStackTrace();
		}
	}
}
