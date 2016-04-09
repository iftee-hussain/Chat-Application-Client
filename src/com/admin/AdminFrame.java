package com.admin;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.admin.groupchat.ManageGroup;
import com.modification.jpanel.JPanelX;
import com.utility.dutta.UsefulData;

public class AdminFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel = new JPanelX("/resources/adminpanelback.png", this);
	private JButton crossButton;
	AdminFrame frm = this;
	private final JButton button = new JButton("");
	public JButton useradd = new JButton();
	public JButton btnAddfriend = new JButton();
	public JButton btnManageGroup = new JButton();
	public JButton msgfilter = new JButton();
	public JButton useraccess = new JButton();
	public JButton useramanage = new JButton();
	private ManageGroup manageGroup;
	private AddFriend addFriend;
	private CreateAccount createAccount;
	private UserAccess useraccessClass;
	public UserManage userManageClass;
	private MsgFilterWindow msgFilterWindow;
	private UserAdd addUser;

	public AdminFrame(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		{

			button.setIcon(new ImageIcon(AdminFrame.class
					.getResource("/resources/cross.png")));
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setFocusable(false);
			button.setRolloverIcon(new ImageIcon(AdminFrame.class
					.getResource("/resources/cra.png")));
			button.setBounds(365, 10, 20, 20);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					UsefulData.getInstance().main.destroyAdminWindow();

				}
			});
			setButtons();
		}
		{

		}
		useradd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addUser = new UserAdd(AdminFrame.this);
				addUser.setVisible(true);

			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(back_panel);
		back_panel.setFocusable(false);
		setResizable(false);
		this.setSize(400, 449);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);

		{
			createAccount = new CreateAccount(AdminFrame.this);
			createAccount.setVisible(false);
		}
		back_panel.setLayout(null);

		back_panel.add(button);

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
		initCrossButton();
	}

	private void setButtons() {
		btnAddfriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addFriend = new AddFriend(AdminFrame.this);
				addFriend.repaint();
				addFriend.validate();
				addFriend.revalidate();
				addFriend.setVisible(true);
			}
		});
		btnAddfriend.setIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b1.png")));
		btnAddfriend.setBounds(6, 134, 390, 40);
		btnAddfriend.setContentAreaFilled(false);
		btnAddfriend.setBorderPainted(false);
		btnAddfriend.setFocusable(false);
		btnAddfriend.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddfriend.setRolloverIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b2.png")));

		useradd.setIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b3.png")));
		useradd.setBounds(6, 174, 390, 40);
		useradd.setContentAreaFilled(false);
		useradd.setBorderPainted(false);
		useradd.setFocusable(false);
		useradd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		useradd.setRolloverIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b4.png")));
		btnManageGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manageGroup = new ManageGroup(AdminFrame.this);
				manageGroup.repaint();
				manageGroup.validate();
				manageGroup.revalidate();
				manageGroup.setVisible(true);
			}

		});

		btnManageGroup.setIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/managegroup.png")));
		btnManageGroup.setBounds(6, 214, 390, 40);
		btnManageGroup.setContentAreaFilled(false);
		btnManageGroup.setBorderPainted(false);
		btnManageGroup.setFocusable(false);
		btnManageGroup.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnManageGroup.setRolloverIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/managegroupr.png")));

		msgfilter.setIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b9.png")));
		msgfilter.setBounds(6, 253, 390, 40);
		msgfilter.setContentAreaFilled(false);
		msgfilter.setBorderPainted(false);
		msgfilter.setFocusable(false);
		msgfilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		msgfilter.setRolloverIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b10.png")));
		msgfilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				msgFilterWindow = new MsgFilterWindow(AdminFrame.this);

				msgFilterWindow.setVisible(true);

			}
		});

		useraccess.setIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b12.png")));
		useraccess.setBounds(6, 293, 390, 40);
		useraccess.setContentAreaFilled(false);
		useraccess.setBorderPainted(false);
		useraccess.setFocusable(false);
		useraccess.setCursor(new Cursor(Cursor.HAND_CURSOR));
		useraccess.setRolloverIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b11.png")));
		useraccess.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				useraccessClass = new UserAccess(AdminFrame.this);
				useraccessClass.repaint();
				useraccessClass.validate();
				useraccessClass.revalidate();
				useraccessClass.setVisible(true);

			}
		});

		useramanage.setIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b8.png")));
		useramanage.setBounds(6, 333, 390, 40);
		useramanage.setContentAreaFilled(false);
		useramanage.setBorderPainted(false);
		useramanage.setFocusable(false);
		useramanage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		useramanage.setRolloverIcon(new ImageIcon(AdminFrame.class
				.getResource("/resources/b7.png")));
		useramanage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				userManageClass = new UserManage(AdminFrame.this);
				userManageClass.repaint();
				userManageClass.validate();
				userManageClass.revalidate();
				userManageClass.setVisible(true);

			}
		});

		back_panel.add(btnAddfriend);
		back_panel.add(useradd);
		back_panel.add(btnManageGroup);
		back_panel.add(msgfilter);
		back_panel.add(useraccess);
		back_panel.add(useramanage);

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

}
