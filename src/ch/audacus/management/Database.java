package ch.audacus.management;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private static final String DATABASE_NAME = "management";
	private static final String SQL = "sql";
	private static final String SQL_SCHEMA = "schema";
	private static final String SQL_DATA = "data";
	private static final String SQL_HISTORY = "history";
	private static final char NAME_SEPARATOR = '-';
	private static SQLiteJDBC sqlite;
	private static Connection database;

	public static Connection get() {
		if (Database.sqlite == null) {
			Database.init();
		}
		return Database.sqlite.get();
	}

	private static SQLiteJDBC init() {
		try {
			Database.sqlite = new SQLiteJDBC(Database.DATABASE_NAME);
			Database.database = Database.get();
			if (Database.database.getSchema() == null) {
				Database.executeSchema();
				Database.executeData();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return Database.sqlite;
	}

	public static String getName() {
		if (Database.sqlite == null) {
			Database.init();
		}
		return Database.sqlite.getName();
	}

	public static File getPath() {
		if (Database.sqlite == null) {
			Database.init();
		}
		return Database.sqlite.getPath();
	}

	public static File getFile() {
		if (Database.sqlite == null) {
			Database.init();
		}
		return Database.sqlite.getFile();
	}

	private static void executeSchema() throws Exception {
		Database.readSqlFile(Database.SQL_SCHEMA);
	}

	private static void executeData() throws Exception {
		Database.readSqlFile(Database.SQL_DATA);
	}

	private static void readSqlFile(final String type) throws Exception {
		final File files = new File(Database.sqlite.getPath(), new File(Database.SQL, type).getPath());
		if (!files.isDirectory()) {
			if (!files.mkdirs()) {
				throw new Exception("failed to create directories: " + files.getPath());
			}
		}

		for (final File file : files.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(final File dir, final String name) {
				return name.startsWith(type + Database.NAME_SEPARATOR + Database.DATABASE_NAME) && name.endsWith("." + Database.SQL);
			}
		})) {
			Database.executeSql(file);
		}
	}

	private static void executeSql(final File sql) {
		System.out.println("execute: " + sql.getPath());
		try {
			Database.executeSql(new String(Files.readAllBytes(sql.toPath()), StandardCharsets.UTF_8));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private static void executeSql(final String sql) {
		//		System.out.println("execute: \n" + sql);
		try {
			final Statement statement = Database.database.createStatement();
			statement.executeUpdate(sql);
			System.out.println("executed successfully\n");
		} catch (final SQLException e) {
			System.out.println("could not execute: " + e.getMessage() + "\n");
		}
	}
}
