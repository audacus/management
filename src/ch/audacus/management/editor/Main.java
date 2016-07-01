package ch.audacus.management.editor;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class Main extends AView {

	public Main(final Editor parent) {
		super(parent);
		this.editor.setSize(300, 100);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1, 0, 0, 0));

		final JButton btnManage = new JButton("manage");
		this.add(btnManage);

		final JButton btnCraft = new JButton("craft");
		btnCraft.addActionListener(e -> {
			this.editor.setView(EView.MENU_CRAFT);
		});
		this.add(btnCraft);
	}
}
