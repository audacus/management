package ch.audacus.management;

import java.sql.ResultSet;
import java.sql.SQLException;

import ch.audacus.management.table.TableManagement;
import ch.audacus.management.table.TableProperty;
import ch.audacus.management.table.TableThing;

public class Main {

	public static void main(final String[] args) {
		try {
			final TableThing thing = new TableThing();
			final TableProperty property = new TableProperty();
			final TableManagement management = new TableManagement();
			final ResultSet properties = property.getAll();
			final ResultSet things = thing.getAll();
			final ResultSet managements = management.getAll();

			// work
			final Controller controller = new Controller();

			final ResultSet inserted = Controller.createManagement("vehicles");
			while (inserted.next()) {
				System.out.println("here");
			}

			// debug
			System.out.println("\n" + management);
			while (managements.next()) {
				System.out.println(managements.getString("id")+"|"+managements.getString("name"));
			}
			System.out.println("\n" + thing);
			while (things.next()) {
				System.out.println(things.getString("id")+"|"+things.getString("name"));
			}
			System.out.println("\n" + property);
			while (properties.next()) {
				System.out.println(properties.getString("id")+"|"+properties.getString("name"));
			}

			Database.save();
		} catch (final SQLException e) {
			e.printStackTrace();
		}

		/**
		 * TODO general:
		 * [ ] model with table as child or directly on model -> model.persist() => abstract model.persist();
		 *
		 * TODO database:
		 * [x] read in schema
		 * [x] read in data
		 * [?] check if schema and data is already read in
		 * [ ] save queries executed since offline in change file to submit changes to server and not whole database (base64?) -> log on (first check result e.g. insert: if id 0 => not inserted)
		 * [ ] dump database on save (data & schema)
		 * [ ] save whole db on server
		 * [ ] recreate db from server back up
		 * [ ] history of all queries on the server and/or local
		 * [ ] reverting database with history (revisions? => svn)
		 *
		 * TODO craft:
		 * [ ] possibility to create management
		 * [ ] possibility to create thing
		 * [ ] possibility to define properties of thing with things
		 * [ ] define basic things: numberfield, textfield, textarea, dropdown
		 * [ ] if property value is a thing with multiple instances -> possibility to make 1:1 relations -> choose one instance (dropdown or submenu/list) -> e.g. car -> driver
		 * [ ] if property value is a thing with multiple instances -> possibility to make 1:m relations -> choose multiple instances (dropdown or submenu/list) -> e.g. garage -> car
		 *
		 * TODO manage:
		 * [ ] dynamically create forms, list, overview etc from database evaluation
		 */
	}
}
