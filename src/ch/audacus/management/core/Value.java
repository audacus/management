package ch.audacus.management.core;

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

	@SuppressWarnings("unchecked")
	@Override
	public Value<?> fromId(final int id) {
		final Value<Object> value = new Value<>();
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

	@SuppressWarnings("unchecked")
	@Override
	public Value<?> fromResultSet(final ResultSet result) {
		return new Value<>(result);
	}

	@Override
	public Map<String, ? extends Object> toMap() {
		final Map<String, ? super Object> map = new HashMap<>();
		map.put("id", this.getId());
		map.put("instance", this.getInstance());
		map.put("property", this.getProperty());
		map.put("value", this.getValue());
		return map;
	}

	@Override
	public Map<String, ? extends Object> toPersistMap() {
		return this.toMap();
	}

	@Override
	public String getName() {
		return this.property.getThing().getName() + "::" + this.property.getName();
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
