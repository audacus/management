package ch.audacus.management.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

abstract public class AEntity {

	public String table = this.getClass().getSimpleName().toLowerCase();
	public String[] primaries = new String[] { "id" };

	public AEntity(final Map<String, ?> map) {
		for (final Entry<String, ?> entry : map.entrySet()) {
			this.set(entry.getKey(), entry.getValue());
		}
	}

	public AEntity() {}

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

	public int toInt() throws NumberFormatException {
		return Integer.parseInt(String.valueOf(this));
	}

	@Override
	public String toString() {
		return String.valueOf(this.get("id"));
	}
}
