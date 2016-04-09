package com.admin.groupchat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.admin.CreateAccount;
import com.model.dutta.IdentityUser;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class GroupEntry extends JDialog implements CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel = new JPanelX("/resources/adminpanelback.png", this);
	private JButton crossButton;
	GroupEntry frm = this;
	private final JButton button = new JButton("");
	private final JPanel panel = new JPanel();
	private final JList<String> listGroup = new JList<String>();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JLabel lblNewLabel_1 = new JLabel("GROUP");
	private final JLabel lblReport = new JLabel("");
	private final JButton btnEntry = new JButton("ENTRY");

	public GroupEntry(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		{

			button.setIcon(new ImageIcon(GroupEntry.class
					.getResource("/resources/cross.png")));
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setFocusable(false);
			button.setRolloverIcon(new ImageIcon(GroupEntry.class
					.getResource("/resources/cra.png")));
			button.setBounds(365, 10, 20, 20);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();

				}
			});

			setButtons();
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
		scrollPane.setBounds(40, 53, 325, 192);

		panel.add(scrollPane);
		listGroup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listGroup);

		setModelForList();

		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(145, 11, 99, 50);

		panel.add(lblNewLabel_1);

		JButton btnCancel = new JButton("CANCEL");
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCancel.setBounds(264, 269, 99, 36);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel.add(btnCancel);
		lblReport.setFont(new Font("Arial", Font.PLAIN, 12));
		lblReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblReport.setForeground(new Color(204, 204, 0));
		lblReport.setBounds(35, 294, 337, 36);

		panel.add(lblReport);
		btnEntry.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEntry.setBounds(145, 269, 99, 36);
		btnEntry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (listGroup.getSelectedIndex() != -1) {
					IdentityUser.getIdentityUser().setSelectedFriendOrGroup(
							listGroup.getSelectedValue(), true);
					UsefulData.getInstance().main.chatWindow.chatLower.textArea
							.setText("");

					if (UsefulData.getInstance().main.tempHistory
							.isExists(IdentityUser.getIdentityUser()
									.getSelectedFriendOrGroup())) {
						UsefulData.getInstance().main.chatWindow.chatLower
								.clearHistory();
						try {
							// for (TempMessage tmpMsg :
							// UsefulData.getInstance().main.tempHistory
							// .getTempHistory(IdentityUser
							// .getIdentityUser().getSelectedFriendOrGroup())) {
							//
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
					} else
						UsefulData.getInstance().main.clientControl
								.send(new Message(Constant.GROUP_HISTORY,
										IdentityUser.userName, listGroup
												.getSelectedValue(), "SERVER",
										-1));
					dispose();
				} else {
					lblReport.setText("Please Select A Group");
				}

			}
		});
		panel.add(btnEntry);

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
		UsefulData.getInstance().main.clientControl
				.send(GroupEntry.this,
						new Message(Constant.GET_GROUPS_NAMES,
								IdentityUser.userName, "",
								IdentityUser.getIdentityUser()
										.getIdentityUser().userDetails.userType
										+ "", -1));
		initCrossButton();
	}

	private void setModelForList() {
		final String[] values;
		values = UsefulData.getInstance().groupList;
		listGroup.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});

		listGroup.setCellRenderer(new MyListRenderer());
		listGroup.setFixedCellHeight(30);
	}

	private void setButtons() {

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

	private class MyListRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(JList list,
				final Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);

			if (UsefulData.getInstance().isNotifiedGroup(value.toString())) {
				setBackground(Color.YELLOW);
			} else {
				setBackground(Color.WHITE);
			}

			if (isSelected) {
				setBackground(Color.BLUE);
				setForeground(Color.WHITE);
			}
			return (this);
		}
	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(Message msg) {
		if (msg.type == Constant.GET_GROUPS_NAMES) {
			setModelForList();
		}

	}
}
