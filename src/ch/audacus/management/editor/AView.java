package ch.audacus.management.editor;

import javax.swing.JPanel;

public abstract class AView extends JPanel {

	protected Editor editor;

	public AView(final Editor parent) {
		this.editor = parent;
	}
}
