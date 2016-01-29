package ch.audacus.management;

public class Main {
	
	public static void main(String[] args) {
		System.out.println(Database.getPath());
		/**
		 * TODO database:
		 * [ ] read in schema
		 * [ ] read in data
		 * [ ] check if schema and data is already read in
		 * [ ] save queries executed since offline in change file to submit changes to server and not whole database (base64?)
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
