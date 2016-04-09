package com.admin;

import java.awt.Color;
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.model.dutta.IdentityUser;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.UsefulData;
import com.view.chatWindow.FriendInfo;

public class UserManage extends JDialog implements BackgroundWorkable,
		CallBackable {

	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel;
	private JTable table;
	JScrollPane pane;
	JButton edit;
	JButton block;
	JButton delete;
	ArrayList<FriendInfo> users = UsefulData.getInstance().friendInfoList;
	ArrayList<String> delu = new ArrayList<>();
	ArrayList<String> blocku = new ArrayList<>();
	ArrayList<FriendInfo> editu = new ArrayList<>();

	public UserManage(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		back_panel = new JPanelX("/resources/usermanage.png", this, true);
		setContentPane(back_panel);

		back_panel.setLayout(null);

		back_panel.setFocusable(false);

		addMoveListener(back_panel);
		setResizable(false);
		this.setSize(500, 600);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);

		JButton crossButton = new JButton(new ImageIcon(
				MsgFilterWindow.class.getResource("/resources/cross.png")));
		crossButton.setBounds(460, 15, 24, 20);
		crossButton.setRolloverIcon(new ImageIcon(MsgFilterWindow.class
				.getResource("/resources/cra.png")));
		crossButton.setBorderPainted(false);
		crossButton.setContentAreaFilled(false);
		back_panel.add(crossButton);
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
		back_panel.add(crossButton);
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(false);

		table.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] { "User Name", "Full Name", "User Type", "Set" }));
		setTable();
		table.setBounds(485, 200, 400, 300);

		pane = new JScrollPane(table);
		pane.setBounds(50, 221, 400, 250);
		pane.setBorder(new LineBorder(new Color(0, 0, 0)));
		pane.setBackground(Color.WHITE);
		back_panel.add(pane);

		edit = new JButton();
		edit.setBounds(50, 500, 104, 37);
		edit.setOpaque(false);
		edit.setRolloverIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmedit.png")));
		edit.setPressedIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmedit.png")));
		edit.setIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmedit.png")));
		edit.setBorderPainted(false);
		edit.setContentAreaFilled(false);
		back_panel.add(edit);

		delete = new JButton();
		delete.setBounds(180, 500, 104, 37);
		delete.setOpaque(false);
		delete.setRolloverIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmdelete.png")));
		delete.setPressedIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmdelete.png")));
		delete.setIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmdelete.png")));
		delete.setBorderPainted(false);
		delete.setContentAreaFilled(false);
		back_panel.add(delete);

		block = new JButton();
		block.setBounds(310, 500, 104, 37);
		block.setOpaque(false);
		block.setRolloverIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmblock.png")));
		block.setPressedIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmblock.png")));
		block.setIcon(new ImageIcon(UserManage.class
				.getResource("/resources/usmblock.png")));
		block.setBorderPainted(false);
		block.setContentAreaFilled(false);
		back_panel.add(block);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				table.setModel(new DefaultTableModel() {

					private static final long serialVersionUID = 1L;

					/*
					 * @Override public Class getColumnClass(int column) {
					 * return getValueAt(0, column).getClass(); }
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
				model.setColumnIdentifiers(new Object[] { "User Name",
						"Full Name", "User Type", "Set" });
				setTable();

				for (FriendInfo e : users) {
					model.addRow(new Object[] { e.userName, e.fullName,
							e.userType, false });

				}

			}
		});

		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				UsefulData.getInstance().main.clientControl.send(
						UserManage.this, new Message(Constant.DELETE_USERS,
								IdentityUser.userName, getUsersToDelete(),
								"Server", -1));

			}
		});

		block.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = getWhomToBlock();
				if (!s.equals("")) {
					UsefulData.getInstance().main.clientControl.send(
							UserManage.this, new Message(Constant.BLOCK,
									IdentityUser.userName, getWhomToBlock(),
									"Server", -1));
				}

			}
		});

		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = getWhomToEdit();
				UsefulData.getInstance().main.clientControl.send(
						UserManage.this, new Message(Constant.EDIT,
								IdentityUser.userName, s, "Server", -1));

			}
		});

	}

	protected String getWhomToEdit() {
		String editUsers = "";

		for (int i = 0; i < table.getRowCount(); i++) {
			Object x = table.getValueAt(i, 3);
			Object y = table.getValueAt(i, 0);
			Object z = table.getValueAt(i, 1);
			Object w = table.getValueAt(i, 2);

			boolean di = new Boolean((boolean) x).booleanValue();
			String si = new String((String) y);
			String sii = new String((String) z);
			if (di) {

				editUsers = editUsers
						+ UsefulData.getInstance().friendInfoList.get(i).userName
						+ Message.SECOND_LEVEL_SEPERATOR + si
						+ Message.SECOND_LEVEL_SEPERATOR + sii
						+ Message.SECOND_LEVEL_SEPERATOR + w.toString()
						+ Message.SECOND_LEVEL_SEPERATOR;
				FriendInfo info = new FriendInfo(
						si,
						sii,
						UsefulData.getInstance().friendInfoList.get(i).userName,
						true, Integer.parseInt(w.toString()));
				editu.add(info);

			}

		}

		return editUsers;

	}

	protected String getWhomToBlock() {
		String blockUsers = "";
		for (int i = 0; i < table.getRowCount(); i++) {
			Object x = table.getValueAt(i, 3);
			Object y = table.getValueAt(i, 0);
			int z = (int) table.getValueAt(i, 2);
			boolean di = new Boolean((boolean) x).booleanValue();
			String si = new String((String) y);

			if (di) {
				if (z == 1)
					JOptionPane.showMessageDialog(UserManage.this,
							"Superadmin '" + si + "' can't be blocked...");
				else {
					blocku.add(si);
					blockUsers = blockUsers + si
							+ Message.SECOND_LEVEL_SEPERATOR;
				}
			}
		}
		return blockUsers;
	}

	protected String getUsersToDelete() {
		String delUsers = "";
		for (int i = 0; i < table.getRowCount(); i++) {
			Object x = table.getValueAt(i, 3);
			Object y = table.getValueAt(i, 0);
			boolean di = new Boolean((boolean) x).booleanValue();
			String si = new String((String) y);
			if (di) {
				delu.add(si);
				delUsers = delUsers + si + Message.SECOND_LEVEL_SEPERATOR;
			}
		}
		return delUsers;

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

				int thisX = UserManage.this.getLocation().x;
				int thisY = UserManage.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				UserManage.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(Message msg) {
		if (msg.type == Constant.DELETE_USERS) {
			JOptionPane.showMessageDialog(UserManage.this, msg.content);
			if (msg.sender.equals("true")) {

				for (int i = 0; i < UsefulData.getInstance().friendInfoList
						.size(); i++) {
					for (int j = 0; j < delu.size(); j++) {
						if (UsefulData.getInstance().friendInfoList.get(i).userName
								.equals(delu.get(j))) {
							UsefulData.getInstance().friendInfoList.remove(i);
						}

					}

				}
				delu.clear();

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				model.setColumnIdentifiers(new Object[] { "User Name",
						"Full Name", "User Type", "Set" });
				setTable();

				for (FriendInfo e : UsefulData.getInstance().friendInfoList) {
					model.addRow(new Object[] { e.userName, e.fullName,
							e.userType, false });

				}
			}
			UsefulData.getInstance().main.chatWindow.contactsPanel.refresh();
			repaint();
			validate();
			revalidate();
		}

		else if (msg.type == Constant.EDIT) {

			if (msg.sender.equals("true")) {

				for (int i = 0; i < UsefulData.getInstance().friendInfoList
						.size(); i++) {
					for (int j = 0; j < editu.size(); j++) {
						if (UsefulData.getInstance().friendInfoList.get(i).userName
								.equals(editu.get(j).country)) { // /Country as
																	// Old
																	// username
							UsefulData.getInstance().friendInfoList.get(i).userType = editu
									.get(j).userType;
							UsefulData.getInstance().friendInfoList.get(i).userName = editu
									.get(j).userName;
							UsefulData.getInstance().friendInfoList.get(i).fullName = editu
									.get(j).fullName;

						}

					}

				}
				editu.clear();

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				model.setColumnIdentifiers(new Object[] { "User Name",
						"Full Name", "User Type", "Set" });
				setTable();

				for (FriendInfo e : UsefulData.getInstance().friendInfoList) {
					model.addRow(new Object[] { e.userName, e.fullName,
							e.userType, false });

				}
			}
			JOptionPane.showMessageDialog(UserManage.this, msg.content);
			UsefulData.getInstance().main.chatWindow.contactsPanel
					.refresh(true);
			repaint();
			validate();
			revalidate();

		}

		else if (msg.type == Constant.BLOCK) {

			if (msg.sender.equals("true")) {
				JOptionPane.showMessageDialog(UserManage.this, msg.content);
				for (int i = 0; i < UsefulData.getInstance().friendInfoList
						.size(); i++) {
					for (int j = 0; j < blocku.size(); j++) {
						if (UsefulData.getInstance().friendInfoList.get(i).userName
								.equals(blocku.get(j))) {
							UsefulData.getInstance().friendInfoList.remove(i);
						}

					}

				}
				blocku.clear();

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				model.setColumnIdentifiers(new Object[] { "User Name",
						"Full Name", "User Type", "Set" });
				setTable();

				for (FriendInfo e : UsefulData.getInstance().friendInfoList) {
					model.addRow(new Object[] { e.userName, e.fullName,
							e.userType, false });

				}
			}
			UsefulData.getInstance().main.chatWindow.contactsPanel.refresh();
			repaint();
			validate();
			revalidate();

		}

	}

	@Override
	public Long donInBackground() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finished() {
		// TODO Auto-generated method stub

	}

}
