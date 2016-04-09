package com.view.chatWindow;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.model.dutta.IdentityUser;
import com.model.dutta.UserDetails;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;
import com.view.settings.SettingFrame;

public class MenuTopPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image = null;
	JButton btnSettings = new JButton("");
	JButton btnAdmin = new JButton();
	JButton btnLogout = new JButton();
	JButton btnchatTransfer = new JButton("Chat Transfer");
	JLabel lbl = new JLabel();
	ChatFrame frm;

	public MenuTopPanel(final ChatFrame frm) {
		setLayout(null);
		this.frm = frm;
		image = new ImageIcon(getClass().getClassLoader().getResource(
				"resources/ntop.png")).getImage();
		
		

		btnSettings.setBorderPainted(false);
		btnSettings.setFocusable(false);
		btnSettings.setContentAreaFilled(false);
		btnSettings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSettings.setIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("resources/settinngs1.png")));
		btnSettings.setRolloverIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("resources/settinngs.png")));
		btnSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UsefulData.getInstance().main.initSettingWindow();

			}
		});
		{
			 btnchatTransfer.setBorderPainted(false);
			 btnchatTransfer.setFocusable(false);
			btnchatTransfer.setContentAreaFilled(false);
			btnchatTransfer.setCursor(new Cursor(Cursor.HAND_CURSOR));
			// btnchatTransfer.setIcon(new ImageIcon(getClass().getClassLoader()
			// .getResource("resources/settinngs1.png")));
			// btnchatTransfer.setRolloverIcon(new
			// ImageIcon(getClass().getClassLoader()
			// .getResource("resources/settinngs.png")));
			btnchatTransfer.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new CallTransferWindow(
							UsefulData.getInstance().main.chatWindow)
							.setVisible(true);

				}
			});
		}

		btnAdmin.setBorderPainted(false);
		btnAdmin.setFocusable(false);
		btnAdmin.setContentAreaFilled(false);
		btnAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdmin.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
				"resources/ap.png")));

		btnAdmin.setRolloverIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("resources/ap1.png")));
		btnAdmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UsefulData.getInstance().main.initAdminWindow();

			}
		});

		btnLogout.setBorderPainted(false);
		btnLogout.setFocusable(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogout.setIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("resources/logout.png")));

		btnLogout.setRolloverIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("resources/logout1.png")));
		btnLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						UsefulData.getInstance().main.signOut();
						UsefulData.getInstance().main.initLoginWindow();
						frm.dispose();
						

					}
				});

			}
		});
		if (IdentityUser.getIdentityUser().userDetails.userType == 1||UsefulData.getInstance().userPermissions.adminPanelAccess)
			add(btnAdmin);
		add(btnSettings);
		add(btnLogout);
		btnchatTransfer.setVisible(false);
		if(UsefulData.getInstance().userPermissions.calltransfer)
			add(btnchatTransfer);
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		btnSettings.setLocation(2, 5);
		btnSettings.setSize(85, 15);

		btnAdmin.setLocation(180, 5);
		btnAdmin.setSize(110, 15);

		btnLogout.setLocation(90, 5);
		btnLogout.setSize(85, 15);

		btnchatTransfer.setLocation(320, 5);
		btnchatTransfer.setSize(140, 15);

	}

}
