package ch.audacus.management.editor;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class Craft extends AView {

	public Craft(final Editor parent) {
		super(parent);
		this.editor.setSize(300, 100);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1, 0, 0, 0));

		final JButton btnManagement = new JButton("management");
		btnManagement.addActionListener(e -> {
			this.editor.setView(EView.CRAFT_MANAGEMENT);
		});
		this.add(btnManagement);

		final JButton btnThing = new JButton("thing");
		this.add(btnThing);

		final JButton btnSomething = new JButton("something");
		this.add(btnSomething);
	}

}
