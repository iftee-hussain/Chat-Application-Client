package com.view.chatWindow;

import java.awt.Cursor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.model.dutta.IdentityUser;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.JPanelWithImageBackground;
import com.utility.dutta.UsefulData;

public class InfoPanel extends JPanel implements CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageIcon avaterImg;
	public final String userName;
	private String dept;
	public boolean online;
	boolean selected;
	boolean notified = false;
	boolean isGroup = false;
	ContactsPanel friendListPanel;
	private JPanel selectedBackground = JPanelWithImageBackground
			.getJPanel("resources/friendbackgroundselected.png");

	private JPanel rolloverBackground = JPanelWithImageBackground
			.getJPanel("resources/friendbackgroundselected.png");
	private JPanel notifiedBackground = JPanelWithImageBackground
			.getJPanel("resources/friendbackground.png");
	private JPanel defaultBackground = JPanelWithImageBackground
			.getJPanel("resources/friendbackground.png");
	private JLabel icoNotification = new JLabel(new ImageIcon(getClass()
			.getClassLoader().getResource("resources/iconotification.png")));

	private JMenuItem addPeople;
	private JMenuItem[] groupMenuItems;
	private JMenuItem removePeople;

	private JMenuItem deleteGroup;

	private JMenuItem renameGroup;

	private JPopupMenu pop;

	private JMenuItem callMenuItem;

	private JMenuItem sendFileMenuItem;

	private JMenu addToGroupMenuItem;

	// Constructor for Group
	public InfoPanel(GroupPanel panel, String groupName, boolean selected) {
		this.userName = groupName;
		this.dept = "Group";
		this.online = false;
		this.selected = selected;
		this.isGroup = true;
		avaterImg = new ImageIcon(
				InfoPanel.class.getResource("/resources/confe.png"));
		notifiedBackground.setLayout(null);
		notifiedBackground.setBounds(0, 0, 275, 53);
		notifiedBackground.add(icoNotification);
		notifiedBackground.add(makeContentPanel());

		selectedBackground.setLayout(null);
		selectedBackground.setBounds(0, 0, 275, 53);
		selectedBackground.add(makeContentPanel());

		defaultBackground.setLayout(null);
		defaultBackground.setBounds(0, 0, 274, 53);
		defaultBackground.add(makeContentPanel());
		setDefault();
		pop = new JPopupMenu();
		JMenuItem leaveGroup = new JMenuItem("Leave Group");
		pop.add(leaveGroup);
		addPeople = new JMenuItem("Add Member");
		removePeople = new JMenuItem("Remove Member");
		deleteGroup = new JMenuItem("Delete Group");
		renameGroup = new JMenuItem("Rename Group Name");
		if (IdentityUser.getIdentityUser().userDetails.userType == 1) {
			pop.add(renameGroup);
			pop.addSeparator();
			pop.add(addPeople);
			pop.add(removePeople);
			pop.addSeparator();
			pop.add(deleteGroup);

		}
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				super.mousePressed(arg0);
				if (arg0.getButton() == MouseEvent.BUTTON3)
					pop.show(InfoPanel.this, arg0.getX(), arg0.getY());
			}
		});

	}

	// Constructor for Contacts
	public InfoPanel(ContactsPanel panel, String userName, String dept,
			boolean online, boolean selected, ImageIcon img) {
		this.friendListPanel = panel;
		avaterImg = img;
		this.userName = userName;
		this.dept = dept;
		this.online = online;
		this.selected = selected;

		rolloverBackground.setLayout(null);
		rolloverBackground.setBounds(0, 0, 275, 53);
		rolloverBackground.add(makeContentPanel());
		icoNotification.setBounds(210, 10, 50, 30);

		notifiedBackground.setLayout(null);
		notifiedBackground.setBounds(0, 0, 275, 53);
		notifiedBackground.add(icoNotification);
		notifiedBackground.add(makeContentPanel());

		selectedBackground.setLayout(null);
		selectedBackground.setBounds(0, 0, 275, 53);
		selectedBackground.add(makeContentPanel());

		defaultBackground.setLayout(null);
		defaultBackground.setBounds(0, 0, 274, 53);
		defaultBackground.add(makeContentPanel());
		setDefault();
		// /pop up
		final JPopupMenu pop = new JPopupMenu();
		callMenuItem = new JMenuItem("Call");
		callMenuItem.setEnabled(online);
		pop.add(callMenuItem);
		sendFileMenuItem = new JMenuItem("Send File");
		sendFileMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				UsefulData.getInstance().sendFileWindowShow();
			}
		});
		sendFileMenuItem.setEnabled(online);
		addToGroupMenuItem = new JMenu("Add To Group");

		if (UsefulData.getInstance().haveFilePermission())
			pop.add(sendFileMenuItem);
		if (UsefulData.getInstance().haveGroupPermission())
			pop.add(addToGroupMenuItem);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				super.mousePressed(arg0);
				if (arg0.getButton() == MouseEvent.BUTTON3)
					pop.show(InfoPanel.this, arg0.getX(), arg0.getY());
				addToGroupMenuItem.removeAll();
				groupMenuItems = new JMenuItem[UsefulData.getInstance().groupList.length];

				for (int i = 0; i < UsefulData.getInstance().groupList.length; i++) {
					groupMenuItems[i] = new JMenuItem(
							UsefulData.getInstance().groupList[i]);
					addToGroupMenuItem.add(groupMenuItems[i]);
					groupMenuItems[i].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							System.out.println("Group Selected: "
									+ arg0.getActionCommand());

						}
					});
				}
			}
		});
		setDropTarget(new DropTarget() {
			@Override
			public synchronized void drop(DropTargetDropEvent arg0) {
				if (InfoPanel.this.online) {
					new DropFileSending(InfoPanel.this.userName).drop(arg0);
				} else {
					JOptionPane.showMessageDialog(
							UsefulData.getInstance().main.chatWindow,
							"Error!!! Please select online user.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public void setSelected() {
		UsefulData.getInstance().main.chatWindow.homePanel.setVisible(false);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				if (UsefulData.getInstance().selectedPanel.isGroup) {
					UsefulData.getInstance().main.chatWindow.contactsPanel
							.clearSelection();
				}
				notified = false;
				selected = true;
				if (!isGroup)
					friendListPanel.setSelectedFriend(userName, online);
				IdentityUser.getIdentityUser().setSelectedFriendOrGroup(
						userName);
				removeAll();
				add(selectedBackground);
				repaint();
				validate();
				UsefulData.getInstance().main.chatWindow.chatLower
						.clearHistory();
				if (!UsefulData.getInstance().main.tempHistory
						.isExists(userName)) {
					UsefulData.getInstance().main.clientControl.send(
							InfoPanel.this, new Message(Constant.HISTORY,
									IdentityUser.getIdentityUser().userName,
									"*", IdentityUser.getIdentityUser()
											.getSelectedFriendOrGroup(), -1));
				} else {
					// UsefulData.getInstance().main.chatWindow.chatLower.clearHistory();
					try {
						// for (TempMessage tmpMsg :
						// UsefulData.getInstance().main.tempHistory
						// .getTempHistory(IdentityUser
						// .getIdentityUser().getSelectedFriendOrGroup())) {

						// if (tmpMsg.isGroup()) {
						// UsefulData.getInstance().main.chatWindow.chatLower.addHistoryMessageForGroup(
						// tmpMsg.getSender(),
						// tmpMsg.getContent(), tmpMsg.getDate(),
						// IdentityUser.getIdentityUser().getSelectedFriendOrGroup());
						// } else if (tmpMsg.isUserSender()) {
						// UsefulData.getInstance().main.chatWindow.chatLower
						// .addHistoryMessage(
						// IdentityUser.userName,
						// tmpMsg.getContent(),
						// tmpMsg.getDate());
						//
						// } else
						// UsefulData.getInstance().main.chatWindow.chatLower
						// .addHistoryMessage(IdentityUser
						// .getIdentityUser().getSelectedFriendOrGroup(),
						// tmpMsg.getContent(), tmpMsg
						// .getDate());
						//
						// }
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if (!isGroup) {
					UsefulData.getInstance().main.clientControl
							.send(new Message(Constant.NOTIFICATION_LIST,
									IdentityUser.getIdentityUser().userName,
									"clear", IdentityUser.getIdentityUser()
											.getSelectedFriendOrGroup(), -1));

					if (UsefulData.getInstance().find(
							IdentityUser.getIdentityUser()
									.getSelectedFriendOrGroup()).isOnline) {
						friendListPanel.chatFrame.menuTopPanel.btnchatTransfer
								.setVisible(true);
					} else {
						friendListPanel.chatFrame.menuTopPanel.btnchatTransfer
								.setVisible(false);
					}
				}
				UsefulData.getInstance().selectedPanel = InfoPanel.this;
			}
		});

	}

	public void setDefault() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				selected = false;
				removeAll();
				add(defaultBackground);
				repaint();
				validate();
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});

	}

	public void setNotified() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				notified = true;
				if (!selected) {
					removeAll();
					add(notifiedBackground);
					repaint();
					validate();
					setCursor(new Cursor(Cursor.HAND_CURSOR));

				}
			}
		});

	}

	public void select() {
		selected = true;
	}

	private JPanel makeContentPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(null);
		panel.setBounds(0, 0, 274, 53);

		JLabel avater = new JLabel(avaterImg);
		avater.setBounds(5, 12, 35, 32);
		panel.add(avater);

		if (!isGroup) {
			JLabel logoBlue = new JLabel(new ImageIcon(
					ChatFrame.class.getResource("/resources/logo_blue.png")));
			JLabel logoWhite = new JLabel(new ImageIcon(
					ChatFrame.class.getResource("/resources/logo_white.png")));
			logoBlue.setBounds(45, 12, 35, 32);
			logoWhite.setBounds(45, 12, 35, 32);

			if (online) {
				panel.add(logoBlue);
			} else {
				panel.add(logoWhite);

			}

		}

		JLabel deptLbl = new JLabel(dept);
		deptLbl.setBounds(90, 22, 180, 32);
		panel.add(deptLbl);
		JLabel userNameLbl = new JLabel(userName);
		userNameLbl.setBounds(90, 0, 180, 32);
		panel.add(userNameLbl);
		return panel;
	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(Message msg) {
		String m[] = msg.content.split(Message.SECOND_LEVEL_SEPERATOR);

		// friendListPanel.chatFrame.chatLower.clearHistory();
		for (String str : m) {

			String s[] = str.split(Message.THIRD_LEVEL_SEPERATOR);
			if (s.length > 1) {

				UsefulData.getInstance().main.chatWindow.chatLower
						.addHistoryMessage(s[0], s[2],
								java.sql.Timestamp.valueOf(s[3]));
				// if (s[0].equals(IdentityUser.userName))
				// UsefulData.getInstance().main.tempHistory.addTempHistory(
				// s[1], s[2], java.sql.Timestamp.valueOf(s[3]), true);
				// else
				// UsefulData.getInstance().main.tempHistory
				// .addTempHistory(s[0], s[2],
				// java.sql.Timestamp.valueOf(s[3]), false);

			}

		}

	}

}
