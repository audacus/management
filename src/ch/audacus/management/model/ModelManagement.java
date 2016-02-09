package ch.audacus.management.model;

import ch.audacus.management.table.ATable;
import ch.audacus.management.table.TableManagement;

public class ModelManagement extends AModel {

	public ModelManagement() {
		super(new TableManagement());
	}

}
