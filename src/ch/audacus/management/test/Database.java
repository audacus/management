package ch.audacus.management.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Database {

	private static final String DATABASE_PATH = "db";
	private static final String DATABASE_NAME = "management";
	private static final String DATABASE_FILE_ENDING = "db";

	private static final String SELECT = "select * from %s where 1 = 1";
	private static final String SELECT_WHERE = " and (%s);";
	private static final String INSERT = "insert or ignore into %s (%s) values (%s);";

	private static Connection connection;

	public static Connection init() {
		final File path = new File(Database.DATABASE_PATH);
		if (!path.isDirectory()) {
			if (!path.mkdir()) {
				new Exception("failed to create directory: " + path.getPath()).printStackTrace();
			}
		}
		final File database = new File(path.getPath(), new File(Database.DATABASE_NAME).getPath() + "." + Database.DATABASE_FILE_ENDING);
		try {
			Class.forName("org.sqlite.JDBC");
			Database.connection = DriverManager.getConnection("jdbc:sqlite:" + database.getPath());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		System.out.println("opened connection: " + database.getPath() + "\n");
		return Database.connection;
	}

	public static Connection get() {
		if (Database.connection == null) {
			Database.init();
		}
		return Database.connection;
	}

	public static ResultSet getByPrimary(final AEntity entity, final int... primaries) throws SQLException {
		final List<Field> list = new ArrayList<>();
		for (int i = 0; i < primaries.length; i++) {
			if (entity.primaries.length > i) {
				list.add(new Field<Integer>(entity.primaries[i], new Integer(primaries[i])));
			}
		}
		return Database.getByFields(entity, list);
	}

	public static ResultSet getByFields(final AEntity entity, final List<Field> fields) throws SQLException {
		PreparedStatement statement = null;
		// set up select string with table name
		final StringBuilder sql = new StringBuilder(String.format(Database.SELECT, entity.table));
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
				final Field field = fields.get(i - 1);
				final Object value = field.getValue();
				if (value instanceof Integer || value instanceof AEntity) {
					// number
					statement.setInt(i, Integer.parseInt(String.valueOf(value)));
				} else {
					// everything else
					statement.setObject(i, value);
				}
			}
		} else {
			// execute select without wheres
			statement = Database.get().prepareStatement(sql.toString());
		}
		return statement.executeQuery();
	}

	public static ResultSet persist(final AEntity entity) throws SQLException {
		final Map<String, Object> map = entity.toMap();
		final PreparedStatement statement = null;
		// set up insert string with table and fields
		map.keySet().forEach(e -> System.out.println(e));
		final StringBuilder sql = new StringBuilder(String.format(Database.INSERT, new Object[] { 
				entity.table, 
				map.keySet().stream().collect(Collectors.joining(", ")), 
				map.values().stream().collect(Collectors.toList(e -> e = "?")).stream().collect(Collectors.joining(", ")) 
		}));

		System.out.println(sql);

		//		statement.executeUpdate();
		//		final ResultSet keys = statement.getGeneratedKeys();
		//		final ResultSet inserted = Database.getByPrimary(entity, keys.getInt(keys.getMetaData().getColumnLabel(1)));
		//		if (inserted.next()) {
		//			// TODO 2016-09-02: log
		//		}
		return null;
	}

	public static List<ResultSet> persist(final AEntity... entities) throws SQLException {
		return Arrays.stream(entities).collect(Collectors.toList(e -> Database.persist(e)));
	}
}
