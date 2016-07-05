package ch.audacus.management.editor;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ch.audacus.management.core.AEntity;
import ch.audacus.management.core.Database;

public class ItemList extends JPanel {

	protected AEntity entity; // entity instance for reference
	protected List<AEntity> items;
	protected List<JButton> buttons = new ArrayList<>();

	public ItemList(final AEntity entity) {
		super();
		this.entity = entity;
		try {
			this.items = Database.resultSetToList(entity, Database.getByFields(entity, null));
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		this.initList();
	}

	public ItemList(final Class<? extends AEntity> clazz) {
		super();
		try {
			this.entity = clazz.newInstance();
			this.items = Database.resultSetToList(clazz, Database.getByFields(clazz, null));
		} catch (final SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.initList();
	}

	private void initList() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// item buttons
		JButton buttonItem = null;
		for (final AEntity item : this.items) {
			System.out.println(item);
			buttonItem = new JButton(item.get("name").toString());
			this.buttons.add(buttonItem);
		}
		// "new" button
		final JButton buttonNew = new JButton("+ new " + this.entity.getClass().getSimpleName());
		buttonNew.addActionListener(e -> {
			new EntityEditor(this.entity).setVisible(true);
		});
		this.buttons.add(buttonNew);


		// do for all buttons
		for (final JButton button : this.buttons) {
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.setSize(button.getMaximumSize());
			this.add(button);
		}
	}
}
