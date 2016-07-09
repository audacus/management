package ch.audacus.management.editor;


public enum EMessage {

	DATABASE_LOCKED("database connection locked!\nplease close all other instances of the program!");

	private final String message;

	private EMessage(final String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.message;
	}
}
