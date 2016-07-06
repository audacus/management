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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import ch.audacus.management.core.AEntity;
import ch.audacus.management.core.Database;

// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FormattedTextFieldDemoProject/src/components/FormattedTextFieldDemo.java
public class EntityEditor extends AView implements PropertyChangeListener {

	protected AEntity entity;
	protected Map<String, JComponent> fields = new HashMap<>();
	protected List<JFormattedTextField> fieldsInt = new ArrayList<>();
	protected List<JFormattedTextField> fieldsDouble = new ArrayList<>();

	public EntityEditor(final Editor editor, final AEntity entity) {
		super(editor);
		this.entity = entity;
		this.initEditor();
	}

	// TODO: NumberFormat integer, double, string
	// TODO: [ ] Instance, [ ]Management, [ ] Property, [ ] Relation, [ ] Thing, [ ] Value
	private void initEditor() {
		this.setLayout(new SpringLayout());
		final Map<String, Object> map = this.entity.toMap();
		System.out.println("open: " + map);
		map.forEach((key, value) -> {
			if (Arrays.stream(this.entity.primaries).anyMatch(p -> p != key)) {
				final Class<?> clazz = this.entity.getPropertyType(key);
				switch (clazz.getSimpleName()) {
					case "int":
					case "Integer":
						final JFormattedTextField fieldInt = new JFormattedTextField(NumberFormat.getIntegerInstance());
						fieldInt.setName(key);
						if (value != null) {
							fieldInt.setValue(new Integer(value.toString()));
						}
						fieldInt.addPropertyChangeListener(this);
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
						fieldDouble.addPropertyChangeListener(this);
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
						//				case "Property":
						//					final Property property = (Property) value;
						//					if (property.getRelation().getName() == "multiple") {
						//						try {
						//							final List<? extends AEntity> listProperty = Database.resultSetToList(property, Database.getByFields(new Property(), null));
						//							final JComboBox<Property> comboProperty = new JComboBox<>(listProperty.toArray(new Property[listProperty.size()]));
						//							this.add(comboProperty);
						//						} catch (final SQLException e) {
						//							e.printStackTrace();
						//						}
						//					} else {
						//
						//					}
						//					break;
					default:
						// do nothing
				}
			}
		});
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
					System.out.println("save: " + this.entity.toMap());
					Database.persist(this.entity);
					this.editor.back();
				} catch (final SQLException ex) {
					ex.printStackTrace();
				}
			});
		});
		this.add(cancel);
		this.add(save);
		SpringUtilities.makeCompactGrid(this, map.size() - this.entity.primaries.length + 1, 2, 6, 6, 6, 6);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (this.fieldsInt.contains(evt.getSource())) {

		} else if (this.fieldsDouble.contains(evt.getSource())) {

		}
	}
}
