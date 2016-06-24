package ch.audacus.management.test;

import java.util.Map;

public class Instance extends AEntity {

	protected int id;
	protected Management management;
	protected Thing thing;

	public Instance(final Map<String, ?> map) {
		super(map);
	}

	public Instance() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Management getManagement() {
		return this.management;
	}

	public void setManagement(final Management management) {
		this.management = management;
	}

	public Thing getThing() {
		return this.thing;
	}

	public void setThing(final Thing thing) {
		this.thing = thing;
	}
}
