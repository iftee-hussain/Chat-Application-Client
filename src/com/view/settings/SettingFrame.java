package com.view.settings;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.admin.MyProfile;
import com.modification.jpanel.JPanelX;

public class SettingFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel;

	private JButton crossButton;
	public JButton button = new JButton();
	SettingFrame frm = this;

	JButton btnAddFriend;
	JButton btnImageUp;
	JButton btnChangePass;
	JButton btnMyProfile;

	public SettingFrame(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);

		back_panel = new JPanelX("/resources/adminpanelback.png",
				SettingFrame.this, true);

		setContentPane(back_panel);
		setResizable(false);
		this.setSize(420, 480);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);

		button.setIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/cross.png")));
		button.setRolloverIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/cra.png")));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setBounds(380, 10, 20, 20);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();

			}
		});

		back_panel.add(button);

		btnMyProfile = new JButton("");
		btnMyProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MyProfile(SettingFrame.this).setVisible(true);
			}
		});
		btnMyProfile.setIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/c1.png")));
		btnMyProfile.setRolloverIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/c2.png")));
		btnMyProfile.setBounds(15, 151, 390, 36);
		btnMyProfile.setContentAreaFilled(false);
		btnMyProfile.setBorderPainted(false);
		btnMyProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
		back_panel.add(btnMyProfile);

		btnChangePass = new JButton("");
		btnChangePass.setIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/c4.png")));
		btnChangePass.setRolloverIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/c3.png")));
		btnChangePass.setBounds(15, 184, 390, 36);
		btnChangePass.setContentAreaFilled(false);
		btnChangePass.setBorderPainted(false);
		btnChangePass.setCursor(new Cursor(Cursor.HAND_CURSOR));
		back_panel.add(btnChangePass);

		btnChangePass.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ChangePassword(SettingFrame.this);

			}
		});

		btnImageUp = new JButton("");
		btnImageUp.setIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/c8.png")));
		btnImageUp.setRolloverIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/c7.png")));
		btnImageUp.setBounds(14, 217, 390, 36);
		btnImageUp.setContentAreaFilled(false);
		btnImageUp.setBorderPainted(false);
		btnImageUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		back_panel.add(btnImageUp);

		btnAddFriend = new JButton("");
		btnAddFriend.setIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/c5.png")));
		btnAddFriend.setRolloverIcon(new ImageIcon(SettingFrame.class
				.getResource("/resources/c6.png")));
		btnAddFriend.setBounds(17, 252, 390, 36);
		btnAddFriend.setContentAreaFilled(false);
		btnAddFriend.setBorderPainted(false);
		btnAddFriend.setCursor(new Cursor(Cursor.HAND_CURSOR));

		back_panel.add(btnAddFriend);

		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new com.view.settings.AddFriend(SettingFrame.this);
			}
		});

		back_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
			}
		});
		back_panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				int thisX = frm.getLocation().x;
				int thisY = frm.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				frm.setLocation(X, Y);
			}
		});

	}

}
