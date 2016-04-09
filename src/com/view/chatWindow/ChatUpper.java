package com.view.chatWindow;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.admin.MyProfile;
import com.admin.groupchat.GroupEntry;
import com.model.dutta.IdentityUser;
import com.socket.CallBackable;
import com.socket.ClientControl;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class ChatUpper extends JPanel implements CallBackable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ImageIcon avater;
	private Dimension defaultSize = new Dimension(600, 50);
	private Dimension maxSize = new Dimension(Toolkit.getDefaultToolkit()
			.getScreenSize().width - 330, 50);
	private JLabel avaterLabel;
	private JLabel separatero;
	public JLabel nameLabel;
	private JButton callButton;
	public JButton plusButton;
	private JButton historyButton;
	private boolean isOnline = false;
	public JMenuItem chatTransferMenuItem;
	public JMenu addPeopleMenuItem;
	public JMenuItem sendFileMenuItem;
	private JMenuItem createGroup;

	public ChatUpper(final ChatFrame w, ImageIcon av, String n) {
		setLayout(null);
		setLocation(304, 51);
		setOpaque(false);
		separatero = new JLabel(new ImageIcon(
				ChatUpper.class.getResource("/resources/seperator.png")));
		separatero.setBounds(0, defaultSize.height - 4, Toolkit
				.getDefaultToolkit().getScreenSize().width - 330, 2);
		this.avater = av;
		avaterLabel = new JLabel(avater);
		avaterLabel.setBounds(0, 10, 35, 32);

		this.name = n;
		nameLabel = new JLabel(IdentityUser.getIdentityUser().getSelectedFriendOrGroup());
		nameLabel.setBounds(45, 10, 90, 32);
		// >>>>>>>>>>>Call Button<<<<<<<<<<<<<<<<<
		callButton = new JButton(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/call.png")));
		callButton.setBounds(120, 9, 110, 37);
		callButton.setBorderPainted(false);
		callButton.setContentAreaFilled(false);
		;
		callButton.setRolloverIcon(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/callr.png")));
		callButton.setPressedIcon(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/callp.png")));
		add(callButton);

		// >>>>>>>>>>>>>>Plus Button<<<<<<<<<<<<<<<<<<<<<<,

		final JPopupMenu pop = new JPopupMenu();
		sendFileMenuItem = new JMenuItem("Send File...");
		sendFileMenuItem.setEnabled(false);
		sendFileMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UsefulData.getInstance().sendFileWindowShow();
			}

		});

		chatTransferMenuItem = new JMenuItem("Chat Transfer...");
		addPeopleMenuItem = new JMenu("Add People to Group...");
		chatTransferMenuItem.setEnabled(false);
		if (UsefulData.getInstance().haveFilePermission())
			pop.add(sendFileMenuItem);
		if (UsefulData.getInstance().haveChatTransferPermission())
			pop.add(chatTransferMenuItem);
		if (UsefulData.getInstance().haveGroupPermission())
			pop.add(addPeopleMenuItem);
		
		createGroup = new JMenuItem("Create Group...");
		if(IdentityUser.getIdentityUser().userDetails.userType==1){
			pop.addSeparator();
			pop.add(createGroup);
		}
		

		plusButton = new JButton(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/plus.png")));
		plusButton.setBounds(236, 9, 37, 37);
		plusButton.setBorderPainted(false);
		plusButton.setContentAreaFilled(false);
		plusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (UsefulData.getInstance().selectedPanel != null) {
					sendFileMenuItem.setEnabled(UsefulData.getInstance().selectedPanel.online);
					chatTransferMenuItem.setEnabled(UsefulData.getInstance().selectedPanel.online);
				}
				JMenuItem []groupMenuItem = new  JMenuItem[UsefulData.getInstance().groupList.length];
				addPeopleMenuItem.removeAll();
				for(int i=0; i<UsefulData.getInstance().groupList.length; i++){
					groupMenuItem[i] = new JMenuItem(UsefulData.getInstance().groupList[i]);
					addPeopleMenuItem.add(groupMenuItem[i]);
					groupMenuItem[i].addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							System.out.println("Group Selected: "+arg0.getActionCommand());
							
						}
					});
				}
				pop.show(ChatUpper.this, 240, 37);
				// new GroupEntry(w).setVisible(true);

			}
		});
		plusButton.setRolloverIcon(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/plusr.png")));
		plusButton.setPressedIcon(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/plusp.png")));
		add(plusButton);
		// >>>>>>>>>>>>>>>>history Button>>>>>>>>>>>>>>>>

		historyButton = new JButton(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/history.png")));
		historyButton.setSize(212, 21);
		historyButton.setBorderPainted(false);
		historyButton.setContentAreaFilled(false);
		;
		historyButton.setRolloverIcon(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/historyr.png")));
		historyButton.setPressedIcon(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/historyp.png")));

		add(historyButton);

		setDefatult();

		add(nameLabel);
		add(avaterLabel);
		add(separatero);

		plusButton
				.setVisible(UsefulData.getInstance().userPermissions.groupeMessage);
		callButton
				.setVisible(UsefulData.getInstance().userPermissions.calltransfer);

	}

	public void setName(String name, boolean online) {

		IdentityUser.getIdentityUser().setSelectedFriendOrGroup(name);
	}

	public void setDefatult() {
		historyButton.setLocation(defaultSize.width - 162, 16);
		setSize(defaultSize);
		repaint();
	}

	public void setMax() {
		historyButton.setLocation(maxSize.width - 162, 16);
		setSize(maxSize);
		repaint();
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
