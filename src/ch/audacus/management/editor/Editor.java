package ch.audacus.management.editor;

import java.awt.EventQueue;

import javax.swing.JFrame;

import ch.audacus.management.core.Database;

public class Editor extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				final Editor frame = new Editor();
				frame.setVisible(true);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Editor() {
		// setup database
		Database.get();
		this.setTitle("Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setView(EView.MENU_MAIN);
	}

	public void setView(final EView view) {
		switch (view) {
			case MENU_MAIN:
				this.setView(new Main(this));
				break;
			case MENU_CRAFT:
				this.setView(new Craft(this));
				break;
			case CRAFT_MANAGEMENT:
				this.setView(new CraftManagement(this));
				break;
			default:
				// do nothing
				break;
		}
	}

	public void setView(final AView view) {
		this.setContentPane(view);
		this.validate();
		this.pack();
		this.setLocationRelativeTo(null);
		System.out.println("view: " + view.getClass().getSimpleName());
	}
}
