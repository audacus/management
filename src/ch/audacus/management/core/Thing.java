package ch.audacus.management.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Thing extends AEntity {

	private int id;
	private String name;

	public Thing() {
		super();
	}

	public Thing(final Map<String, ?> map) {
		super(map);
	}

	public Thing(final ResultSet result) {
		super(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Thing fromId(final int id) {
		final Thing thing = new Thing();
		try {
			final ResultSet result = Database.getByPrimary(this, id);
			if (result.next()) {
				thing.setId(result.getInt(1));
				thing.setName(result.getString(2));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return thing;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Thing fromResultSet(final ResultSet result) {
		return new Thing(result);
	}

	@Override
	public Map<String, ? extends Object> toMap() {
		final Map<String, ? super Object> map = new HashMap<>();
		map.put("id", this.getId());
		map.put("name", this.getName());
		map.put("properties", this.getProperties());
		return map;
	}

	@Override
	public Map<String, ? extends Object> toPersistMap() {
		final Map<String, ? super Object> map = new HashMap<>();
		map.put("id", this.getId());
		map.put("name", this.getName());
		return map;
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

	// additional
	public List<Property> getProperties() {
		final List<Property> properties = new ArrayList<>();
		final List<Field<?>> fields = new ArrayList<>();
		fields.add(new Field<>("thing", this));
		try {
			final ResultSet result = Database.getByFields(new Property(), fields);
			while (result.next()) {
				properties.add(new Property(result));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
