package ch.audacus.management.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Craft extends AView {

	public Craft(final Editor editor) {
		super(editor);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridBagLayout());
		// constraints
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		// title
		final JLabel title = new JLabel("Craft", SwingConstants.HORIZONTAL);
		this.add(title, constraints);
		// management
		final JButton btnManagement = new JButton("management");
		btnManagement.addActionListener(e -> {
			this.editor.setView(EView.CRAFT_MANAGEMENT);
		});
		this.add(btnManagement, constraints);
		// thing
		final JButton btnThing = new JButton("thing");
		btnThing.addActionListener(e -> {
			this.editor.setView(EView.CRAFT_THING);
		});
		this.add(btnThing, constraints);
		// back
		final JButton btnBack = new JButton("back..");
		btnBack.addActionListener(e -> {
			this.editor.back();
		});
		this.add(btnBack, constraints);
	}
}
