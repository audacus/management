package ch.audacus.management.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.text.NumberFormat;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ch.audacus.management.core.AEntity;
import ch.audacus.management.core.Database;
import ch.audacus.management.core.Property;
import ch.audacus.management.core.Thing;

// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FormattedTextFieldDemoProject/src/components/FormattedTextFieldDemo.java
@SuppressWarnings("serial")
public class EntityEditor extends AView implements IItemView {

	protected AEntity entity;
	protected IItemView itemView;
	protected Map<String, JComponent> fields = new HashMap<>();
	//	protected List<JFormattedTextField> fieldsInt = new ArrayList<>();
	//	protected List<JFormattedTextField> fieldsDouble = new ArrayList<>();
	protected JPanel fieldsPanel = new JPanel(new SpringLayout());

	public EntityEditor(final Editor editor, final AEntity entity, final IItemView itemView) {
		super(editor);
		this.entity = entity;
		this.itemView = itemView;
		this.initEditor();
	}

	// TODO: NumberFormat integer, double, string
	// TODO: [ ] Instance, [ ] Management, [x] Property, [ ] Relation, [x] Thing,
	// [ ] Value
	private void initEditor() {
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(new GridBagLayout());
		// constraints
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		// title
		final JLabel title = new JLabel(this.entity.getName(), SwingConstants.CENTER);
		this.add(title, constraints);
		// fields
		this.addFields(this.entity.toMap());
		this.add(this.fieldsPanel, constraints);
		// cancel
		final JButton cancel = new JButton("cancel");
		cancel.addActionListener(e -> {
			this.editor.back();
		});
		// save
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
			});
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
		this.add(cancel, constraints);
		this.add(save, constraints);

	}

	private void addFields(final Map<String, ? extends Object> map) {
		System.out.println("open: " + map);
		// add fields based on the entity map
		for (final Entry<String, ? extends Object> entry : map.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();
			// if field is not primary and key is not null
			if (Arrays.stream(this.entity.primaries).anyMatch(p -> p != key) && key != null) {
				// get type of field
				final Class<?> clazz = this.entity.getPropertyType(key);
				switch (clazz.getSimpleName()) {
					case "int":
					case "Integer":
						this.addIntegerField(key, value);
						break;
					case "float":
					case "Float":
					case "double":
					case "Double":
						this.addDoubleField(key, value);
						break;
					case "String":
						this.addStringField(key, value);
						break;
					case "List":
						if (key.equals("properties")) {
							// properties
							this.addPropertyFields(value);
						}
						break;
					case "Thing":
						this.addThingField(key, value);
						break;
					case "Relation":
						// TODO: single multiple
						break;
					case "Value":
						// TODO: single multiple
						// TODO: set value in <thing> field / <thing> combobox
						break;
					default:
						// do nothing
				}
			}
		}
		SpringUtilities.makeCompactGrid(this.fieldsPanel, this.fields.size(), 2, 6, 6, 6, 6);
	}

	public <T extends AEntity> JComboBox<T> createCombobox(final T entity) {
		final JComboBox<T> combo = new JComboBox<>();
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

	private void addIntegerField(final String name, final Object value) {
		final JFormattedTextField fieldInt = new JFormattedTextField(NumberFormat.getIntegerInstance());
		fieldInt.setName(name);
		if (value != null) {
			fieldInt.setValue(new Integer(value.toString()));
		}
		fieldInt.addPropertyChangeListener("integer", e -> {
			System.out.println("integer changed");
		});
		final JLabel labelInt = new JLabel(name);
		labelInt.setLabelFor(fieldInt);
		this.fieldsPanel.add(labelInt);
		this.fieldsPanel.add(fieldInt);
		//		this.fieldsInt.add(fieldInt);
		this.fields.put(name, fieldInt);
	}

	private void addDoubleField(final String name, final Object value) {
		final JFormattedTextField fieldDouble = new JFormattedTextField(NumberFormat.getNumberInstance());
		fieldDouble.setName(name);
		if (value != null) {
			fieldDouble.setValue(new Double(value.toString()));
		}
		fieldDouble.addPropertyChangeListener("double", e -> {
			System.out.println("double changed");
		});
		final JLabel labelDouble = new JLabel(name);
		labelDouble.setLabelFor(fieldDouble);
		this.fieldsPanel.add(labelDouble);
		this.fieldsPanel.add(fieldDouble);
		//		this.fieldsDouble.add(fieldDouble);
		this.fields.put(name, fieldDouble);
	}

	private void addStringField(final String name, final Object value) {
		final JTextField fieldString = new JTextField();
		fieldString.setName(name);
		if (value != null) {
			fieldString.setText(value.toString());
		}
		final JLabel labelString = new JLabel(name);
		labelString.setLabelFor(fieldString);
		this.fieldsPanel.add(labelString);
		this.fieldsPanel.add(fieldString);
		this.fields.put(name, fieldString);
	}

	private void addPropertyFields(final Object value) {
		if (value != null && value instanceof List) {
			@SuppressWarnings("unchecked")
			final List<Property> list = (List<Property>) value;
			list.forEach(property -> {
				final String name = property.getName();
				final JButton btnProperty = new JButton(property.getRelation().getName() + " " + property.getType().getName());
				btnProperty.setName(name);
				btnProperty.addActionListener(e -> {
					this.editor.setView(new EntityEditor(this.editor, property, this.itemView));
				});
				final JLabel labelProperty = new JLabel(name);
				labelProperty.setLabelFor(btnProperty);
				this.fieldsPanel.add(labelProperty);
				this.fieldsPanel.add(btnProperty);
				this.fields.put(name, btnProperty);
			});
		}
	}

	private void addThingField(final String name, final Object value) {
		if (!name.equals("thing")) {
			final Thing thing = (Thing) value;
			final JButton btnThing = new JButton(thing.getName());
			btnThing.setName(name);
			btnThing.addActionListener(e -> {
				this.editor.setView(new EntityEditor(this.editor, thing, this.itemView));
			});
			final JLabel labelThing = new JLabel(name);
			labelThing.setLabelFor(btnThing);
			this.fieldsPanel.add(labelThing);
			this.fieldsPanel.add(btnThing);
			this.fields.put(name, btnThing);
		}
	}
}
