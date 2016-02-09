package ch.audacus.management;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ch.audacus.management.table.*;

public class Controller {
	
	private static TableInstance instance;
	private static TableManagement management;
	private static TableProperty property;
	private static TableRelation relation;
	private static TableThing thing;
	private static TableValue value;
	
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
		Controller.instance = new TableInstance();
	}

	private static void initManagement() {
		Controller.management = new TableManagement();
	}
	
	private static void initProperty() {
		Controller.property = new TableProperty();
	}
	
	private static void initRelation() {
		Controller.relation = new TableRelation();
	}
	
	private static void initThing() {
		Controller.thing = new TableThing();
	}
	
	private static void initValue() {
		Controller.value = new TableValue();
	}
}
