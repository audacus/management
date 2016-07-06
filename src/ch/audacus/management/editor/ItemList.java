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

	protected Editor editor;
	protected EView listView;
	protected AEntity entity; // entity instance for reference
	protected List<? extends AEntity> items;
	protected List<JButton> buttons = new ArrayList<>();

	public ItemList(final Editor editor, final EView listView, final AEntity entity) {
		super();
		this.entity = entity;
		try {
			this.items = Database.resultSetToList(entity, Database.getByFields(entity, null));
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		this.initList(editor, listView);
	}

	public ItemList(final Editor editor, final EView listView, final Class<? extends AEntity> clazz) {
		super();
		try {
			this.entity = clazz.newInstance();
			this.items = Database.resultSetToList(clazz, Database.getByFields(clazz, null));
		} catch (final SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.initList(editor, listView);
	}

	private void initList(final Editor editor, final EView listView) {
		this.editor = editor;
		this.listView = listView;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// item buttons
		this.items.forEach(item -> {
			System.out.println("item: " + item.toMap());
			final JButton buttonItem = new JButton(item.get("name").toString());
			buttonItem.addActionListener(e -> {
				this.editor.setView(new EntityEditor(editor, item));
			});
			this.buttons.add(buttonItem);
		});
		// "new" button
		final JButton buttonNew = new JButton("+ new " + this.entity.getClass().getSimpleName());
		buttonNew.addActionListener(e -> {
			this.editor.setView(new EntityEditor(editor, this.entity));
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
