package ch.audacus.management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ch.audacus.management.table.*;

public class Controller {
	
	private static Instance instance;
	private static Management management;
	private static Property property;
	private static Relation relation;
	private static Thing thing;
	private static Value value;
	
	public static ResultSet createManagement(final String name) throws SQLException {
		if (Controller.management == null) {
			Controller.initManagement();
		}
		return Controller.management.insert(new HashMap<String, Object>(){{
			put("name", name);
		}});
	}
	
//	public static ResultSet createPropety()
	
	private static void initInstance() {
		Controller.instance = new Instance();
	}

	private static void initManagement() {
		Controller.management = new Management();
	}
	
	private static void initProperty() {
		Controller.property = new Property();
	}
	
	private static void initRelation() {
		Controller.relation = new Relation();
	}
	
	private static void initThing() {
		Controller.thing = new Thing();
	}
	
	private static void initValue() {
		Controller.value = new Value();
	}
}
