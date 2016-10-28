package ch.audacus.management.gui;

import java.applet.Applet;
import java.awt.Graphics;

public class Test extends Applet {

	@Override
	public void paint(final Graphics g) {
		g.drawString("Hello Java8", 0, this.getHeight() / 2);
	}
}
