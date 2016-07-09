package ch.audacus.management.editor;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import ch.audacus.management.core.AEntity;

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
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// items
		this.add(new ItemList(this.editor, this.entity));
		// back
		final JButton btnBack = new JButton("back..");
		btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnBack.addActionListener(e -> {
			this.editor.back();
		});
		this.add(btnBack);
	}
}
