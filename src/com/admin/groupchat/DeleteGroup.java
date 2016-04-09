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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.admin.CreateAccount;
import com.app.dutta.Main;
import com.model.dutta.IdentityUser;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class DeleteGroup extends JDialog implements CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel = new JPanelX("/resources/backgroundform.png", this);
	private JButton crossButton;
	DeleteGroup frm = this;
	private final JButton button = new JButton("");
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Group Name");
	private final JButton btnCancel = new JButton("CANCEL");
	private final JButton btnDelete = new JButton("DELETE");
	private final JLabel lblNewLabel_1 = new JLabel("");
	private final JLabel lblDeleteGroup = new JLabel("DELETE GROUP");
	private final JLabel lblReport = new JLabel("");
	private JComboBox comboGroupName;
	private JComboBox<String> comboDummy;


	public DeleteGroup(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		{

			button.setIcon(new ImageIcon(DeleteGroup.class
					.getResource("/resources/cross.png")));
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setFocusable(false);
			button.setRolloverIcon(new ImageIcon(DeleteGroup.class
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
		{

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
		panel.setBounds(0, 200, 400, 174);

		back_panel.add(panel);
		panel.setLayout(null);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(30, 25, 114, 41);

		panel.add(lblNewLabel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(278, 85, 89, 31);

		panel.add(btnCancel);
		btnDelete.setBounds(179, 85, 89, 31);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (comboGroupName.getSelectedIndex() != -1)
					UsefulData.getInstance().main.clientControl
							.send(DeleteGroup.this,
									new Message(Constant.DELETE_GROUP,
											IdentityUser.userName,
											comboGroupName.getSelectedItem().toString(),
											"SERVER", -1));
				
			}
		});

		panel.add(btnDelete);
		comboDummy = new JComboBox<>();
		comboDummy.setBounds(177, 36, 190, 30);
		panel.add(comboDummy);

		lblNewLabel_1.setIcon(new ImageIcon(DeleteGroup.class
				.getResource("/resources/dash.png")));
		lblNewLabel_1.setBounds(22, 181, 353, 5);

		back_panel.add(lblNewLabel_1);
		lblDeleteGroup.setForeground(Color.WHITE);
		lblDeleteGroup.setFont(new Font("Arial", Font.BOLD, 18));
		lblDeleteGroup.setBounds(23, 147, 157, 41);

		back_panel.add(lblDeleteGroup);
		lblReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblReport.setForeground(new Color(204, 204, 0));
		lblReport.setFont(new Font("Arial", Font.PLAIN, 12));
		lblReport.setBounds(23, 404, 337, 36);

		back_panel.add(lblReport);

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
		if(UsefulData.getInstance().groupList==null)
		UsefulData.getInstance().main.clientControl.send(DeleteGroup.this,
				new Message(Constant.GET_GROUPS_NAMES, IdentityUser.userName,
						"", IdentityUser.getIdentityUser().userDetails.userType + "", -1));
		else
			init();
		initCrossButton();
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

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(Message msg) {
		if (msg.type == Constant.GET_GROUPS_NAMES) {
			if (msg.sender.equalsIgnoreCase("true")) {
				UsefulData.getInstance().groupList = msg.content.split(Message.SECOND_LEVEL_SEPERATOR);
				init();

			} else {
				lblReport.setText("Server error: Can't get group names. ");
			}

		} else if (msg.type == Constant.DELETE_GROUP) {
			if (msg.content.equalsIgnoreCase("true")) {
				deleteGroupName();
			} else {
				lblReport.setText(msg.content);
			}
		}

	}
	public void init(){
		if (comboGroupName != null)
			comboGroupName.setVisible(false);
	
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				comboGroupName = new JComboBox<>(UsefulData.getInstance().groupList);
				comboDummy.setVisible(false);
				comboGroupName.setBounds(177, 36, 190, 30);
				comboGroupName.setVisible(true);

				comboGroupName.repaint();
				panel.add(comboGroupName);
			}
		});
	}
	public void deleteGroupName(){
		
		UsefulData.getInstance().deleteGroupName(comboGroupName.getSelectedItem().toString());
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if(comboGroupName!=null){
					comboGroupName.setVisible(false);
					panel.remove(comboGroupName);
				}
				
				comboGroupName = new JComboBox<>(UsefulData.getInstance().groupList);
				comboDummy.setVisible(false);
				comboGroupName.setBounds(177, 36, 190, 30);
				comboGroupName.setVisible(true);
				comboGroupName.setSelectedIndex(-1);
				comboGroupName.repaint();
				comboGroupName.validate();
				comboGroupName.revalidate();
				panel.add(comboGroupName);
				panel.validate();
				panel.revalidate();
				validate();
				revalidate();
			}
		});
		
		lblReport.setText("Successfully deleted group");
	}
}
