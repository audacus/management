package ch.audacus.management.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import ch.audacus.management.core.AEntity;

@SuppressWarnings("serial")
public abstract class ACraftEntity extends AView {

	protected AEntity entity;

	public ACraftEntity(final Editor editor, final AEntity entity) {
		super(editor);
		this.entity = entity;
		this.initPanel();
	}

	public ACraftEntity(final Editor editor, final Class<? extends AEntity> clazz) {
		super(editor);
		try {
			this.entity = clazz.newInstance();
		} catch (final InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.initPanel();
	}

	private void initPanel() {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridBagLayout());
		// constraints
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		// items
		this.add(new ItemList(this.editor, this.entity), constraints);
		// back
		final JButton btnBack = new JButton("back..");
		btnBack.addActionListener(e -> {
			this.editor.back();
		});
		this.add(btnBack, constraints);
	}
}
