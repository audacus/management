package ch.audacus.management.editor;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import ch.audacus.management.core.Management;

public class CraftManagement extends AView {

	public CraftManagement(final Editor editor) {
		super(editor);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// items
		this.add(new ItemList(editor, EView.CRAFT_MANAGEMENT, new Management()));
		// back
		final JButton btnBack = new JButton("back..");
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBack.addActionListener(e -> {
			this.editor.back();
		});
		this.add(btnBack);
	}
}
