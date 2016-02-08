package ch.audacus.management.table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ch.audacus.management.Database;

public abstract class ATable implements ITable {

	protected static final String WHERE = " where ? = ?";
	protected static final String INSERT_VALUES = " values (?)";
	protected String SELECT = "select * from ";
	protected String DELETE = "delete from ";
	protected String INSERT = "insert or ignore into ";

	protected String table;
	protected String primary;

	public ATable(String table, String primary) {
		this.table = table;
		this.primary = primary;
		this.SELECT += this.table;
		this.DELETE += this.table;
		this.INSERT += this.table;
	}
	
	@Override
	public String getName() {
		return this.table;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public ResultSet getAll() throws SQLException {
		PreparedStatement statement = Database.get().prepareStatement(SELECT + ";");
		return statement.executeQuery();
	}

	@Override
	public ResultSet getByPrimary(int id) throws SQLException {
		return getByField(this.primary, String.valueOf(id));
	}

	@Override
	public ResultSet getByField(String field, String value) throws SQLException {
		PreparedStatement statement = Database.get().prepareStatement(SELECT + WHERE + ";");
		statement.setString(1, field);
		statement.setString(2, value);
		return statement.executeQuery();
	}

	@Override
	public ResultSet insert(HashMap<String, Object> parameters) throws SQLException {
		ArrayList<String> fieldsList = new ArrayList<>();
		ArrayList<Object> valuesList = new ArrayList<>();
		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for (final Entry<String, Object> entry : parameters.entrySet()) {
			String key = entry.getKey();
			if (key != null) {
				fieldsList.add(entry.getKey());
				valuesList.add(entry.getValue());
			}
		}
		String lastField = fieldsList.remove(fieldsList.size()-1);
		Object lastValue = valuesList.remove(valuesList.size()-1);
		for (String field : fieldsList) {
			fields.append(field + ",");
		}
		fields.append(lastField);
		for (Object value : valuesList) {
			values.append(String.valueOf(value) + ",");
		}
		values.append(lastValue);
		String insert = this.INSERT + " (" + fields + ") " + ATable.INSERT_VALUES;
		System.out.println(insert);
		PreparedStatement statement = Database.get().prepareStatement(insert);
		statement.setString(1, values.toString());
		statement.executeUpdate();
		ResultSet keys = statement.getGeneratedKeys();
		ResultSet inserted = this.getByPrimary(keys.getInt(keys.getMetaData().getColumnLabel(1)));
		if (inserted.next()) {
			// TODO 2016-09-02: log
		}
		return inserted;
	}

	@Override
	public int deleteByPrimary(int id) throws SQLException {
		return deleteByField(this.primary, String.valueOf(id));
	}

	@Override
	public int deleteByField(String field, String value) throws SQLException {
		PreparedStatement statement = Database.get().prepareStatement(DELETE + WHERE + ";");
		statement.setString(1, field);
		statement.setString(2, value);
		return statement.executeUpdate();
	}

	@Override
	public int deleteAll() throws SQLException {
		PreparedStatement statement = Database.get().prepareStatement(DELETE + ";");
		statement.setString(1, this.table);
		return statement.executeUpdate();
	}
}
