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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.model.dutta.IdentityUser;
import com.modification.jpanel.InputField;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.UsefulData;

public class ChangePassword extends JDialog implements BackgroundWorkable,
		CallBackable {

	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel;
	ChangePassword frm = this;
	JButton button = new JButton();
	InputField oldF = new InputField(65, 150, 300, 40, "Old Password", true);
	InputField passF = new InputField(65, 150, 300, 40, "New Password", true);

	public ChangePassword(Window owner) {

		super(owner, ModalityType.APPLICATION_MODAL);
		back_panel = new JPanelX("/resources/adminpanelback.png",
				ChangePassword.this, true);

		back_panel.add(addCrossButton());
		oldF.setLocation(65, 180);
		passF.setLocation(65, 229);
		back_panel.add(passF);
		back_panel.add(oldF);
		addMoveListener(back_panel);
		setContentPane(back_panel);

		JButton btnChange = new JButton("");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!new String(passF.getPassword()).equals("")
						&& !new String(oldF.getPassword()).equals("")) {

					if (IdentityUser.getSecureHash(
							new String(oldF.getPassword()))
							.equals(IdentityUser.getSecureHash(UsefulData
									.getInstance().userpass))) {
						UsefulData.getInstance().main.clientControl.send(
								ChangePassword.this,
								new Message(Constant.CHANGE_PASS,
										IdentityUser.userName, IdentityUser
												.getSecureHash(new String(passF
														.getPassword())), "",
										-1));
					} else {
						JOptionPane.showMessageDialog(ChangePassword.this,
								"Old password  doesn't Match.");
					}

				} else {
					JOptionPane.showMessageDialog(ChangePassword.this,
							"These two fields are required.");
				}
			}
		});
		btnChange.setIcon(new ImageIcon(ChangePassword.class
				.getResource("/resources/change.png")));
		btnChange.setRolloverIcon(new ImageIcon(ChangePassword.class
				.getResource("/resources/change_.png")));
		btnChange.setBounds(160, 269, 116, 49);
		btnChange.setContentAreaFilled(false);
		btnChange.setBorderPainted(false);
		btnChange.setCursor(new Cursor(Cursor.HAND_CURSOR));
		back_panel.add(btnChange);

		setResizable(false);
		this.setSize(420, 480);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);
		setVisible(true);

	}

	private JButton addCrossButton() {

		JButton crossButton = new JButton(new ImageIcon(
				ChangePassword.class.getResource("/resources/cross.png")));
		crossButton.setBounds(380, 10, 20, 20);
		crossButton.setRolloverIcon(new ImageIcon(ChangePassword.class
				.getResource("/resources/cra.png")));
		crossButton.setBorderPainted(false);
		crossButton.setOpaque(false);
		crossButton.setContentAreaFilled(false);
		crossButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						dispose();

					}
				});

			}
		});

		return crossButton;
	}

	private void addMoveListener(JPanel panel) {

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				int thisX = ChangePassword.this.getLocation().x;
				int thisY = ChangePassword.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				ChangePassword.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public Long donInBackground() {

		return null;
	}

	@Override
	public void finished() {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(Message msg) {
		if (msg.type == Constant.CHANGE_PASS) {

			if (msg.sender.equals("true")) {
				UsefulData.getInstance().userpass = IdentityUser
						.getSecureHash(new String(passF.getPassword()));
				JOptionPane.showConfirmDialog(ChangePassword.this, msg.content);
			}
		}

	}

}
