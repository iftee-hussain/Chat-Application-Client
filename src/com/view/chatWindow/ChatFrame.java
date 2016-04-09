package com.view.chatWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.admin.MyProfile;
import com.model.dutta.IdentityUser;
import com.model.dutta.MeToWhom;
import com.model.dutta.UserPermissions;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.GeneralErrorReportable;
import com.utility.dutta.UsefulData;

public class ChatFrame extends JFrame implements BackgroundWorkable,
		CallBackable, GeneralErrorReportable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ChatFrame frame;
	public int state;
	public ArrayList<MeToWhom> chatwiths = new ArrayList<>();
	BasePanel basePanel = new BasePanel();
	TopBorderPanel topBorderPanel = new TopBorderPanel(this);
	MenuTopPanel menuTopPanel = new MenuTopPanel(this);
	private final Dimension defaultSize = new Dimension(925, 575);
	public ContactsPanel contactsPanel = new ContactsPanel(this);
	public GroupPanel groupPanel = new GroupPanel();
	private JPanel contactsOrGroup = new JPanel();
	Dimension dimMaxFriendListPanel = new Dimension(274, Toolkit
			.getDefaultToolkit().getScreenSize().height - 82);
	Dimension dimDefaultFriendListPanel = new Dimension(274,
			defaultSize.height - 82);
	public boolean maxSize = false;
	private JButton minButton;
	private JButton maxButton;
	private JButton crossButton;
	public ChatUpper chatUpper;
	public ChatLower chatLower;
	public HomePanel homePanel;
	private boolean toggle = true;
	private JToggleButton contactsButton;
	private JToggleButton groupButton;
	private boolean isContactSelected = true;

	/**
	 * /** Create the frame.
	 */
	public ChatFrame() {

		frame = this;
		try {
			// for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			// {
			// if ("Nimbus".equals(info.getName())) {
			// UIManager.setLookAndFeel(info.getClassName());
			// break;
			// }
			// }
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception e) {
			// If Nimbus is not available, fall back to cross-platform
			try {
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {
				// not worth my time
			}

		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setSize(defaultSize);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		basePanel.setBorder(new MatteBorder(1, 1, 1, 1,
				(Color) SystemColor.textHighlight));
		setContentPane(basePanel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowIconified(e);
				state = 1;
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowDeiconified(e);
				state = 0;
			}
		});

		JLabel duttaLogo = new JLabel("");
		duttaLogo.setIcon(new ImageIcon(ChatFrame.class
				.getResource("/resources/duttalogo.png")));
		duttaLogo.setBounds(10, 0, 32, 27);
		basePanel.add(duttaLogo);

		JLabel duttatex = new JLabel("");
		duttatex.setIcon(new ImageIcon(ChatFrame.class
				.getResource("/resources/duttatop.png")));
		duttatex.setBounds(52, 0, 115, 27);
		basePanel.add(duttatex);
		// >>>>>>>Min Button<<<<<<<<<<<<<<
		minButton = new JButton(new ImageIcon(
				ChatFrame.class.getResource("/resources/min.png")));
		minButton.setContentAreaFilled(false);
		minButton.setBorderPainted(false);
		minButton.setRolloverIcon(new ImageIcon(ChatFrame.class
				.getResource("/resources/minr.png")));
		minButton.setPressedIcon(new ImageIcon(ChatFrame.class
				.getResource("/resources/minp.png")));
		minButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setState(JFrame.ICONIFIED);
			}
		});
		basePanel.add(minButton);

		// >>>>>>>>Max Button<<<<<<<<<<<<<<<<<,
		maxButton = new JButton(new ImageIcon(
				ChatFrame.class.getResource("/resources/max.png")));
		maxButton.setContentAreaFilled(false);
		maxButton.setBorderPainted(false);
		maxButton.setRolloverIcon(new ImageIcon(ChatFrame.class
				.getResource("/resources/maxr.png")));
		maxButton.setPressedIcon(new ImageIcon(ChatFrame.class
				.getResource("/resources/maxp.png")));
		maxButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (maxSize == true) {

					setDefaultSize();
				} else {
					setMaxSize();
				}
				maxSize = !maxSize;

			}
		});
		basePanel.add(maxButton);

		// >>>>>>>Cross Button<<<<<<<<<<<<<<<<<<<<<<<<<
		crossButton = new JButton(new ImageIcon(
				ChatFrame.class.getResource("/resources/cross.png")));
		crossButton.setContentAreaFilled(false);
		crossButton.setBorderPainted(false);
		crossButton.setRolloverIcon(new ImageIcon(ChatFrame.class
				.getResource("/resources/crossr.png")));
		crossButton.setPressedIcon(new ImageIcon(ChatFrame.class
				.getResource("/resources/crossp.png")));
		crossButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				UsefulData.getInstance().main.signOut();
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (UsefulData.getInstance().upload != null)
							UsefulData.getInstance().upload.stopUploader();
						UsefulData.getInstance().upload = null;

						if (UsefulData.getInstance().download != null)
							UsefulData.getInstance().download.stopDownloader();
						UsefulData.getInstance().download = null;
						frame.dispose();
						System.exit(0);

					}
				});

			}
		});
		basePanel.add(crossButton);

		basePanel.add(topBorderPanel);
		basePanel.add(menuTopPanel);
		// User info panel
		JPanel panel = new JPanel();
		panel.setBounds(1, 27, 275, 55);
		panel.setBackground(new Color(193, 217, 241));
		panel.setLayout(null);
		JLabel img = new JLabel(new ImageIcon(
				ChatFrame.class.getResource("/resources/friend.png")));
		img.setBounds(5, 12, 35, 32);
		img.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				new MyProfile(UsefulData.getInstance().main.chatWindow)
						.setVisible(true);
			}
		});

		JLabel name = new JLabel(IdentityUser.getIdentityUser().userName);
		name.setBounds(60, 12, 200, 32);
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				new MyProfile(UsefulData.getInstance().main.chatWindow)
						.setVisible(true);
			}
		});
		panel.add(img);
		panel.add(name);
		basePanel.add(panel);
		// /Contacts Or Group

		// Contacts Button
		contactsOrGroup.setBounds(1, 82, 275, 50);
		contactsOrGroup.setLayout(null);

		contactsButton = new JToggleButton("Contacts", new ImageIcon(
				ChatFrame.class.getResource("/resources/cont.png")));
		contactsButton.setFont(new Font("Arial", Font.PLAIN, 14));

		contactsButton.setBounds(0, 0, 137, 50);
		contactsButton.setContentAreaFilled(false);
		contactsButton.setBackground(Color.WHITE);
		contactsButton.setOpaque(true);
		contactsButton.setHorizontalTextPosition(JButton.RIGHT);
		contactsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!isContactSelected) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							groupButton.setBackground(new Color(220, 220, 220));
							contactsButton.setBackground(Color.white);

							isContactSelected = true;
							contactsPanel.setVisible(true);
							groupPanel.setVisible(false);
							validate();

						}
					});

				}

			}
		});
		// Group Button

		groupButton = new JToggleButton(" Group", new ImageIcon(
				ChatFrame.class.getResource("/resources/group.png")));
		groupButton.setFont(new Font("Arial", Font.PLAIN, 14));
		groupButton.setBounds(137, 0, 137, 50);
		groupButton.setContentAreaFilled(false);
		groupButton.setBackground(new Color(220, 220, 220));
		groupButton.setOpaque(true);
		groupButton.setHorizontalTextPosition(JButton.RIGHT);
		groupButton.add(contactsButton);
		groupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isContactSelected) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							contactsButton.setBackground(new Color(220, 220,
									220));
							groupButton.setBackground(Color.white);
							isContactSelected = false;
							groupPanel.loadfriend();
							groupPanel.setVisible(true);
							contactsPanel.setVisible(false);
							basePanel.validate();

						}
					});

				}
			}
		});
		contactsOrGroup.add(contactsButton);
		contactsOrGroup.add(groupButton);
		add(contactsOrGroup);

		groupPanel.setBounds(1, 140, 274, 500);
		groupPanel.setPreferredSize(new Dimension(275, 500));
		groupPanel.setBackground(new Color(200, 220, 241));
		groupPanel.setVisible(false);
		basePanel.add(groupPanel);
		// Friend List Panel
		// ///////GROUP/////////////////////////

		contactsPanel.setBounds(1, 140, 274, 500);
		contactsPanel.setPreferredSize(new Dimension(275, 500));
		contactsPanel.setBackground(new Color(200, 220, 241));
		basePanel.add(contactsPanel);

		chatUpper = new ChatUpper(this, new ImageIcon(
				ChatFrame.class.getResource("/resources/friend.png")),
				"Rajib Khan");
		chatLower = new ChatLower();

		chatLower.setVisible(false);
		chatUpper.setVisible(false);
		add(chatLower);

		add(chatUpper);

		homePanel = new HomePanel();
		//add(homePanel);

		UsefulData.getInstance().main.clientControl.send(ChatFrame.this,
				new Message(Constant.FILTERED_TEXT, IdentityUser.userName, "",
						"GET", -1));
		setDefaultSize();
		UsefulData.getInstance().main.clientControl.send(new Message(
				Constant.GET_GROUPS_NAMES, IdentityUser.userName, "",
				IdentityUser.getIdentityUser().userDetails.userType + "", -1)); // For
																				// group
		// name list
		UsefulData.getInstance().main.clientControl.send(new Message(
				Constant.GET_GROUP_NOTIFICATION, IdentityUser.userName, "", "",
				-1));
	}

	void setDefaultSize() {
		frame.setSize(defaultSize);
		frame.setLocationRelativeTo(null);
		crossButton.setBounds(883, 0, 32, 27);
		maxButton.setBounds(855, 0, 32, 27);
		minButton.setBounds(827, 0, 32, 27);
		topBorderPanel.setSize(925, 27);
		menuTopPanel.setBounds(275, 27, 648, 27);
		basePanel.setDefault();
		homePanel.setDefault();

		contactsPanel.setSize(dimDefaultFriendListPanel);
		contactsPanel.setPreferredSize(dimDefaultFriendListPanel);
		contactsPanel.setDefault();

		groupPanel.setSize(dimDefaultFriendListPanel);
		groupPanel.setPreferredSize(dimDefaultFriendListPanel);
		groupPanel.setDefault();

		chatUpper.setDefatult();
		chatLower.setDefatult();
		topBorderPanel.setDefault();
		validate();

	}

	void setMaxSize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		minButton.setBounds(dim.width - 100, 0, 32, 27);
		maxButton.setBounds(dim.width - 68, 0, 32, 27);
		crossButton.setBounds(dim.width - 36, 0, 32, 27);
		topBorderPanel.setSize(dim.width, 27);
		menuTopPanel.setBounds(275, 27, dim.width, 27);
		basePanel.setMaximum();
		contactsPanel.setSize(dimMaxFriendListPanel);
		contactsPanel.setPreferredSize(dimMaxFriendListPanel);
		contactsPanel.setMax();
		groupPanel.setSize(dimMaxFriendListPanel);
		groupPanel.setPreferredSize(dimMaxFriendListPanel);
		groupPanel.setMax();
		topBorderPanel.setMaximum();
		chatUpper.setMax();
		chatLower.setMax();
		homePanel.setMax();
		frame.setSize(dim);
		frame.setLocationRelativeTo(null);
		validate();
	}

	@Override
	public void done(Message msg) {
		if (msg.type == Constant.UPLOAD_RESPONSE) {
			if (msg.content.equalsIgnoreCase("no")) {
				chatLower.addHistoryMessage(msg.sender,
						"rejected file request", Calendar.getInstance()
								.getTime());
				// UsefulData.getInstance().main.tempHistory.addTempHistory(
				// // msg.sender, "rejected file request", Calendar
				// .getInstance().getTime(), true);
			} else {
				chatLower.addHistoryMessage(msg.sender, "Download complete.",
						Calendar.getInstance().getTime());
				// UsefulData.getInstance().main.tempHistory.addTempHistory(
				// msg.sender, "Download complete.", Calendar
				// .getInstance().getTime(), true);
			}
		} else if (msg.type == Constant.FILTERED_TEXT) {
			if (msg.sender.equalsIgnoreCase("GET")
					&& !msg.recipient.equalsIgnoreCase("ERROR")) {
				UsefulData.getInstance().filteredText = msg.content;
			}
		}

	}

	@Override
	public void done(String exception) {

	}

	@Override
	public void showError(String error) {

		// Error Handling goes here
	}

	@Override
	public Long donInBackground() {
		return null;
	}

	@Override
	public void finished() {
		// TODO Auto-generated method stub

	}

	public void updateSpecificUserPermission(Message msg) {
		final String[] userPchat = msg.content
				.split(Message.THIRD_LEVEL_SEPERATOR);

		String[] users = userPchat[0].split(Message.SECOND_LEVEL_SEPERATOR);

		if (msg.type == Constant.SPECIFIC_USER_PERMISSION) {

			boolean permission[] = new boolean[10];
			for (int i = 0; i < 10; i++) {
				permission[i] = Boolean.parseBoolean(users[i]);
			}

			UserPermissions leftPermissions = new UserPermissions(
					IdentityUser.getIdentityUser().userName,
					Boolean.parseBoolean(users[0]),
					Boolean.parseBoolean(users[1]),
					Boolean.parseBoolean(users[2]),
					Boolean.parseBoolean(users[3]),
					Boolean.parseBoolean(users[4]),
					Boolean.parseBoolean(users[5]),
					Boolean.parseBoolean(users[6]),
					Boolean.parseBoolean(users[7]),
					Boolean.parseBoolean(users[8]),
					Boolean.parseBoolean(users[9]));
			UsefulData.getInstance().userPermissions = leftPermissions;
			if (userPchat.length == 2) {
				String[] rusers = userPchat[1]
						.split(Message.SECOND_LEVEL_SEPERATOR);
				for (int i = 0; i < rusers.length; i = i + 2) {

					chatwiths.add(new MeToWhom(rusers[i], Boolean
							.parseBoolean(rusers[i + 1])));
				}
				UsefulData.getInstance().chatwiths = chatwiths;
			}

			contactsPanel.refresh();
			groupPanel.loadfriend();
		}
	}
}
