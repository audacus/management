package ch.audacus.management.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Management extends AEntity {

	private int id;
	private String name;

	public Management() {
		super();
	}

	public Management(final Map<String, ?> map) {
		super(map);
	}

	public Management(final ResultSet result) {
		super(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Management fromId(final int id) {
		final Management management = new Management();
		try {
			final ResultSet result = Database.getByPrimary(this, id);
			if (result.next()) {
				management.setId(result.getInt(1));
				management.setName(result.getString(2));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return management;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Management fromResultSet(final ResultSet result) {
		return new Management(result);
	}

	@Override
	public Map<String, ? extends Object> toMap() {
		final Map<String, ? super Object> map = new HashMap<>();
		map.put("id", this.getId());
		map.put("name", this.getName());
		return map;
	}

	@Override
	public Map<String, ? extends Object> toPersistMap() {
		return this.toMap();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
