package ch.audacus.management.test;

import java.util.Map;

public class Property extends AEntity {

	protected int id;
	protected Thing thing;
	protected Thing type;
	protected Relation relation;
	protected String name;

	public Property(final Map<String, ?> map) {
		super(map);
	}

	public Property() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Thing getThing() {
		return this.thing;
	}

	public void setThing(final Thing thing) {
		this.thing = thing;
	}

	public Thing getType() {
		return this.type;
	}

	public void setType(final Thing type) {
		this.type = type;
	}

	public Relation getRelation() {
		return this.relation;
	}

	public void setRelation(final Relation relation) {
		this.relation = relation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
