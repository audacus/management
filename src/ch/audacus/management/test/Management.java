package ch.audacus.management.test;

import java.util.Map;

public class Management extends AEntity {

	protected int id;
	protected String name;

	public Management(final Map<String, ?> map) {
		super(map);
	}

	public Management() {
		super();
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
