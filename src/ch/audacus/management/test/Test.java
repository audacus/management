package ch.audacus.management.test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void main(final String[] args) {
		try {
			ResultSet result = Database.getByFields(new Management(), null);
			while (result.next()) {
				new Management(result).toMap().forEach((key, value) -> System.out.println(key + " " + value));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//		int id = 1;
		//		Thing thing = null;
		//		while ((thing = new Thing().fromId(id)).getName() != null) {
		//			System.out.println("properties from " + thing.getName());
		//			thing.getProperties().forEach(property -> System.out.println(property + "|" + property.getName()));
		//			id++;
		//		}
		//		final Instance tmp = new Instance();
		//		tmp.setManagement(1);
		//		tmp.setThing(2);
		//		try {
		//			Database.persist(tmp);
		//		} catch (final SQLException e) {
		//			e.printStackTrace();
		//		}
	}
}
