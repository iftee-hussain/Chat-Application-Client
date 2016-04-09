package com.view.chatWindow;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TopBorderPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image = null;
	JButton cl = new JButton();
	JButton ex = new JButton();
	JLabel lbl = new JLabel();
	TopBorderPanel thisPanel;
	ChatFrame frm;
	private Point initialClick;
	private boolean maxSize = false;

	public TopBorderPanel(final ChatFrame frm) {
		this.frm = frm;
		thisPanel = this;
		image = new ImageIcon(getClass().getClassLoader().getResource(
				"resources/top.png")).getImage();
		HandelerClass handeler = new HandelerClass();

		ex.addActionListener(handeler);
		cl.addActionListener(handeler);
		addMouseListener(handeler);
		addMouseMotionListener(handeler);

		setDefault();
	}

	public void setDefault() {
		maxSize = false;

	}

	public void setMaximum() {
		maxSize = true;
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);

	}

	public class HandelerClass extends MouseAdapter implements ActionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			try{
			if (!frm.maxSize) {
				int thisX = frm.getLocation().x;
				int thisY = frm.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				frm.setLocation(X, Y);
			}
			}catch(Exception es){
				
			}

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

			if (arg0.getClickCount() == 2) {
				if (frm.maxSize == true) {
					frm.setDefaultSize();
					frm.maxSize = false;
				} else {

					frm.setMaxSize();
					frm.maxSize = true;
				}
				maxSize = !maxSize;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			initialClick = e.getPoint();

		}

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == ex) {
				frm.dispose();
				System.exit(1);
			}
			if (e.getSource() == cl) {

				frm.setState(Frame.ICONIFIED);
			}

		}
	}

}
