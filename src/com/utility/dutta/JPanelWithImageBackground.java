package com.utility.dutta;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelWithImageBackground extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;

	private JPanelWithImageBackground(String url) {
		this.image = new ImageIcon(getClass().getClassLoader().getResource(url))
				.getImage();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public static JPanel getJPanel(String url) {
		JPanelWithImageBackground panel = new JPanelWithImageBackground(url);
		return panel;
	}
}
