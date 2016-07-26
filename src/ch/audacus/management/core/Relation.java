package ch.audacus.management.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Relation extends AEntity {

	private int id;
	private String name;

	public Relation() {
		super();
	}

	public Relation(final Map<String, ?> map) {
		super(map);
	}

	public Relation(final ResultSet result) {
		super(result);
	}

	@Override
	public Relation fromId(final int id) {
		final Relation relation = new Relation();
		try {
			final ResultSet result = Database.getByPrimary(this, id);
			if (result.next()) {
				relation.setId(result.getInt(1));
				relation.setName(result.getString(2));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return relation;
	}

	@Override
	public Relation fromResultSet(final ResultSet result) {
		return new Relation(result);
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

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
