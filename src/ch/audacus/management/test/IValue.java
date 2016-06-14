package ch.audacus.management.test;


public interface IValue {

	public <T> T get();

	public <T extends AValue> void set(Class<T> value);
}
