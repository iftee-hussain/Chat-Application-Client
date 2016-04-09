package com.admin;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.modification.jpanel.InputField;
import com.modification.jpanel.JPanelX;
import com.utility.dutta.BackgroundWork;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.LoadingPanel;

public class CreateAccount extends JDialog implements BackgroundWorkable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JButton crossButton;
	private JPanelX panel;
	private JButton createButton;
	private Point initialClick;
	private InputField userNameField;
	private InputField emailField;
	private InputField passwordField;
	private InputField countryField;
	private LoadingPanel loadingPanel;
	private JLabel lblReport;
	public CreateAccount(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.setSize(450, 450);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);
		getContentPane().setLayout(null);
	
		panel = new JPanelX(new ImageIcon(
				getClass().getClassLoader().
						getResource("resources/backgroundform.png")), true);
		panel.setBounds(0, 0, 450, 450);
		addMoveListener(panel);
		// >>>>>>>>>>>>Cross Button>>>>>>>>>>>>>>>>>>>
		initCrossButton();
		panel.setLayout(null);
		panel.add(crossButton);
		// >>>>>>>>>>>>>>>Create
		initCreateButton();

		getContentPane().add(panel);
		{
			contentPanel = new JPanel();
			contentPanel.setOpaque(false);
			contentPanel.setBounds(95, 140, 274, 264);
			contentPanel.setLayout(null);
			panel.add(contentPanel);
		}
		{
			JLabel lblNewLabel = new JLabel("Create Account");
			lblNewLabel.setBounds(10, 11, 108, 19);
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			contentPanel.add(lblNewLabel);
		}

		{
			createButton = new JButton(new ImageIcon(
					CreateAccount.class.getResource("/resources/create.png")));
			createButton.setBounds(180, 221, 84, 32);
			contentPanel.add(createButton);
			createButton.setRolloverIcon(new ImageIcon(CreateAccount.class
					.getResource("/resources/creater.png")));
			createButton.setPressedIcon(new ImageIcon(CreateAccount.class
					.getResource("/resources/createp.png")));
			createButton.setBorderPainted(false);
			createButton.setContentAreaFilled(false);
			createButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							
							if (userNameField.getText() != ""
									&& passwordField.getPassword().length != 0
									&& emailField.getText() != ""
									&& countryField.getText() != null)
								new BackgroundWork(CreateAccount.this)
										.execute();
						
						}
					});

				}
			});
		}

		{
			userNameField = new InputField(0, 0, 270, 34, "Name");
			userNameField.setPlaceHolderString("User Name");
			userNameField.setLocation(0, 40);
			contentPanel.add(userNameField);
		}
		{
			emailField = new InputField(0, 0, 270, 34, "Name");
			emailField.setPlaceHolderString("Email");
			emailField.setBounds(0, 85, 270, 34);
			contentPanel.add(emailField);
		}

		{
			countryField = new InputField(0, 0, 270, 34, "Name");
			countryField.setPlaceHolderString("Country");
			countryField.setBounds(0, 130, 270, 34);
			contentPanel.add(countryField);
		}
		{
			passwordField = new InputField(0, 0, 270, 34, "Name", true);
			passwordField.setPlaceHolderString("Password");
			passwordField.setBounds(0, 175, 270, 34);
			contentPanel.add(passwordField);
		}
		{
			loadingPanel = new LoadingPanel();
			loadingPanel.setLocation(174, 180);
			panel.add(loadingPanel);
		}
		{
			lblReport = new JLabel("");
			lblReport.setHorizontalAlignment(SwingConstants.CENTER);
			lblReport.setFont(new Font("Arial", Font.PLAIN, 14));
			lblReport.setForeground(new Color(102, 0, 51));
			lblReport.setBounds(40, 404, 356, 35);
			panel.add(lblReport);
		}
		loadingPanel.setVisible(false);
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

				int thisX = CreateAccount.this.getLocation().x;
				int thisY = CreateAccount.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				CreateAccount.this.setLocation(X, Y);
			}
		});
	}

	private void initCreateButton() {
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
						refresh();

					}
				});

			}
		});
	}

	private void refresh() {
		userNameField.refresh();
		passwordField.refresh();
		countryField.refresh();
		emailField.refresh();
	}

	@Override
	public Long donInBackground() {
//		
//		try {
//			contentPanel.setVisible(false);
//			loadingPanel.setVisible(true);
//			Manager manager = new Manager();
//		
//			manager.createAccount(userNameField.getText(), passwordField.getPassword(), 
//					emailField.getText(),countryField.getText());
//			lblReport
//					.setText("Successfully Account Created.");
//			lblReport.setForeground(Color.GREEN);
//			
//			main.chatWindow.friendListPanel.loadfriend();
//			refresh();
//		} catch (Exception e) {
//			lblReport.setText(e.getMessage());
//			lblReport.setForeground(Color.RED);
//
//		}
		return null;

	}

	@Override
	public void finished() {
		contentPanel.setVisible(true);
		loadingPanel.setVisible(false);
	}
}
