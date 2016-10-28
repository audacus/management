package ch.audacus.management.gui;

import ch.audacus.management.core.Management;

@SuppressWarnings("serial")
public class CraftManagement extends ACraftEntity {

	public CraftManagement(final Editor editor) {
		super(editor, new Management());
	}
}
