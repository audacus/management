package ch.audacus.management.editor;

import ch.audacus.management.core.Management;

public class CraftManagement extends ACraftEntity {

	public CraftManagement(final Editor editor) {
		super(editor, new Management());
	}
}
