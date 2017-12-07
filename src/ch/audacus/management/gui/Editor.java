package ch.audacus.management.gui;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.audacus.management.core.Database;

@SuppressWarnings("serial")
public class Editor extends JFrame {

	protected LinkedList<JPanel> history = new LinkedList<>();

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
		super();
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
			case CRAFT_THING:
				this.setView(new CraftThing(this));
				break;
			default:
				// do nothing
				break;
		}
	}

	public void setView(final AView view) {
		this.history.add(view);
		this.setContentPane(view);
		this.pack();
		this.setLocationRelativeTo(null);
		//		System.out.println("view: " + view.getClass().getSimpleName());
		System.out.println("view: " + this.history.stream().map(e -> e.getClass().getSimpleName()).collect(Collectors.joining(" > ")));
	}

	public void back() {
		this.history.pollLast();
		this.setView((AView) this.history.pollLast());
	}

	public void showMessage(final EMessage message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public AView replaceView(final AView toBeReplaced, final AView newView) {
		return (AView) this.history.set(this.history.lastIndexOf(toBeReplaced), newView);
	}
}
