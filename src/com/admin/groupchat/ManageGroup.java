package com.admin.groupchat;

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

import com.admin.CreateAccount;
import com.model.dutta.IdentityUser;
import com.modification.jpanel.JPanelX;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class ManageGroup extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel = new JPanelX("/resources/adminpanelback.png", this);
	private JButton crossButton;
	ManageGroup frm = this;
	private final JButton button = new JButton("");
	public JButton btnDeleteGroup = new JButton();
	public JButton btnCreateGroup = new JButton();
	public JButton btnAddRemoveGroup = new JButton();
	private CreateGroup createGroup;
	private AddRemoveMember addRemoveMember;
	private DeleteGroup deleteGroup;
	public ManageGroup( Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		{
			
			button.setIcon(new ImageIcon(ManageGroup.class
					.getResource("/resources/cross.png")));
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setFocusable(false);
			button.setRolloverIcon(new ImageIcon(ManageGroup.class
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
		
		btnDeleteGroup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteGroup = new DeleteGroup(ManageGroup.this);
				deleteGroup.setVisible(true);

			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(back_panel);
		back_panel.setFocusable(false);
		setResizable(false);
		this.setSize(400, 450);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);

		
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
		btnCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createGroup =new CreateGroup(ManageGroup.this);
				createGroup.setVisible(true);
			}
		});
		btnCreateGroup.setIcon(new ImageIcon(ManageGroup.class
				.getResource("/resources/creategroup.png")));
		btnCreateGroup.setBounds(6, 134, 390, 40);
		btnCreateGroup.setContentAreaFilled(false);
		btnCreateGroup.setBorderPainted(false);
		btnCreateGroup.setFocusable(false);
		btnCreateGroup.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCreateGroup.setRolloverIcon(new ImageIcon(ManageGroup.class
				.getResource("/resources/creategroupr.png")));

		btnDeleteGroup.setIcon(new ImageIcon(ManageGroup.class
				.getResource("/resources/deletegroup.png")));
		btnDeleteGroup.setBounds(6, 174, 390, 40);
		btnDeleteGroup.setContentAreaFilled(false);
		btnDeleteGroup.setBorderPainted(false);
		btnDeleteGroup.setFocusable(false);
		btnDeleteGroup.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteGroup.setRolloverIcon(new ImageIcon(ManageGroup.class
				.getResource("/resources/deletegroupr.png")));
		btnAddRemoveGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			addRemoveMember = new AddRemoveMember(ManageGroup.this);
			addRemoveMember.setVisible(true);
			}

		});

		btnAddRemoveGroup.setIcon(new ImageIcon(ManageGroup.class
				.getResource("/resources/addremovemember.png")));
		btnAddRemoveGroup.setBounds(6, 214, 390, 40);
		btnAddRemoveGroup.setContentAreaFilled(false);
		btnAddRemoveGroup.setBorderPainted(false);
		btnAddRemoveGroup.setFocusable(false);
		btnAddRemoveGroup.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddRemoveGroup.setRolloverIcon(new ImageIcon(ManageGroup.class
				.getResource("/resources/addremovememberr.png")));
		
		

		back_panel.add(btnCreateGroup);
		back_panel.add(btnDeleteGroup);
		back_panel.add(btnAddRemoveGroup);

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
