package ch.audacus.management.gui;

import ch.audacus.management.core.Thing;

@SuppressWarnings("serial")
public class CraftThing extends ACraftEntity {

	public CraftThing(final Editor editor) {
		super(editor, new Thing());
	}
}
