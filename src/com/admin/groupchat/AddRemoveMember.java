package com.admin.groupchat;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.admin.CreateAccount;
import com.model.dutta.IdentityUser;
import com.model.dutta.MeToWhom;
import com.modification.jpanel.InputField;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class AddRemoveMember extends JDialog implements CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel = new JPanelX("/resources/adminpanelback.png", this);
	private JButton crossButton;
	AddRemoveMember frm = this;
	private final JButton button = new JButton("");
	private final JPanel panel = new JPanel();
	private final JList<String> listMember = new JList();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JLabel lblNewLabel = new JLabel("Group Name");
	private final JLabel lblNewLabel_1 = new JLabel("Member List");
	private JComboBox<String> comboAddMember;
	private final JLabel lblAddNewMember = new JLabel("Add new Member");
	private final JLabel lblReport = new JLabel("");
	private String[] groupName = null;
	private JComboBox<String> comboGroupName;
	private JComboBox<String> comboDummy;
	private ArrayList<String> removeList;
	private String addedItem;
	private boolean internal = false;

	public AddRemoveMember(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		{

			button.setIcon(new ImageIcon(AddRemoveMember.class
					.getResource("/resources/cross.png")));
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setFocusable(false);
			button.setRolloverIcon(new ImageIcon(AddRemoveMember.class
					.getResource("/resources/cra.png")));
			button.setBounds(365, 10, 20, 20);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();

				}
			});

		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(back_panel);
		back_panel.setFocusable(false);
		setResizable(false);
		this.setSize(400, 450);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);

		back_panel.setLayout(null);

		back_panel.add(button);
		panel.setOpaque(false);
		panel.setBounds(0, 111, 400, 328);

		back_panel.add(panel);
		panel.setLayout(null);

		scrollPane.setBounds(175, 133, 197, 91);

		panel.add(scrollPane);
		scrollPane.setViewportView(listMember);

		listMember.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;
			String[] values = new String[] { "" };

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});

		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(35, 27, 111, 50);

		panel.add(lblNewLabel);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(35, 133, 99, 50);

		panel.add(lblNewLabel_1);

		JButton btnRemoveMember = new JButton("Remove Member");
		btnRemoveMember.setBounds(175, 247, 197, 36);
		btnRemoveMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (comboGroupName.getSelectedIndex() != -1
						&& listMember.getSelectedValuesList().size() != 0) {
					removeList = (ArrayList<String>) listMember
							.getSelectedValuesList();
					String str = "";
					for (String string : removeList) {
						comboAddMember.addItem(string);
						internal = true;
						str += string + Message.SECOND_LEVEL_SEPERATOR;
					}
					comboAddMember.setSelectedIndex(-1);
					UsefulData.getInstance().main.clientControl.send(
							AddRemoveMember.this, new Message(
									Constant.REMOVE_MEMBER_FROM_GROUP,
									IdentityUser.userName, comboGroupName
											.getSelectedItem().toString(), str,
									-1));
				} else {
					lblReport.setText("Please select member from member list");
				}

			}
		});
		panel.add(btnRemoveMember);

		lblAddNewMember.setVerticalAlignment(SwingConstants.TOP);
		lblAddNewMember.setForeground(Color.WHITE);
		lblAddNewMember.setFont(new Font("Arial", Font.PLAIN, 14));
		lblAddNewMember.setBounds(35, 77, 109, 36);

		panel.add(lblAddNewMember);
		lblReport.setFont(new Font("Arial", Font.PLAIN, 12));
		lblReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblReport.setForeground(new Color(204, 204, 0));
		lblReport.setBounds(35, 294, 337, 36);

		panel.add(lblReport);

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

		comboDummy = new JComboBox<>();
		comboDummy.setBounds(175, 39, 197, 26);
		panel.add(comboDummy);
		initComboUserName();
		initComboMemberList();
		initCrossButton();
		validate();
		revalidate();
	}

	public void initComboMemberList() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (comboAddMember != null)
					comboAddMember.setVisible(false);
				String list[] = { "" };
				comboAddMember = new JComboBox<>(list);
				comboAddMember.setBounds(175, 76, 197, 26);
				comboAddMember.setSelectedIndex(-1);
				comboAddMember.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!internal) {

							if (comboAddMember.getSelectedIndex() != -1
									&& comboGroupName.getSelectedIndex() != -1) {
								addedItem = comboAddMember.getSelectedItem()
										.toString();
								UsefulData.getInstance().main.clientControl
										.send(AddRemoveMember.this,
												new Message(
														Constant.ADD_MEMBER_TO_GROUP,
														IdentityUser.userName,
														comboGroupName
																.getSelectedItem()
																.toString(),
														comboAddMember
																.getSelectedItem()
																.toString(), -1));
								comboAddMember.removeItem(comboAddMember
										.getSelectedItem().toString());
								comboAddMember.setSelectedIndex(-1);

							} else {
								lblReport.setText("Please select a group");
							}
						}
						internal = false;
					}
				});
				panel.add(comboAddMember);

				comboAddMember.repaint();
				comboAddMember.validate();
				panel.add(comboAddMember);
				panel.validate();
				panel.revalidate();
			}
		});
	}

	public void initComboUserName() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (comboGroupName != null)
					comboGroupName.setVisible(false);
				comboGroupName = new JComboBox<>(
						UsefulData.getInstance().groupList);
				comboDummy.setVisible(false);
				comboGroupName.setBounds(175, 39, 197, 26);
				comboGroupName.setSelectedIndex(-1);
				comboGroupName.setVisible(true);

				comboGroupName.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (comboGroupName.getSelectedIndex() != -1) {

							UsefulData.getInstance().main.clientControl.send(
									AddRemoveMember.this, new Message(
											Constant.GET_MEMBERS_FROM_GROUP,
											IdentityUser.userName,
											comboGroupName.getSelectedItem()
													.toString(), "SERVER", -1));
						}
					}
				});
				comboGroupName.repaint();
				comboGroupName.validate();
				panel.add(comboGroupName);
				panel.validate();
				panel.revalidate();
			}
		});
	}

	private void initCrossButton() {
		crossButton = new JButton(new ImageIcon(
				CreateAccount.class.getResource("/resources/cross.png")));
		crossButton.setBounds(416, 11, 24, 20);
		crossButton.setRolloverIcon(new ImageIcon(CreateAccount.class
				.getResource("/resources/cra.png")));
		crossButton.setBorderPainted(false);
		crossButton.setContentAreaFilled(false);
		crossButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						setVisible(false);

					}
				});

			}
		});
	}

	@Override
	public void done(String exception) {

	}

	public void setMemberListModel(final String arg[]) {
		listMember.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;
			String[] values = arg;

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
	}

	@Override
	public void done(Message msg) {
		if (msg.type == Constant.GET_MEMBERS_FROM_GROUP) {
			if (msg.sender.equalsIgnoreCase("true")) {
				String gNameAndUser[] = msg.content
						.split(Message.SECOND_LEVEL_SEPERATOR);
				String userWithPermission[] = gNameAndUser[1]
						.split(Message.THIRD_LEVEL_SEPERATOR);
				
				final String sp[] = gNameAndUser[0]
						.split(Message.THIRD_LEVEL_SEPERATOR);
				UsefulData.getInstance().groupAndMembers.put(comboGroupName
						.getSelectedItem().toString(),
						new ArrayList<>(Arrays.asList(sp)));
				setMemberListModel(sp);
				comboAddMember.removeAll();
				ArrayList<String> list = new ArrayList<>();
				for (String whom : userWithPermission) {
					list.add(whom);

				}
				for (String string : sp) {
					list.remove(string);
				}

				Collections.sort(list);
				for (String string : list) {
					comboAddMember.addItem(string);
				}
				comboAddMember.removeItem("");
				comboAddMember.removeItem("");
				listMember.repaint();
				listMember.validate();
				listMember.revalidate();
			} else {
				lblReport.setText(msg.content);
			}

		} else if (msg.type == Constant.ADD_MEMBER_TO_GROUP) {
			if (msg.content.equalsIgnoreCase("true")) {

				ArrayList<String> l = UsefulData.getInstance().groupAndMembers
						.get(comboGroupName.getSelectedItem().toString());
				l.add(msg.sender);
				UsefulData.getInstance().groupAndMembers.put(comboGroupName
						.getSelectedItem().toString(), l);

				String s[] = l.toArray(new String[l.size()]);
				setMemberListModel(s);
				lblReport.setText("Member Added to Group");
			} else {
				lblReport.setText("Error while adding to group");
			}
		} else if (msg.type == Constant.REMOVE_MEMBER_FROM_GROUP) {
			if (msg.content.equalsIgnoreCase("true")) {

				for (String e : removeList) {
					UsefulData.getInstance().groupAndMembers.get(
							comboGroupName.getSelectedItem().toString())
							.remove(e);
				}

				String s[] = UsefulData.getInstance().groupAndMembers.get(
						comboGroupName.getSelectedItem().toString()).toArray(
						new String[UsefulData.getInstance().groupAndMembers
								.get(comboGroupName.getSelectedItem()
										.toString()).size()]);
				setMemberListModel(s);
				lblReport.setText("Member Remove from group "
						+ comboGroupName.getSelectedItem().toString());

			} else {
				lblReport.setText("Error while adding to group "
						+ comboGroupName.getSelectedItem().toString());
			}
		}
	}

}
