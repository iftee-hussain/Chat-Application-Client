package com.view.chatWindow;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class BasePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JSeparator separator = new JSeparator();

	/**
	 * Create the panel.
	 */
	public BasePanel() {
		setLayout(null);
		// setSize(925,575);
		this.setBackground(Color.WHITE);
		separator.setForeground(SystemColor.textHighlight);
		separator.setOrientation(SwingConstants.VERTICAL);

		setDefault();
		
		add(separator);
	}

	public void setDefault() {
		separator.setBounds(275, 26, 49, 562);
	}

	public void setMaximum() {
		separator.setBounds(275, 26, 49, Toolkit.getDefaultToolkit()
				.getScreenSize().width);
	}
}
