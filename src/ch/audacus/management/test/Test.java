package ch.audacus.management.test;

import java.sql.SQLException;

public class Test {

	public static void main(final String[] args) {
		//		final int id = 1;
		//		final Thing thing = null;
		//		while ((thing = new Thing().fromId(id)).getName() != null) {
		//			System.out.println("properties from " + thing.getName());
		//			thing.getProperties().forEach(property -> System.out.println(property + "|" + property.getName()))	;
		//			id++;
		//		}
		final Instance tmp = new Instance();
		tmp.setManagement(1);
		tmp.setThing(2);
		try {
			Database.persist(tmp);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
}
