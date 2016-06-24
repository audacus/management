package ch.audacus.management.test;

import java.util.Map;

public class Relation extends AEntity {

	protected int id;
	protected String name;

	public Relation(final Map<String, ?> map) {
		super(map);
	}

	public Relation() {
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
