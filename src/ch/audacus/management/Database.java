package ch.audacus.management;

import java.sql.Connection;

public class Database {
	
	private static final String DATABASE_NAME = "management";
	
	private static SQLiteJDBC database;
	
	public static Connection get() {
		if (database == null) {
			init();
		}
		return database.get();
	}
	
	private static SQLiteJDBC init() {
		database = new SQLiteJDBC(DATABASE_NAME);
		return database;
	}
	
	public static String getName() {
		if (database == null) {
			init();
		}
		return database.getName();
	}
	
	public static String getPath() {
		if (database == null) {
			init();
		}
		return database.getPath();
	}
}
