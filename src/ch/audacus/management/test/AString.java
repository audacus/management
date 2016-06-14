package ch.audacus.management.test;

public class AString<String> extends AValue<String> {

	@Override
	public <T> T get() {
		return (T) this.value;
	}

	@Override
	public <T extends AValue> void set(final Class<T> value) {
		// TODO Auto-generated method stub

	}

}
