package ch.audacus.management.test;

import java.util.Map;

public class Value extends AEntity {

	protected int id;
	protected Instance instance;
	protected Property property;
	protected String value;

	public Value(final Map<String, ?> map) {
		super(map);
	}

	public Value() {
		super();
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

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}
}
