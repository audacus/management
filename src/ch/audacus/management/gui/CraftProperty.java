package ch.audacus.management.gui;

import ch.audacus.management.core.Property;

@SuppressWarnings("serial")
public class CraftProperty extends ACraftEntity {

	public CraftProperty(final Editor editor) {
		super(editor, new Property());
	}
}
