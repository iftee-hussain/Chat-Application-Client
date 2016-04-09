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
import com.model.dutta.MeToWhom;
import com.modification.jpanel.InputField;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class AddFriend extends JDialog implements CallBackable {

	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel;
	AddFriend frm = this;
	JButton button = new JButton();
	InputField friendF = new InputField(65, 150, 300, 40, "Give username");


	public AddFriend(Window owner) {

		super(owner, ModalityType.APPLICATION_MODAL);
		back_panel = new JPanelX("/resources/adminpanelback.png",
				AddFriend.this, true);

		back_panel.add(addCrossButton());
		back_panel.add(friendF);

		addMoveListener(back_panel);
		setContentPane(back_panel);

		JButton btnChange = new JButton("");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String frnd = friendF.getText().toString();
				boolean flag = true;
				for (MeToWhom m : UsefulData.getInstance().chatwiths) {

					if (m.chatWith.equals(friendF.getText().toString())
							&& !m.yesOrNo
							&& UsefulData.getInstance().find(friendF.getText()).userType != 1) {

						UsefulData.getInstance().main.clientControl.send(
								AddFriend.this,
								new Message(Constant.FRIEND_REQ,
										IdentityUser.userName, friendF
												.getText(), "Server", -1));
						JOptionPane.showMessageDialog(AddFriend.this,
								"Friend Request Sent");
						flag = false;
					} else if (m.chatWith.equals(friendF.getText().toString())
							&& m.yesOrNo) {
						JOptionPane.showMessageDialog(AddFriend.this,
								"Already a friend");
						flag = false;
					} else {

					}

				}
				if (flag)
					JOptionPane.showMessageDialog(AddFriend.this,
							"User not found");

			}
		});
		btnChange.setIcon(new ImageIcon(AddFriend.class
				.getResource("/resources/add_.png")));
		btnChange.setRolloverIcon(new ImageIcon(AddFriend.class
				.getResource("/resources/add.png")));
		btnChange.setBounds(160, 201, 116, 49);
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
				AddFriend.class.getResource("/resources/cross.png")));
		crossButton.setBounds(380, 10, 20, 20);
		crossButton.setRolloverIcon(new ImageIcon(AddFriend.class
				.getResource("/resources/cra.png")));
		crossButton.setBorderPainted(false);
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

				int thisX = AddFriend.this.getLocation().x;
				int thisY = AddFriend.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				AddFriend.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(Message msg) {
		// TODO Auto-generated method stub

	}

}
