package ch.audacus.management.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Value<V> extends AEntity {

	private int id;
	private Instance instance;
	private Property property;
	private V value;

	public Value() {
		super();
	}

	public Value(final Map<String, ?> map) {
		super(map);
	}

	public Value(final ResultSet result) {
		super(result);
	}

	@Override
	public Value fromId(final int id) {
		final Value value = new Value();
		try {
			final ResultSet result = Database.getByPrimary(this, id);
			if (result.next()) {
				value.setId(result.getInt(1));
				value.setInstance(new Instance().fromId(result.getInt(2)));
				value.setProperty(new Property().fromId(result.getInt(3)));
				value.setValue(result.getObject(4));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return value;
	}

	@Override
	public Map<String, Object> toMap() {
		final Map<String, Object> map = new HashMap<>();
		map.put("id", this.getId());
		map.put("instance", this.getInstance());
		map.put("property", this.getProperty());
		map.put("value", this.getValue());
		return map;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Instance getInstance() {
		return this.instance;
	}

	public void setInstance(final Instance instance) {
		this.instance = instance;
	}

	public Property getProperty() {
		return this.property;
	}

	public void setProperty(final Property property) {
		this.property = property;
	}

	public void setProperty(final int id) {
		final Property property = new Property();
		property.setId(id);
		this.property = property;
	}

	public V getValue() {
		return this.value;
	}

	public void setValue(final V value) {
		this.value = value;
	}
}
