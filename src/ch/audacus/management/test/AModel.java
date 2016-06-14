package ch.audacus.management.test;

import java.util.HashMap;

abstract public class AModel {

	protected HashMap<String, IValue> propertries;

	protected AModel() {
		this.init();
	}

	abstract void init();
}
