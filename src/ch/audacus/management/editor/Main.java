package ch.audacus.management.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Main extends AView {

	public Main(final Editor editor) {
		super(editor);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridBagLayout());
		// constraints
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		// title
		final JLabel title = new JLabel("Main", SwingConstants.CENTER);
		this.add(title, constraints);
		// manage
		final JButton btnManage = new JButton("manage");
		this.add(btnManage, constraints);
		// craft
		final JButton btnCraft = new JButton("craft");
		btnCraft.addActionListener(e -> {
			this.editor.setView(EView.MENU_CRAFT);
		});
		this.add(btnCraft, constraints);
	}
}
