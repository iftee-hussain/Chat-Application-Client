package com.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.model.dutta.IdentityUser;
import com.model.dutta.UserPermissions;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.UsefulData;
import com.view.chatWindow.FriendInfo;

public class UserAccess extends JDialog implements BackgroundWorkable,
		CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Point initialClick;
	String userstype[] = { "Superadmin", "Admin", "Office Staff", "Customer" };
	ArrayList<String> current_all_users = UsefulData.getInstance().current_all_users;
	private JTable table;
	JComboBox<?> UserIdBox;
	JScrollPane pane;
	JComboBox<?>  userIdBox;
	JCheckBox chckbxCallTransfer;
	JCheckBox chckbxApprovalForFriend;
	JCheckBox chckbxRecieveMessage;
	JCheckBox chckbxSendMessage;
	JCheckBox chckbxApproval;
	JCheckBox chckbxSendFriendAdd;
	JCheckBox chckbxAdminPanelAccess;
	JCheckBox chckbxActive;
	private JButton btnCancel;
	final JComboBox<?>  UserTypeBox;
	JLabel lblNewLabel = new JLabel("");
	JLabel label = new JLabel("");
	private JCheckBox chckbxFileSharing;
	JCheckBox chckbxGroupChat;

	public UserAccess(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.setLocationRelativeTo(owner);
		try {
//			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		setSize(925, 700);
		setResizable(false);
		setFocusable(true);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane = new JPanelX("/resources/useraccess.png", this, true);
		setContentPane(contentPane);
		addMoveListener(contentPane);
		contentPane.setLayout(null);

		JButton crossButton = new JButton(new ImageIcon(
				UserAccess.class.getResource("/resources/cross.png")));
		crossButton.setBounds(870, 15, 24, 20);
		crossButton.setRolloverIcon(new ImageIcon(UserAccess.class
				.getResource("/resources/cra.png")));
		crossButton.setBorderPainted(false);
		crossButton.setContentAreaFilled(false);
		contentPane.add(crossButton);

		UserTypeBox = new JComboBox<>(userstype);
		UserTypeBox.setBounds(176, 247, 159, 23);
		UserTypeBox.setSelectedIndex(-1);
		contentPane.add(UserTypeBox);
		UserTypeBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = UserTypeBox.getSelectedIndex();
				i++;

				UsefulData.getInstance().main.clientControl.send(
						UserAccess.this, new Message(Constant.SPECIFIC_USERS,
								IdentityUser.getIdentityUser().userName,
								i + "", "Server", -1));

			}
		});
		// /Kono Kamer na
		userIdBox = new JComboBox<>();
		userIdBox.setBounds(176, 290, 157, 23);
		userIdBox.setSelectedIndex(-1);

		contentPane.add(userIdBox);

		chckbxActive = new JCheckBox("Active");
		chckbxActive.setOpaque(false);
		chckbxActive.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxActive.setBounds(176, 404, 97, 23);
		contentPane.add(chckbxActive);

		lblNewLabel.setOpaque(false);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel.setBounds(176, 334, 142, 14);
		contentPane.add(lblNewLabel);

		label.setOpaque(false);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(177, 370, 142, 14);
		contentPane.add(label);

		chckbxAdminPanelAccess = new JCheckBox("Admin Panel Access");
		chckbxAdminPanelAccess.setOpaque(false);
		chckbxAdminPanelAccess.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxAdminPanelAccess.setBounds(176, 430, 159, 23);
		contentPane.add(chckbxAdminPanelAccess);

		chckbxSendFriendAdd = new JCheckBox("Send Friend Add Request");
		chckbxSendFriendAdd.setOpaque(false);
		chckbxSendFriendAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxSendFriendAdd.setBounds(176, 456, 181, 23);
		contentPane.add(chckbxSendFriendAdd);

		chckbxApproval = new JCheckBox("Approval Remove Request");
		chckbxApproval.setOpaque(false);
		chckbxApproval.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxApproval.setBounds(176, 482, 194, 23);
		contentPane.add(chckbxApproval);

		chckbxApprovalForFriend = new JCheckBox(
				"Approval For Friend Add Request");
		chckbxApprovalForFriend.setOpaque(false);
		chckbxApprovalForFriend.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxApprovalForFriend.setBounds(176, 508, 205, 23);
		contentPane.add(chckbxApprovalForFriend);

		chckbxCallTransfer = new JCheckBox("Call Transfer");
		chckbxCallTransfer.setOpaque(false);
		chckbxCallTransfer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxCallTransfer.setBounds(176, 534, 97, 23);
		contentPane.add(chckbxCallTransfer);

		chckbxSendMessage = new JCheckBox("Send Message");
		chckbxSendMessage.setOpaque(false);
		chckbxSendMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxSendMessage.setBounds(176, 560, 194, 23);
		contentPane.add(chckbxSendMessage);

		chckbxRecieveMessage = new JCheckBox("Recieve Message");
		chckbxRecieveMessage.setOpaque(false);;
		chckbxRecieveMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxRecieveMessage.setBounds(176, 586, 194, 23);
		contentPane.add(chckbxRecieveMessage);

		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(false);

		table.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] { "User Name", "Full Name", "User Type", "Set" }));
		setTable();
		table.setBounds(485, 200, 400, 300);

		pane = new JScrollPane(table);
		pane.setBounds(485, 221, 400, 300);
		pane.setBorder(new LineBorder(new Color(0, 0, 0)));
		pane.setBackground(Color.WHITE);
		contentPane.add(pane);
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
		UserIdBox = new JComboBox<>();
		UserIdBox.setBounds(176, 290, 157, 23);
		UserIdBox.setSelectedIndex(-1);
		contentPane.add(UserIdBox);

		JButton btnSave = new JButton("");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				UserPermissions leftPermissions = new UserPermissions(UserIdBox
						.getSelectedItem().toString(), chckbxActive
						.isSelected(), chckbxAdminPanelAccess.isSelected(),
						chckbxSendFriendAdd.isSelected(),
						chckbxApprovalForFriend.isSelected(), chckbxApproval
								.isSelected(), chckbxCallTransfer.isSelected(),
						chckbxSendMessage.isSelected(), chckbxRecieveMessage
								.isSelected(), chckbxFileSharing.isSelected(),
						chckbxGroupChat.isSelected());
				String lp = leftPermissions.toString();
				String rp = "";
				for (int i = 0; i < table.getRowCount(); i++) {
					rp = rp + table.getValueAt(i, 0)
							+ Message.SECOND_LEVEL_SEPERATOR
							+ table.getValueAt(i, 3)
							+ Message.SECOND_LEVEL_SEPERATOR;
				}
				String content = lp + Message.THIRD_LEVEL_SEPERATOR + rp;
				UsefulData.getInstance().main.clientControl.send(
						UserAccess.this,
						new Message(Constant.UPDATE_PERMISSION, IdentityUser
								.getIdentityUser().userName, content, "Server",
								-1));
				JOptionPane.showMessageDialog(null, "Successfully Updated....");

			}
		});
		btnSave.setBounds(509, 552, 104, 37);
		btnSave.setOpaque(false);
		btnSave.setRolloverIcon(new ImageIcon(UserAccess.class
				.getResource("/resources/saver.png")));
		btnSave.setPressedIcon(new ImageIcon(UserAccess.class
				.getResource("/resources/savep.png")));
		btnSave.setIcon(new ImageIcon(UserAccess.class
				.getResource("/resources/save.png")));
		btnSave.setBorderPainted(false);
		btnSave.setContentAreaFilled(false);
		contentPane.add(btnSave);

		btnCancel = new JButton("");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserAccess.this.dispose();
			}
		});
		btnCancel.setRolloverIcon(new ImageIcon(UserAccess.class
				.getResource("/resources/cancelr.png")));
		btnCancel.setPressedIcon(new ImageIcon(UserAccess.class
				.getResource("/resources/cancelp.png")));
		btnCancel.setIcon(new ImageIcon(UserAccess.class
				.getResource("/resources/cancel.png")));
		btnCancel.setOpaque(false);
		btnCancel.setContentAreaFilled(false);
		btnCancel.setBorderPainted(false);
		btnCancel.setBounds(647, 553, 104, 37);
		contentPane.add(btnCancel);

		chckbxFileSharing = new JCheckBox("File Sharing");
		chckbxFileSharing.setOpaque(false);
		chckbxFileSharing.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxFileSharing.setBounds(176, 614, 194, 23);
		contentPane.add(chckbxFileSharing);

		chckbxGroupChat = new JCheckBox("Group Chat");
		chckbxGroupChat.setOpaque(false);
		chckbxGroupChat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxGroupChat.setBounds(176, 640, 194, 23);
		contentPane.add(chckbxGroupChat);
		UserIdBox.setVisible(true);
		UserIdBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UsefulData.getInstance().main.clientControl.send(
						UserAccess.this,
						new Message(Constant.SPECIFIC_USER_PERMISSION,
								IdentityUser.getIdentityUser().userName,
								UserIdBox.getSelectedItem().toString(),
								"Server", -1));

			}
		});
		repaint();
		validate();
		revalidate();

	}

	private void setTable() {
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
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

				int thisX = UserAccess.this.getLocation().x;
				int thisY = UserAccess.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				UserAccess.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(final Message msg) {
		int type = UserTypeBox.getSelectedIndex() + 1;
		final String[] userPchat = msg.content
				.split(Message.THIRD_LEVEL_SEPERATOR);

		String[] users = userPchat[0].split(Message.SECOND_LEVEL_SEPERATOR);

		if (msg.type == Constant.SPECIFIC_USER_PERMISSION) {

			boolean permission[] = new boolean[10];
			for (int i = 0; i < 10; i++) {
				permission[i] = Boolean.parseBoolean(users[i]);
			}

			String[] rusers = userPchat[1]
					.split(Message.SECOND_LEVEL_SEPERATOR);

			table.setModel(new DefaultTableModel() {

				private static final long serialVersionUID = 1L;

				/*
				 * @Override public Class getColumnClass(int column) { return
				 * getValueAt(0, column).getClass(); }
				 */
				@Override
				public Class<?> getColumnClass(int column) {
					switch (column) {
					case 0:
						return String.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					case 3:
						return Boolean.class;
					default:
						return Boolean.class;
					}
				}
			});

			DefaultTableModel model = (DefaultTableModel) table.getModel();

			model.setColumnIdentifiers(new Object[] { "User Name", "Full Name",
					"User Type", "Set" });
			setTable();

			for (int i = 0; i < rusers.length - 1; i++) {
				FriendInfo e = UsefulData.getInstance().find(rusers[i]);
				if (e != null) {
					model.addRow(new Object[] { rusers[i], e.fullName,
							e.userType, Boolean.parseBoolean(rusers[i + 1]) });
					i++;
				}
			}

			chckbxActive.setSelected(permission[0]);
			chckbxAdminPanelAccess.setSelected(permission[1]);

			chckbxSendFriendAdd.setSelected(permission[2]);

			chckbxApprovalForFriend.setSelected(permission[3]);
			chckbxApproval.setSelected(permission[4]);

			chckbxSendMessage.setSelected(permission[5]);
			chckbxRecieveMessage.setSelected(permission[6]);
			chckbxCallTransfer.setSelected(permission[7]);
			chckbxFileSharing.setSelected(permission[8]);
			chckbxGroupChat.setSelected(permission[9]);

		} else {

			if (UserIdBox != null)
				UserIdBox.setVisible(false);

			final String n_users[];
			if (IdentityUser.getIdentityUser().userDetails.userType == type) {
				n_users = new String[users.length - 1];
			} else {

				n_users = new String[users.length];
			}

			int j = 0;
			for (int i = 0; i < users.length; i++) {
				if (!users[i].equals(IdentityUser.userName)) {
					n_users[j] = users[i];
					j++;

				}

			}

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					userIdBox.setVisible(false);

					UserIdBox = new JComboBox<>(n_users);
					UserIdBox.setBounds(176, 290, 157, 23);
					UserIdBox.setSelectedIndex(-1);
					contentPane.add(UserIdBox);
					UserIdBox.setVisible(true);
					UserIdBox.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {

							lblNewLabel.setText(UserIdBox.getSelectedItem()
									.toString());
							FriendInfo e = UsefulData.getInstance().find(
									UserIdBox.getSelectedItem().toString());
							label.setText(e.country);
							UsefulData.getInstance().main.clientControl
									.send(UserAccess.this,
											new Message(
													Constant.SPECIFIC_USER_PERMISSION,
													IdentityUser
															.getIdentityUser().userName,
													UserIdBox.getSelectedItem()
															.toString(),
													"Server", -1));
						}

					});

					repaint();
					validate();
					revalidate();

				}
			});

		}

	}

	@Override
	public Long donInBackground() {
		// tableData();
		return null;
	}

	@Override
	public void finished() {
		// TODO Auto-generated method stub

	}
}
