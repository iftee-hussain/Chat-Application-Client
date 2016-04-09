package com.view.chatWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.model.dutta.IdentityUser;
import com.model.dutta.MeToWhom;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class ContactsPanel extends JPanel implements CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InfoPanel selectedPanel = null;
	ChatFrame chatFrame;
	private ArrayList<InfoPanel> friendListPanel;
	private JScrollPanelForContacts mainContactPanel;
	private JPanel filterPanel;
	private String oldList = new String();
	boolean isAllSelected = true;
	private int selected = 0;
	private boolean unCheckFriend = false;

	public ContactsPanel(ChatFrame cframe) {
		this.chatFrame = cframe;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		setLayout(null);
		mainContactPanel = new JScrollPanelForContacts();
		this.add(mainContactPanel);

		// Filter Panel
		filterPanel = new JPanel();
		filterPanel.setBorder(null);
		filterPanel.setBackground(Color.WHITE);
		filterPanel.setLayout(null);
		filterPanel.setSize(275, 30);

		PopupComboAllOrOnline allOrOnline = new PopupComboAllOrOnline();
		filterPanel.add(allOrOnline);

		PopupComboStaffOrCustomer staffOrCustomer = new PopupComboStaffOrCustomer();
		filterPanel.add(staffOrCustomer);

		// mainContactPanel.add(filterPanel);

		UsefulData.getInstance().main.clientControl.send(ContactsPanel.this,
				new Message(Constant.USER_LIST, IdentityUser.userName,
						"USER LIST", "SERVER", -1));
		

	}

	public void updateFriendList(String list) {

		if (!list.equals(oldList)) {
			ArrayList<FriendInfo> friendInfoList = new ArrayList<>();
			friendListPanel = new ArrayList<>();

			String user[] = list.split(Message.SECOND_LEVEL_SEPERATOR);
			for (String info : user) {
				String is[] = info.split(Message.THIRD_LEVEL_SEPERATOR);
				friendInfoList.add(new FriendInfo(is[0], is[1], is[2], Boolean
						.parseBoolean(is[3]), Integer.parseInt(is[4])));
			}
			UsefulData.getInstance().friendInfoList = friendInfoList;
			loadfriend(friendInfoList);
			oldList = list;
		}
	}

	public void refresh() {
		ArrayList<FriendInfo> friendInfoList = UsefulData.getInstance().friendInfoList;
		friendListPanel = new ArrayList<>();
		loadfriend(friendInfoList);
	}

	public void refresh(boolean flag) {
		unCheckFriend = flag;
		ArrayList<FriendInfo> friendInfoList = UsefulData.getInstance().friendInfoList;
		friendListPanel = new ArrayList<>();
		loadfriend(friendInfoList);
	}

	private boolean applyOnlineFilter(FriendInfo info) {
		if (!isAllSelected)
			return info.isOnline;
		return true;
	}

	private boolean applyUserTypeFilter(FriendInfo info) {

		if (selected == 0)
			return true;
		if (selected == 1 && info.userType == 4) {
			return true; // Customer
		} else if (selected == 2) {
			if (info.userType == 1 || info.userType == 2 || info.userType == 3) {
				return true;
			}
		}
		return false; // all
	}

	private boolean applyGeneralFilter(FriendInfo info) {
		if (IdentityUser.getIdentityUser().userName.equals(info.userName))
			return false;
		if (checkFriend(info.userName) || info.userType == 1
				|| IdentityUser.getIdentityUser().userDetails.userType == 1
				|| unCheckFriend)
			return true;
		return false;

	}

	public void loadfriend(final ArrayList<FriendInfo> friendInfoList) {
		try {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					friendListPanel.clear();
					for (FriendInfo info : friendInfoList) {
						if (applyOnlineFilter(info)
								&& applyGeneralFilter(info)
								&& applyUserTypeFilter(info)) {
							InfoPanel infoPanel = makeFriendPanel(
									info.userName, info.country, info.isOnline);
							friendListPanel.add(infoPanel);
						}
					}
					unCheckFriend = false;
					mainContactPanel.clear();
					mainContactPanel.add(filterPanel);

					for (InfoPanel panel : friendListPanel) {
						panel.setSize(275, 53);
						mainContactPanel.add(panel);
					}

					if (UsefulData.getInstance().main.clientControl != null)
						UsefulData.getInstance().main.clientControl.send(
								ContactsPanel.this, new Message(
										Constant.NOTIFICATION_LIST,
										IdentityUser.userName, "Notification",
										"SERVER", -1));
					for (InfoPanel in : friendListPanel) {

						if (UsefulData.getInstance().selectedPanel != null
								&& in.userName.equals(UsefulData.getInstance().selectedPanel.userName)) {
							in.setSelected();
						}

					}

					validate();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSelectedFriend(String name, boolean online) {
		IdentityUser.getIdentityUser().setSelectedFriendOrGroup(name);
		chatFrame.chatUpper.setName(name, online);
	}

	public void setSelectedGroup(String userName) {
		IdentityUser.getIdentityUser().setSelectedFriendOrGroup(userName);
	}

	private InfoPanel makeFriendPanel(String userName, String dept,
			boolean isOnline) {
		// parameter --- name, dept, online, selected, avater
		final InfoPanel panel = new InfoPanel(ContactsPanel.this, userName,
				dept, isOnline, false, new ImageIcon(
						ChatFrame.class.getResource("/resources/friend.png")));

		panel.setLayout(null);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				select(panel);

			}
		});
		panel.setPreferredSize(new Dimension(275, 55));
		return panel;
	}

	private void select(final InfoPanel panel) {
		InfoPanel ipanel = null;
		if (!UsefulData.getInstance().isGroupSelected) {
			for (InfoPanel in : friendListPanel) {

				if (UsefulData.getInstance().selectedPanel != null
						&& in.userName
								.equals(UsefulData.getInstance().selectedPanel.userName)) {
					ipanel = in;
				}

			}
		}
		if (panel != ipanel) {
			if (ipanel == null) {
				panel.setSelected();
				UsefulData.getInstance().selectedPanel = panel;
				selectedPanel = panel;
			} else {
				ipanel.setDefault();
				panel.setSelected();
				UsefulData.getInstance().selectedPanel = panel;
				selectedPanel = panel;
			}

		}
	}

	public void selectFriend(String name) {
		select(getRequiredPanel(name));
	}

	public InfoPanel getRequiredPanel(String uname) {
		for (InfoPanel i : friendListPanel) {
			if (i.userName.equals(uname))
				return i;
		}
		return null;
	}
	public boolean isOnline(String userName){
		InfoPanel panel = getRequiredPanel(userName);
		if(panel!=null&&panel.online){
			return true;
		}
		return false;
	}

	public void setNotification(String content) {
		String second[] = content.split(Message.SECOND_LEVEL_SEPERATOR);
		for (String s : second) {
			String third[] = s.split(Message.THIRD_LEVEL_SEPERATOR);
			InfoPanel p = getRequiredPanel(third[0]);
			if (p != null && third[1].equalsIgnoreCase("true"))
				p.setNotified();

		}
	}

	public void setNotification(String content, int i) {
		InfoPanel p = getRequiredPanel(content);
		if (p != null && content != IdentityUser.getIdentityUser().getSelectedFriendOrGroup())
			p.setNotified();

		if (UsefulData.getInstance().main.chatWindow.state == 1) {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
			Rectangle rect = defaultScreen.getDefaultConfiguration()
					.getBounds();

			final MiniNotification x = new MiniNotification();
			x.label.setText(p.userName);
			int xx = (int) rect.getMaxX() - x.getWidth() - 5;
			int yy = (int) rect.getMaxY() - x.getHeight() - 50;

			x.setLocation(xx, yy);

			x.setVisible(true);

			Timer t = new Timer();
			t.schedule(new TimerTask() {

				@Override
				public void run() {
					x.dispose();

				}
			}, 6000);

		}
	}

	@Override
	public void done(String exception) {

	}

	@Override
	public void done(Message msg) {
		if (msg.type == Constant.USER_LIST)
			updateFriendList(msg.content);
		else if (msg.type == Constant.NOTIFICATION_LIST) {
			setNotification(msg.content);
		}
	}

	public boolean checkFriend(String username) {
		for (MeToWhom e : UsefulData.getInstance().chatwiths) {
			if (e.chatWith.equals(username)) {
				return e.yesOrNo;
			}
		}
		return false;
	}

	public void setDefault() {
		mainContactPanel.setSize(getSize());
	}

	public void setMax() {
		mainContactPanel.setSize(getSize());

	}

	// ////////////////////Popup Combo All Or
	// Online//////////////////////////////
	class PopupComboAllOrOnline extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JPopupMenu pop;
		JCheckBoxMenuItem[] cbList;
		String[] menuList = { "All", "Online" };
		JLabel label = new JLabel("All");

		JLabel icoLabel = new JLabel(new ImageIcon(
				ContactsPanel.class.getResource("/resources/downarrow.png")));

		public PopupComboAllOrOnline() {

			ButtonGroup btnGroup = new ButtonGroup();
			setLayout(null);
			setBackground(Color.WHITE);
			setBounds(0, 0, 137, 30);
			pop = new JPopupMenu();
			cbList = new JCheckBoxMenuItem[menuList.length];
			for (int i = 0; i < menuList.length; i++) {
				cbList[i] = new JCheckBoxMenuItem(menuList[i]);
				cbList[i].addActionListener(PopupComboAllOrOnline.this);
				pop.add(cbList[i]);
				btnGroup.add(cbList[i]);
			}
			cbList[0].setSelected(true);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					pop.show(PopupComboAllOrOnline.this, 45, 30);
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					setBackground(new Color(217, 217, 217));
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					setBackground(Color.WHITE);
				}
			});
			label.setBounds(10, 0, 35, 30);
			icoLabel.setBounds(35, 0, 30, 30);

			add(label);
			add(icoLabel);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			label.setText(arg0.getActionCommand());
			if (arg0.getActionCommand().equals(menuList[0])
					&& cbList[0].isSelected()
					&& !ContactsPanel.this.isAllSelected) {
				isAllSelected = true;
				ContactsPanel.this.refresh(false);
			} else if (arg0.getActionCommand().equals(menuList[1])
					&& cbList[1].isSelected() && isAllSelected) {
				isAllSelected = false;
				ContactsPanel.this.refresh(false);

			}
		}
	}

	// ////////////////////PopupComboStaffOrCustomer
	class PopupComboStaffOrCustomer extends JPanel implements ActionListener {
		/**
* 
*/
		private static final long serialVersionUID = 1L;
		JPopupMenu pop;
		String[] menuList = { "All User", "User", "Office Staff" };
		JCheckBoxMenuItem[] cbList;
		JLabel label = new JLabel("All User");
		JLabel icoLabel = new JLabel(new ImageIcon(
				ContactsPanel.class.getResource("/resources/downarrow.png")));

		public PopupComboStaffOrCustomer() {

			setLayout(null);
			setBackground(Color.WHITE);
			setBounds(137, 0, 137, 30);
			pop = new JPopupMenu();
			ButtonGroup group = new ButtonGroup();
			cbList = new JCheckBoxMenuItem[menuList.length];
			for (int i = 0; i < menuList.length; i++) {
				cbList[i] = new JCheckBoxMenuItem(menuList[i]);
				cbList[i].addActionListener(PopupComboStaffOrCustomer.this);
				pop.add(cbList[i]);
				group.add(cbList[i]);
			}
			cbList[0].setSelected(true);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					pop.show(PopupComboStaffOrCustomer.this, 0, 30);
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					setBackground(new Color(217, 217, 217));
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					setBackground(Color.WHITE);
				}
			});
			label.setBounds(10, 0, 60, 30);
			icoLabel.setBounds(80, 0, 30, 30);

			add(label);
			add(icoLabel);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			label.setText(arg0.getActionCommand());
			if (arg0.getActionCommand().equals(menuList[0])
					&& cbList[0].isSelected() && selected != 0) {
				selected = 0;
				ContactsPanel.this.refresh(false);
			} else if (arg0.getActionCommand().equals(menuList[1])
					&& cbList[1].isSelected() && selected != 1) {
				selected = 1;
				ContactsPanel.this.refresh(false);

			} else if (arg0.getActionCommand().equals(menuList[2])
					&& cbList[2].isSelected() && selected != 2) {
				selected = 2;
				ContactsPanel.this.refresh(false);
			}

		}
	}

	public void clearSelection() {
		for (InfoPanel info : friendListPanel) {
			info.setDefault();
		}

	}

}
