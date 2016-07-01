package ch.audacus.management.core;

public class Field<V extends Object> {

	private String name;
	private V value;

	public Field() {}

	public Field(final String name, final V value) {
		this.name = name;
		this.setValue(value);
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public V getValue() {
		return this.value;
	}

	public void setValue(final V value) {
		this.value = value;
	}
}
