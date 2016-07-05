package ch.audacus.management.editor;

import java.awt.GridLayout;

import javax.swing.border.EmptyBorder;

import ch.audacus.management.core.Management;

public class CraftManagement extends AView {

	public CraftManagement(final Editor parent) {
		super(parent);
		this.editor.setSize(300, 100);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridLayout(1, 0, 0, 0));

		this.add(new ItemList(Management.class));


	}
}
