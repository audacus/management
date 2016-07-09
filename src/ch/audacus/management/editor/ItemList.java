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

public class ItemList extends JPanel implements IItemView {

	protected Editor editor;
	protected AEntity entity; // entity instance for reference
	protected List<? extends AEntity> items;
	protected List<JButton> buttons = null;

	public ItemList(final Editor editor, final AEntity entity) {
		super();
		this.entity = entity;
		try {
			this.items = this.readItems(entity);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		this.initList(editor);
	}

	public ItemList(final Editor editor, final Class<? extends AEntity> clazz) {
		super();
		try {
			this.entity = clazz.newInstance();
			this.items = this.readItems(this.entity);
		} catch (final SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.initList(editor);
	}

	private List<? extends AEntity> readItems(final AEntity entity) throws SQLException {
		List<? extends AEntity> items = null;
		items = Database.resultSetToList(entity, Database.getByFields(entity, null));
		return items;
	}

	private void initList(final Editor editor) {
		this.removeAll();
		this.editor = editor;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.buttons = new ArrayList<>();

		// item buttons
		this.items.forEach(item -> {
			System.out.println("item: " + item.toMap());
			final JButton buttonItem = new JButton(item.get("name").toString());
			buttonItem.addActionListener(e -> {
				this.editor.setView(new EntityEditor(editor, item, this));
			});
			this.buttons.add(buttonItem);
		});
		// "new" button
		final JButton buttonNew = new JButton("+ new " + this.entity.getClass().getSimpleName());
		buttonNew.addActionListener(e -> {
			this.editor.setView(new EntityEditor(editor, this.entity, this));
		});
		this.buttons.add(buttonNew);

		// do for all buttons
		for (final JButton button : this.buttons) {
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.setSize(button.getMaximumSize());
			this.add(button);
		}
	}

	@Override
	public void reload() {
		if (this.entity != null) {
			try {
				this.items = this.readItems(this.entity);
			} catch (final SQLException e) {
				e.printStackTrace();
			}
			this.initList(this.editor);
		}
	}
}
