package ch.audacus.management.editor;

import java.awt.Component;
import java.awt.TextField;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import ch.audacus.management.core.AEntity;
import ch.audacus.management.core.Database;
import ch.audacus.management.core.Property;

public class EntityEditor extends JFrame {

	protected AEntity entity;

	public EntityEditor(final AEntity entity) {
		super();
		this.entity = entity;
		this.initEditor();
	}

	private void initEditor() {
		final Map<String, Object> map = this.entity.toMap();
		System.out.println(map);
		for (final Entry<String, Object> entry : map.entrySet()) {
			final String key = entry.getKey();
			final Object value = entry.getValue();
			final Class<?> clazz = this.entity.getPropertyType(key);
			switch (clazz.getSimpleName()) {
				case "int":
					final JTextField fieldInt = new JTextField(value.toString());
					this.add(fieldInt);
					break;
				case "String":
					final JTextField fieldString = new JTextField(value.toString());
					this.add(fieldString);
					break;
				case "Property":
					final Property property = (Property) value;
					if (property.getRelation().getName() == "single") {

					} else {
						try {
							final List<? extends AEntity> listProperty = Database.resultSetToList(property, Database.getByFields(new Property(), null));
							final JComboBox<Property> comboProperty = new JComboBox<>(listProperty.toArray(new Property[listProperty.size()]));
							this.add(comboProperty);
						} catch (final SQLException e) {
							e.printStackTrace();
						}
					}

					break;
				default:
					// do nothing
					break;
			}
		}
	}

	private Class<? extends Component> getPropertyComponent(final String name, final Class<?> clazz) {
		Class<? extends Component> component = null;
		switch (clazz.getSimpleName()) {
			case "int":
				component = new TextField();
				break;
			case "String":
			default:
				component = new JTextField();
				break;
		}
		return component;
	}
}
