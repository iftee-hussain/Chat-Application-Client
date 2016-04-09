package com.admin;

import java.awt.Color;
import java.awt.Cursor;
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
import com.utility.dutta.BackgroundWork;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.LoadingPanel;
import com.utility.dutta.UsefulData;
import com.view.chatWindow.FriendInfo;

public class AddFriend extends JDialog implements BackgroundWorkable,
		CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoadingPanel loadingPanel;
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

	public AddFriend(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		back_panel = new JPanelX("/resources/usermanage.png", this, true);
		loadingPanel = new LoadingPanel();
		loadingPanel.setLocation(191, 184);
		setContentPane(back_panel);

		back_panel.setLayout(null);

		back_panel.setFocusable(false);

		addMoveListener(back_panel);
		setResizable(false);
		this.setSize(500, 600);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);

		back_panel.add(loadingPanel);

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

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new BackgroundWork(com.admin.AddFriend.this).execute();

			}
		});

		table.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] { "Requested by", "Requested to", "Set" }));
		setTable();
		table.setBounds(485, 200, 400, 300);

		pane = new JScrollPane(table);
		pane.setBounds(50, 221, 400, 250);
		pane.setBorder(new LineBorder(new Color(0, 0, 0)));
		pane.setBackground(Color.WHITE);
		back_panel.add(pane);

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
							return Boolean.class;
						default:
							return Boolean.class;
						}
					}
				});

				JButton button = new JButton("");
				button.setIcon(new ImageIcon(AddFriend.class
						.getResource("/resources/accept.png")));
				button.setRolloverIcon(new ImageIcon(AddFriend.class
						.getResource("/resources/accept_.png")));
				button.setCursor(new Cursor(Cursor.HAND_CURSOR));
				button.setBorderPainted(false);
				button.setContentAreaFilled(false);
				button.setBounds(50, 480, 400, 39);
				back_panel.add(button);

				JButton btnReject = new JButton("");
				btnReject.setIcon(new ImageIcon(AddFriend.class
						.getResource("/resources/reject.png")));
				btnReject.setRolloverIcon(new ImageIcon(AddFriend.class
						.getResource("/resources/reject_.png")));
				btnReject.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnReject.setBorderPainted(false);
				btnReject.setContentAreaFilled(false);
				btnReject.setBounds(50, 520, 400, 39);
				back_panel.add(btnReject);
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setColumnIdentifiers(new Object[] { "Requested by",
						"Requested to", "Set" });
				setTable();

				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						int rowCount = table.getRowCount();

						String r = "";
						for (int i = 0; i < rowCount; i++) {
							Object x = table.getValueAt(i, 2);
							Object y = table.getValueAt(i, 0);
							Object z = table.getValueAt(i, 1);

							String si = new String((String) y);
							String sii = new String((String) z);
							boolean di = new Boolean((boolean) x)
									.booleanValue();
							if (di) {
								r = r + si + Message.SECOND_LEVEL_SEPERATOR
										+ sii + Message.THIRD_LEVEL_SEPERATOR;

							}

						}

						UsefulData.getInstance().main.clientControl
								.send(AddFriend.this, new Message(
										Constant.UPDATE_FRIEND_REQUEST,
										IdentityUser.userName, r, "Server", -1));

					}
				});

			}
		});

	}

	private void setTable() {
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);

		table.getColumnModel().getColumn(0).setPreferredWidth(180);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		// table.getColumnModel().getColumn(3).setPreferredWidth(50);
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
		if (msg.type == Constant.GET_FRIEND_REQ) {
			if (msg.sender.equals("true")) {
				loadingPanel.setVisible(false);
				final ArrayList<String[]> friend_reqs = new ArrayList<>();
				String[] row_data = msg.content
						.split(Message.SECOND_LEVEL_SEPERATOR);
				for (int i = 0; i < row_data.length; i++) {
					friend_reqs.add(row_data[i]
							.split(Message.THIRD_LEVEL_SEPERATOR));

				}
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {

						DefaultTableModel model = (DefaultTableModel) table
								.getModel();
						model.setColumnIdentifiers(new Object[] {
								"Requested by", "Requested to", "Set" });
						setTable();

						for (int i = 0; i < friend_reqs.size(); i++) {
							String[] data = friend_reqs.get(i);
							model.addRow(new Object[] { data[0], data[1], false });
						}

					}
				});
			}
		}

	}

	@Override
	public Long donInBackground() {
		UsefulData.getInstance().main.clientControl.send(
				com.admin.AddFriend.this, new Message(Constant.GET_FRIEND_REQ,
						IdentityUser.getIdentityUser().userName, "", "Server",
						-1));
		return null;
	}

	@Override
	public void finished() {
		// TODO Auto-generated method stub

	}
}