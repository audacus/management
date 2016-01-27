package ch.audacus.management;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteJDBC {
	
	private static final String DEFAULT_DATABASE_PATH = "db";
	private static final String DEFAULT_DATABASE_NAME = "database";
	private File path;
	private String name;
	private Connection connection;
	
	public SQLiteJDBC() {
		this(DEFAULT_DATABASE_NAME, DEFAULT_DATABASE_PATH);
	}
	
	public SQLiteJDBC(String databaseName) {
		this(databaseName, DEFAULT_DATABASE_PATH);
	}
	
	public SQLiteJDBC(String databaseName, String databasePath) {
		this(databaseName, new File(databasePath));
	}
	
	public SQLiteJDBC(String databaseName, File databasePath) {
		path = databasePath; 
		name = databaseName;
		init();
	}
	
	public Connection init() {
		return init(new File(DEFAULT_DATABASE_PATH), DEFAULT_DATABASE_NAME);
	}
	
	public Connection init(String databasePath, String databaseName) {
		return init(new File(databasePath), databaseName);
	}
	
	public Connection init(File databasePath, String databaseName) {
		if (!databasePath.isDirectory()) {
			databasePath.mkdir();
		}
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"+databasePath.getPath()+File.separator+databaseName+".db");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public Connection get() {
		return connection;
	}
		
	public String getPath() {
		return path.getAbsolutePath();
	}
	
	public SQLiteJDBC setPath(File databasePath) {
		path = databasePath;
		return this;
	}

	public String getName() {
		return name;
	}
	
	public SQLiteJDBC setName(String databaseName) {
		name = databaseName;
		return this;
	}
}
