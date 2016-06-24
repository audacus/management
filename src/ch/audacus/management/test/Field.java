package ch.audacus.management.test;

public class Field<T> {

	protected T value;

	public Field() {}

	public Field(final T value) {
		this.set(value);
	}

	public T get() {
		return this.value;
	}

	public T set(final T value) {
		return this.value = value;
	}
}
