package ch.audacus.management.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Property extends AEntity {

	private int id;
	private Thing thing;
	private Thing type;
	private Relation relation;
	private String name;

	public Property() {
		super();
	}

	public Property(final Map<String, ?> map) {
		super(map);
	}

	public Property(final ResultSet result) {
		super(result);
	}

	@Override
	public Property fromId(final int id) {
		final Property property = new Property();
		try {
			final ResultSet result = Database.getByPrimary(this, id);
			if (result.next()) {
				property.setId(3);
				property.setThing(new Thing().fromId(result.getInt(2)));
				property.setType(new Thing().fromId(result.getInt(3)));
				property.setRelation(new Relation().fromId(result.getInt(4)));
				property.setName(result.getString(5));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return property;
	}

	@Override
	public Map<String, Object> toMap() {
		final Map<String, Object> map = new HashMap<>();
		map.put("id", this.getId());
		map.put("thing", this.getThing());
		map.put("type", this.getType());
		map.put("relation", this.getRelation());
		map.put("name", this.getName());
		return map;
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

	public void setThing(final int id) {
		final Thing thing = new Thing();
		thing.setId(id);
		this.thing = thing;
	}

	public Thing getType() {
		return this.type;
	}

	public void setType(final Integer id) {
		final Thing type = new Thing();
		type.setId(id.intValue());
		this.type = type;
	}

	public void setType(final Thing type) {
		this.type = type;
	}

	public void setType(final int id) {
		final Thing type = new Thing();
		type.setId(id);
		this.type = type;
	}

	public Relation getRelation() {
		return this.relation;
	}

	public void setRelation(final Relation relation) {
		this.relation = relation;
	}

	public void setRelation(final int id) {
		final Relation relation = new Relation();
		relation.setId(id);
		this.relation = relation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
