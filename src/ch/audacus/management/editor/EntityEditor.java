package ch.audacus.management.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import ch.audacus.management.core.AEntity;
import ch.audacus.management.core.Database;
import ch.audacus.management.core.Property;

// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FormattedTextFieldDemoProject/src/components/FormattedTextFieldDemo.java
public class EntityEditor extends AView implements PropertyChangeListener, IItemView {

	protected AEntity entity;
	protected IItemView itemView;
	protected Map<String, JComponent> fields = new HashMap<>();
	protected List<JFormattedTextField> fieldsInt = new ArrayList<>();
	protected List<JFormattedTextField> fieldsDouble = new ArrayList<>();

	public EntityEditor(final Editor editor, final AEntity entity, final IItemView itemView) {
		super(editor);
		this.entity = entity;
		this.itemView = itemView;
		this.initEditor();
	}

	// TODO: NumberFormat integer, double, string
	// TODO: [ ] Instance, [ ]Management, [ ] Property, [ ] Relation, [ ] Thing,
	// [ ] Value
	private void initEditor() {
		this.setLayout(new SpringLayout());

		this.addFields(this.entity.toMap());

		// cancel and save buttons
		final JButton cancel = new JButton("cancel");
		cancel.addActionListener(e -> {
			this.editor.back();
		});

		final JButton save = new JButton("save");
		save.addActionListener(e -> {
			this.fields.forEach((key, value) -> {
				switch (value.getClass().getSimpleName()) {
					case "JTextField":
					case "JFormattedTextField":
						this.entity.set(key, ((JTextField) value).getText());
						break;
					default:
						// do nothing
				}
				try {
					Database.persist(this.entity);
					this.itemView.reload();
					this.editor.back();
				} catch (final SQLException ex) {
					if (ex.getMessage().startsWith("[SQLITE_BUSY]")) {
						this.editor.showMessage(EMessage.DATABASE_LOCKED);
					} else {
						ex.printStackTrace();
					}
				}
			});
		});
		this.add(cancel);
		this.add(save);
		SpringUtilities.makeCompactGrid(this, this.fields.size() + 1, 2, 6, 6, 6, 6);

	}

	private void addFields(final Map<String, ? extends Object> map) {
		System.out.println("open: " + map);
		for (final Entry<String, ? extends Object> entry : map.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();
			if (Arrays.stream(this.entity.primaries).anyMatch(p -> p != key)) {
				Class<?> clazz = null;
				if (key != null) {
					clazz = this.entity.getPropertyType(key);
				} else {
					clazz = key.getClass();
				}
				System.out.println("property: " + key + " -> " + clazz.getName());
				switch (clazz.getSimpleName()) {
					case "int":
					case "Integer":
						final JFormattedTextField fieldInt = new JFormattedTextField(NumberFormat.getIntegerInstance());
						fieldInt.setName(key);
						if (value != null) {
							fieldInt.setValue(new Integer(value.toString()));
						}
						fieldInt.addPropertyChangeListener("integer", this);
						final JLabel labelInt = new JLabel(key);
						labelInt.setLabelFor(fieldInt);
						this.add(labelInt);
						this.add(fieldInt);
						this.fieldsInt.add(fieldInt);
						this.fields.put(key, fieldInt);
						break;
					case "float":
					case "Float":
					case "double":
					case "Double":
						final JFormattedTextField fieldDouble = new JFormattedTextField(NumberFormat.getNumberInstance());
						fieldDouble.setName(key);
						if (value != null) {
							fieldDouble.setValue(new Double(value.toString()));
						}
						fieldDouble.addPropertyChangeListener("double", this);
						final JLabel labelDouble = new JLabel(key);
						labelDouble.setLabelFor(fieldDouble);
						this.add(labelDouble);
						this.add(fieldDouble);
						this.fieldsDouble.add(fieldDouble);
						this.fields.put(key, fieldDouble);
						break;
					case "String":
						final JTextField fieldString = new JTextField();
						fieldString.setName(key);
						if (value != null) {
							fieldString.setText(value.toString());
						}
						final JLabel labelString = new JLabel(key);
						labelString.setLabelFor(fieldString);
						this.add(labelString);
						this.add(fieldString);
						this.fields.put(key, fieldString);
						break;
						// properties
					case "List":
						if (value != null) {
							final List<Property> list = (List<Property>) value;
							list.forEach(property -> {
								final String name = property.getName();
								final JButton btnProperty = new JButton(property.getType().getName());
								btnProperty.setName(name);
								btnProperty.addActionListener(e -> {
									this.editor.setView(new EntityEditor(this.editor, property, this.itemView));
								});
								final JLabel labelProperty = new JLabel(name);
								labelProperty.setLabelFor(btnProperty);
								this.add(labelProperty);
								this.add(btnProperty);
								this.fields.put(name, btnProperty);
							});
						}
						break;
					case "Thing":
						break;
					case "Relation":
						break;
					default:
						// do nothing
				}
			}
		}
	}

	public JComboBox<? extends AEntity> createCombobox(final AEntity entity) {
		final JComboBox<? extends AEntity> combo = null;
		// TODO: combobox for editing things
		// TODO: combobox for selecting values of an instance
		// TODO: revalidate previous todos -> where do i need entity editor and
		// how will the fields be & what are the values of the fields
		return combo;
	}

	@Override
	public void reload() {
		// reload entity from database
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "integer":
				System.out.println("integer changed");
				break;
			case "double":
				System.out.println("double changed");
				break;
			default:
				// do nothing
		}
	}
}
