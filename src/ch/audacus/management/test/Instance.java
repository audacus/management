package ch.audacus.management.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Instance extends AEntity {

	private int id;
	private Management management;
	private Thing thing;

	public Instance() {
		super();
	}

	public Instance(final Map<String, ?> map) {
		super(map);
	}

	public Instance(final ResultSet result) {
		super(result);
	}

	@Override
	public Instance fromId(final int id) {
		final Instance instance = new Instance();
		try {
			final ResultSet result = Database.getByPrimary(this, id);
			if (result.next()) {
				instance.setId(result.getInt(1));
				instance.setManagement(new Management().fromId(result.getInt(2)));
				instance.setThing(new Thing().fromId(result.getInt(3)));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return instance;
	}

	@Override
	public Map<String, Object> toMap() {
		final Map<String, Object> map = new HashMap<>();
		map.put("id", this.getId());
		map.put("management", this.getManagement());
		map.put("thing", this.getThing());
		return map;
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

	public void setManagement(final int id) {
		final Management management = new Management();
		management.setId(id);
		this.management = management;
	}

	public Thing getThing() {
		return this.thing;
	}

	public void setThing(final AEntity thing) {
		this.thing = (Thing) thing;
	}

	public void setThing(final int id) {
		final Thing thing = new Thing();
		thing.setId(id);
		this.thing = thing;
	}
}
