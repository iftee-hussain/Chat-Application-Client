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

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CreateGroup extends JDialog implements CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point initialClick;
	JPanel back_panel = new JPanelX("/resources/backgroundform.png", this);
	private JButton crossButton;
	CreateGroup frm = this;
	private final JButton button = new JButton("");
	private final JPanel panel = new JPanel();
	private final JLabel lblNewLabel = new JLabel("Group Name");
	private final JTextField txtGroupName = new JTextField();
	private final JButton btnNewButton = new JButton("CANCEL");
	private final JButton btnCreate = new JButton("CREATE");
	private final JLabel lblNewLabel_1 = new JLabel("");
	private final JLabel lblDeleteGroup = new JLabel("CREATE GROUP");
	private final JLabel lblError = new JLabel("");
	private String currentlyAddedGroupName = null;

	public CreateGroup(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		txtGroupName.setBounds(177, 33, 190, 30);
		txtGroupName.setColumns(10);
		{

			button.setIcon(new ImageIcon(CreateGroup.class
					.getResource("/resources/cross.png")));
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setFocusable(false);
			button.setRolloverIcon(new ImageIcon(CreateGroup.class
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
		panel.setBounds(0, 200, 400, 169);

		back_panel.add(panel);
		panel.setLayout(null);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(30, 25, 114, 41);

		panel.add(lblNewLabel);

		panel.add(txtGroupName);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton.setBounds(278, 85, 89, 31);

		panel.add(btnNewButton);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!txtGroupName.getText().equals("")) {
					boolean flag = true;
					if (UsefulData.getInstance().groupList != null) {
						for (String e : UsefulData.getInstance().groupList) {
							if (e.equals(txtGroupName.getText())) {
								flag = false;
								break;
							}

						}
					}

					if (flag) {
						UsefulData.getInstance().main.clientControl.send(
								CreateGroup.this,
								new Message(Constant.CREATE_GROUP,
										IdentityUser.userName, txtGroupName
												.getText(), "SERVER", -1));
						currentlyAddedGroupName = txtGroupName.getText();
					} else
						lblError.setText("Error!!! Dublicate Group Name: "
								+ txtGroupName.getText());
				} else {
					lblError.setText("Please Enter group Name");
				}
			}

		});
		btnCreate.setBounds(179, 85, 89, 31);

		panel.add(btnCreate);
		lblNewLabel_1.setIcon(new ImageIcon(CreateGroup.class
				.getResource("/resources/dash.png")));
		lblNewLabel_1.setBounds(22, 181, 353, 5);

		back_panel.add(lblNewLabel_1);
		lblDeleteGroup.setForeground(Color.WHITE);
		lblDeleteGroup.setFont(new Font("Arial", Font.BOLD, 18));
		lblDeleteGroup.setBounds(23, 147, 157, 41);

		back_panel.add(lblDeleteGroup);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(204, 204, 0));
		lblError.setFont(new Font("Arial", Font.PLAIN, 12));
		lblError.setBounds(22, 400, 337, 36);

		back_panel.add(lblError);

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
		if (msg.content.equalsIgnoreCase("true")) {
			lblError.setText("Successfully created group");
			if(currentlyAddedGroupName!=null)
			UsefulData.getInstance().addGroupName(currentlyAddedGroupName);
		} else {
			lblError.setText("Error while creating group");
		}

	}
}
