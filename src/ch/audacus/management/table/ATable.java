package ch.audacus.management.table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

	public ATable(final String table, final String primary) {
		this.table = table;
		this.primary = primary;
		this.SELECT += this.table;
		this.DELETE += this.table;
		this.INSERT += this.table;
	}

	public String getName() {
		return this.table;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public ResultSet getAll() throws SQLException {
		final PreparedStatement statement = Database.get().prepareStatement(this.SELECT + ";");
		return statement.executeQuery();
	}

	public ResultSet getByPrimary(final int id) throws SQLException {
		return this.getByField(this.primary, String.valueOf(id));
	}

	@Override
	public ResultSet getByField(final String field, final String value) throws SQLException {
		final PreparedStatement statement = Database.get().prepareStatement(this.SELECT + ATable.WHERE + ";");
		statement.setString(1, field);
		statement.setString(2, value);
		return statement.executeQuery();
	}

	@Override
	public ResultSet insert(final HashMap<String, Object> parameters) throws SQLException {
		final ArrayList<String> fieldsList = new ArrayList<>();
		final ArrayList<Object> valuesList = new ArrayList<>();
		final StringBuilder fields = new StringBuilder();
		final StringBuilder values = new StringBuilder();
		for (final Entry<String, Object> entry : parameters.entrySet()) {
			final String key = entry.getKey();
			if (key != null) {
				fieldsList.add(entry.getKey());
				valuesList.add(entry.getValue());
			}
		}
		final String lastField = fieldsList.remove(fieldsList.size()-1);
		final Object lastValue = valuesList.remove(valuesList.size()-1);
		for (final String field : fieldsList) {
			fields.append(field + ",");
		}
		fields.append(lastField);
		for (final Object value : valuesList) {
			values.append(String.valueOf(value) + ",");
		}
		values.append(lastValue);
		final String insert = this.INSERT + " (" + fields + ") " + ATable.INSERT_VALUES;
		System.out.println(insert);
		final PreparedStatement statement = Database.get().prepareStatement(insert);
		statement.setString(1, values.toString());
		statement.executeUpdate();
		final ResultSet keys = statement.getGeneratedKeys();
		final ResultSet inserted = this.getByPrimary(keys.getInt(keys.getMetaData().getColumnLabel(1)));
		if (inserted.next()) {
			// TODO 2016-09-02: log
		}
		return inserted;
	}

	@Override
	public int deleteByPrimary(final int id) throws SQLException {
		return this.deleteByField(this.primary, String.valueOf(id));
	}

	@Override
	public int deleteByField(final String field, final String value) throws SQLException {
		final PreparedStatement statement = Database.get().prepareStatement(this.DELETE + ATable.WHERE + ";");
		statement.setString(1, field);
		statement.setString(2, value);
		return statement.executeUpdate();
	}

	@Override
	public int deleteAll() throws SQLException {
		final PreparedStatement statement = Database.get().prepareStatement(this.DELETE + ";");
		statement.setString(1, this.table);
		return statement.executeUpdate();
	}
}
