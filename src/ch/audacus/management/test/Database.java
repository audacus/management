package ch.audacus.management.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Database {

	private static final String DATABASE_PATH = "db";
	private static final String DATABASE_NAME = "management";
	private static final String DATABASE_FILE_ENDING = "db";
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

	public static ResultSet getByPrimary(final AEntity entity, final int... primaries) {
		final Map<String, String> map = new HashMap<>();
		for (int i = 0; i < primaries.length; i++) {
			if (entity.primaries.length > i) {
				map.put(entity.primaries[i], String.valueOf(primaries[i]));
			}
		}
		return Database.getByFields(map);
	}

	public static ResultSet getByFields(final Map<String, String> fieds) {
		return null;
	}
}
