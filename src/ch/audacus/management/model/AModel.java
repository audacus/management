package ch.audacus.management.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import ch.audacus.management.table.ATable;

public abstract class AModel {

	protected ATable table;
	protected HashMap<String, Object> properties;

	public AModel(final ATable table) {
		this.table = table;
	}

	public ResultSet persist() throws SQLException {
		return this.table.insert(this.properties);
	}

	public Object get(final String field) {
		return this.properties.get(field);
	}

	public AModel set(final String field, final Object value) {
		this.properties.put(field, value);
		return this;
	}
}
