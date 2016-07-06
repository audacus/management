package ch.audacus.management.editor;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;

public class Craft extends AView {

	public Craft(final Editor editor) {
		super(editor);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// management
		final JButton btnManagement = new JButton("management");
		btnManagement.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnManagement.addActionListener(e -> {
			this.editor.setView(EView.CRAFT_MANAGEMENT);
		});
		this.add(btnManagement);
		// thing
		final JButton btnThing = new JButton("thing");
		btnThing.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(btnThing);
		// TODO: what is this?
		// something
		final JButton btnSomething = new JButton("something");
		btnSomething.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(btnSomething);
		// back
		final JButton btnBack = new JButton("back..");
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBack.addActionListener(e -> {
			this.editor.back();
		});
		this.add(btnBack);
	}
}
