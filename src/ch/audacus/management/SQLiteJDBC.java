package ch.audacus.management;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteJDBC {

	private static final String DATABASE_PATH = "db";
	private static final String DATABASE_NAME = "database";
	private File path;
	private String name;
	private File database;
	private Connection connection;

	public SQLiteJDBC() throws Exception {
		this(SQLiteJDBC.DATABASE_NAME, SQLiteJDBC.DATABASE_PATH);
	}

	public SQLiteJDBC(final String databaseName) throws Exception {
		this(databaseName, SQLiteJDBC.DATABASE_PATH);
	}

	public SQLiteJDBC(final String databaseName, final String databasePath) throws Exception {
		this(databaseName, new File(databasePath));
	}

	public SQLiteJDBC(final String databaseName, final File databasePath) throws Exception {
		this.path = databasePath;
		this.name = databaseName;
		this.init(this.path, this.name);
	}

	public Connection init() throws Exception {
		return this.init(new File(SQLiteJDBC.DATABASE_PATH), SQLiteJDBC.DATABASE_NAME);
	}

	public Connection init(final String databasePath, final String databaseName) throws Exception {
		return this.init(new File(databasePath), databaseName);
	}

	public Connection init(final File databasePath, final String databaseName) throws Exception {
		if (!databasePath.isDirectory()) {
			if (!databasePath.mkdir()) {
				throw new Exception("failed to create directory: " + databasePath.getPath());
			}
		}
		this.database = new File(databasePath.getPath(), new File(databaseName).getPath() + "." + SQLiteJDBC.DATABASE_PATH);
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database.getPath());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		System.out.println("opened connection: " + this.database.getPath() + "\n");
		return this.connection;
	}

	public Connection get() {
		return this.connection;
	}

	public File getFile() {
		return this.database;
	}

	public File getPath() {
		return this.path;
	}

	public SQLiteJDBC setPath(final File databasePath) {
		this.path = databasePath;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public SQLiteJDBC setName(final String databaseName) {
		this.name = databaseName;
		return this;
	}
}
