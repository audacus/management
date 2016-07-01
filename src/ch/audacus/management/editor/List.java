package ch.audacus.management.editor;

import java.sql.SQLException;

import javax.swing.JPanel;

import ch.audacus.management.core.AEntity;
import ch.audacus.management.core.Database;

public class List extends JPanel {

	public List(final AEntity entity) {
		try {
			Database.resultSetToList(entity, Database.getByFields(entity, null));
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
}
