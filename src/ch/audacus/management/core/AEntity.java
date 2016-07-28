package ch.audacus.management.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

abstract public class AEntity {

	public String table = this.getClass().getSimpleName().toLowerCase();
	public String[] primaries = new String[] { "id" };

	public AEntity() {}

	public AEntity(final Map<String, ?> map) {
		map.forEach((key, value) -> this.set(key, value));
	}

	public AEntity(final ResultSet result) {
		try {
			final ResultSetMetaData metadata = result.getMetaData();
			final int columns = metadata.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				final String name = metadata.getColumnName(i);
				final Object value = result.getObject(i);
				final Class<?> clazz = PropertyUtils.getPropertyType(this, name);
				if (clazz.isPrimitive() || clazz.equals(String.class)) {
					this.set(name, value);
				} else {
					final Object instance = clazz.newInstance();
					if (instance instanceof AEntity) {
						final Method method = instance.getClass().getMethod("fromId", int.class);
						if (method != null) {
							this.set(name, method.invoke(instance, Integer.parseInt(String.valueOf(value))));
						}
					}
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	abstract public String getName();

	abstract public <T extends AEntity> T fromId(final int id);

	abstract public <T extends AEntity> T fromResultSet(ResultSet result);

	abstract public Map<String, ? extends Object> toMap();

	abstract public Map<String, ? extends Object> toPersistMap();


	public Object get(final String name) {
		Object value = null;
		try {
			value = PropertyUtils.getSimpleProperty(this, name);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return value;
	}

	public <T> AEntity set(final String name, final T value) {
		try {
			BeanUtils.setProperty(this, name, value);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return this;
	}

	public Class<?> getPropertyType(final String name) {
		Class<?> clazz = null;
		try {
			clazz = PropertyUtils.getPropertyType(this, name);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public int toInt() throws NumberFormatException {
		return Integer.parseInt(String.valueOf(this));
	}

	@Override
	public String toString() {
		final List<String> values = new ArrayList<>();
		Arrays.stream(this.primaries).forEach(primary -> values.add(String.valueOf(this.get(primary))));
		return values.stream().collect(Collectors.joining("|"));
	}
}
