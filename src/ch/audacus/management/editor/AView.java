package ch.audacus.management.editor;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AView extends JPanel {

	protected Editor editor;

	public AView(final Editor editor) {
		super();
		this.editor = editor;
	}
}
