package com.view.chatWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
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

import com.admin.groupchat.GroupEntry;
import com.model.dutta.IdentityUser;
import com.model.dutta.MeToWhom;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class GroupPanel extends JPanel implements CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InfoPanel selectedPanel = null;
	private ArrayList<InfoPanel> groupListPanel = new ArrayList<>();
	private JScrollPanelForContacts mainGroupPanel;
//	public static void main(String []args){
//		JOptionPane.showMessageDialog(null, new GroupPanel());
//	}
	public GroupPanel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		setLayout(null);
		setBackground(Color.RED);

		mainGroupPanel = new JScrollPanelForContacts();
		this.add(mainGroupPanel);
	}
	public void loadfriend() {
		try {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					groupListPanel.clear();
					for (String info : UsefulData.getInstance().groupList) {
							InfoPanel infoPanel = makeFriendPanel(
									info);
							groupListPanel.add(infoPanel);
					}
					
					mainGroupPanel.clear();

					
					for (InfoPanel panel : groupListPanel) {
						panel.setSize(275, 53);
						mainGroupPanel.add(panel);
						
					}
					for (InfoPanel in : groupListPanel) {
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
		UsefulData.getInstance().main.chatWindow.chatUpper.setName(name, online);
	}

	private InfoPanel makeFriendPanel(String groupName) {
		// parameter --- name, dept, online, selected, avater
		final InfoPanel panel = new InfoPanel(GroupPanel.this, groupName, false);

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
			for (InfoPanel in : groupListPanel) {

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
		for (InfoPanel i : groupListPanel) {
			if (i.userName.equals(uname))
				return i;
		}
		return null;
	}

	public void setNotification(String content) {
//		String second[] = content.split(Message.SECOND_LEVEL_SEPERATOR);
//		for (String s : second) {
//			String third[] = s.split(Message.THIRD_LEVEL_SEPERATOR);
//			InfoPanel p = getRequiredPanel(third[0]);
//			if (p != null && third[1].equalsIgnoreCase("true"))
//				p.setNotified();
//
//		}
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
		System.out.println("Message Here: "+msg.toString());
//		if (msg.type == Constant.USER_LIST)
//			updateFriendList(msg.content);
//		else if (msg.type == Constant.NOTIFICATION_LIST) {
//			setNotification(msg.content);
//		}
	}

	public void setDefault() {
		mainGroupPanel.setSize(getSize());
	}

	public void setMax() {
		mainGroupPanel.setSize(getSize());

	}	
}
