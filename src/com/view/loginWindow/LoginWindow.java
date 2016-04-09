package com.view.loginWindow;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.admin.CreateAccount;
import com.app.dutta.Main;
import com.model.dutta.IdentityUser;
import com.model.dutta.MeToWhom;
import com.model.dutta.UserDetails;
import com.model.dutta.UserPermissions;
import com.modification.jpanel.InputField;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.BackgroundWork;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.Browser;
import com.utility.dutta.GeneralErrorReportable;
import com.utility.dutta.LoadingPanel;
import com.utility.dutta.SaveLoginInfo;
import com.utility.dutta.UsefulData;

public class LoginWindow extends JFrame implements CallBackable,
		GeneralErrorReportable, BackgroundWorkable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<MeToWhom> chatwiths = new ArrayList<>();
	JPanel back_panel;
	private Point initialClick;
	private JCheckBox cbRememberMe;
	LoginWindow frm = this;
	private JButton btnSignIn;
	public JLabel lblError;
	private LoginWindow loginWindow;
	private JPanel loadingPanel;
	private JButton crossButton;
	private boolean success;
	private CreateAccount createAccount;
	private JPanel contentPanel;
	private InputField userNameInputField;
	private InputField passwordInputField;

	/**
	 * Create the frame.
	 */
	public LoginWindow() {

		loginWindow = this;
		back_panel = new JPanelX("/resources/backload.png", this, true);
		setContentPane(back_panel);
		back_panel.setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 450);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		addMoveListener(back_panel);
		setFocusTraversalKeysEnabled(false);
		createAccount = new CreateAccount(LoginWindow.this);
		{
			contentPanel = new JPanel();
			contentPanel.setLayout(null);
			contentPanel.setOpaque(false);
			contentPanel.setBounds(44, 187, 374, 214);
			back_panel.add(contentPanel);
		}
		createAccount.setVisible(false);
		{
			loadingPanel = new LoadingPanel();
			loadingPanel.setLocation(170, 200);
			loadingPanel.setVisible(false);
			getContentPane().add(loadingPanel);
		}
		setFocusable(true);
		{
			crossButton = new JButton("");
			crossButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							dispose();
							System.exit(0);
						}
					});

				}
			});

			crossButton.setIcon(new ImageIcon(LoginWindow.class
					.getResource("/resources/cross.png")));

			crossButton.setRolloverIcon(new ImageIcon(LoginWindow.class
					.getResource("/resources/cra.png")));
			crossButton.setBounds(405, 11, 24, 35);
			crossButton.setMargin(new Insets(0, 0, 0, 0));
			crossButton.setFocusable(false);
			crossButton.setContentAreaFilled(false);
			crossButton.setBorderPainted(false);
			back_panel.add(crossButton);

		}
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				userNameInputField.transferFocus();
			}
		});
		
		lblError = new JLabel();
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(255, 153, 153));
		lblError.setFont(new Font("Serif", Font.BOLD, 17));
		lblError.setBounds(20, 380, 420, 35);
		back_panel.add(lblError);
		// User Name Field
		{

			userNameInputField = new InputField(55, 5, 280, 35, "User Name");
			contentPanel.add(userNameInputField);

			userNameInputField.addKeyListener(KeyEvent.VK_ENTER, "ENTER",
					new AbstractAction() {

						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (!userNameInputField.getText().equals("")
									&& passwordInputField.getPassword().length != 0) {
								validate();
								new BackgroundWork(LoginWindow.this).execute();
							} else {
								lblError.setText("Please Input Username and Password.");
							}
						}
					});

			userNameInputField.addKeyListener(KeyEvent.VK_TAB, "TAB",
					new AbstractAction() {
						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent arg0) {
							passwordInputField.transferFocus();
						}
					});
		}

		// Password Input Field
		{

			passwordInputField = new InputField(55, 50, 280, 35, "Password",
					true);
			contentPanel.add(passwordInputField);
			passwordInputField.addKeyListener(KeyEvent.VK_ENTER, "ENTER",
					new AbstractAction() {

						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (!userNameInputField.getText().equals("")
									&& passwordInputField.getPassword().length != 0) {
								validate();
								new BackgroundWork(LoginWindow.this).execute();
							} else {
								lblError.setText("Please Input Username and Password.");
							}
						}
					});
			passwordInputField.addKeyListener(KeyEvent.VK_TAB, "TAB", new AbstractAction() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userNameInputField.transferFocus();
				}
			});
			passwordInputField.setBorder(null);
			passwordInputField.setOpaque(false);

		}

		cbRememberMe = new JCheckBox();
		cbRememberMe.setBounds(63, 107, 119, 23);
		contentPanel.add(cbRememberMe);
		cbRememberMe.setBackground(Color.WHITE);
		cbRememberMe.setBorderPainted(false);
		cbRememberMe.setContentAreaFilled(false);
		cbRememberMe.setBorderPainted(false);

		JLabel label_1 = new JLabel("");
		label_1.setBounds(67, 112, 200, 14);
		contentPanel.add(label_1);

		label_1.setIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/remme.png")));
		final JLabel plusAc = new JLabel(new ImageIcon(
				LoginWindow.class.getResource("/resources/newac.png")));
		plusAc.setBounds(24, 129, 185, 30);
		contentPanel.add(plusAc);

		final JLabel forgotPassword = new JLabel(new ImageIcon(
				LoginWindow.class.getResource("/resources/forpas.png")));
		forgotPassword.setBounds(30, 157, 200, 27);
		contentPanel.add(forgotPassword);

		btnSignIn = new JButton("");
		btnSignIn.setBounds(224, 103, 89, 23);
		contentPanel.add(btnSignIn);
		btnSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!userNameInputField.getText().equals("")
						&& passwordInputField.getPassword().length != 0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							validate();
							new BackgroundWork(LoginWindow.this).execute();
						}

					});
				} else {
					lblError.setText("Please Input Username and Password.");
				}
			}
		});
		btnSignIn.setIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/signin.png")));
		btnSignIn.setRolloverIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/signinr.png")));
		btnSignIn.setPressedIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/signinp.png")));
		btnSignIn.setMargin(new Insets(0, 0, 0, 0));

		this.btnSignIn.setContentAreaFilled(false);
		this.btnSignIn.setBorderPainted(false);

		this.btnSignIn.setSelected(false);
		forgotPassword.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				forgotPassword.setIcon(new ImageIcon(LoginWindow.class
						.getResource("/resources/forpasroll.png")));
				forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				forgotPassword.setIcon(new ImageIcon(LoginWindow.class
						.getResource("/resources/forpas.png")));
				forgotPassword.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				Browser.openLink("http://www.duttachat.com/forgetpass.php");
			}
		});
		plusAc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				plusAc.setIcon(new ImageIcon(LoginWindow.class
						.getResource("/resources/newacroll1.png")));
				plusAc.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				plusAc.setIcon(new ImageIcon(LoginWindow.class
						.getResource("/resources/newac.png")));
				plusAc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				// new UserAdd(LoginWindow.this, true).setVisible(true);

				Browser.openLink("http://www.duttachat.com/joinus.php");
			}
		});

		SaveLoginInfo s = new SaveLoginInfo();
		String str[] = s.getData(Main.infoFilePath).split(
				Message.FIRST_LEVEL_SEPERATOR);
		if (str.length == 2 && (!str[0].equals("*") && !str[1].equals("*"))) {
			userNameInputField.setText(str[0]);
			passwordInputField.setText(str[1]);
			
			cbRememberMe.setSelected(true);

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					
					new BackgroundWork(LoginWindow.this).execute();
					validate();
				}

			});
		}
		refresh();
		repaint();
		validate();
		revalidate();
	}

	public void refresh() {
		SaveLoginInfo s = new SaveLoginInfo();
		String str[] = s.getData(Main.infoFilePath).split(
				Message.FIRST_LEVEL_SEPERATOR);
		if (str.length == 2 && (!str[0].equals("*") && !str[1].equals("*"))) {
			userNameInputField.setText(str[0]);
			passwordInputField.setText(str[1]);
			lblError.setText("");
		} else {
			userNameInputField.setText("");
			passwordInputField.setText("");

			lblError.setText("");

		}
		repaint();
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

				int thisX = LoginWindow.this.getLocation().x;
				int thisY = LoginWindow.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				LoginWindow.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public void done(String exception) {
		loginWindow.setContentPane(back_panel);
		loginWindow.validate();
		loginWindow.lblError.setText(exception);

	}

	@Override
	public void done(Message msg) {
		if (msg.type == Constant.SERVER_ERROR) {
			loginWindow.setContentPane(back_panel);
			loginWindow.validate();
			loginWindow.lblError.setText(msg.content);
		} else {
			if (msg.content.equalsIgnoreCase("TRUE")) {
				IdentityUser.getIdentityUser().setIdentityUser(new UserDetails(msg.sender));
				if (cbRememberMe.isSelected()) {
					SaveLoginInfo si = new SaveLoginInfo();
					si.saveData(userNameInputField.getText(), new String(
							passwordInputField.getPassword()),
							Main.infoFilePath);
				} else {

					SaveLoginInfo si = new SaveLoginInfo();
					si.saveData("", "", Main.infoFilePath);

				}
				permissions();
			} else if (msg.type != Constant.SPECIFIC_USER_PERMISSION) {
				loginWindow.setContentPane(back_panel);
				loginWindow.validate();
				loginWindow.lblError.setText("Login Failed: " + msg.content);
			} else {
				loginWindow.setContentPane(back_panel);
				loginWindow.validate();
				loginWindow.lblError.setText("Login Failed: " + msg.content);
			}
		}
		if (msg.type == Constant.SPECIFIC_USER_PERMISSION) {
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
				loginWindow.setContentPane(back_panel);
				loginWindow.dispose();
				UsefulData.getInstance().userpass = new String(
						passwordInputField.getPassword());
				UsefulData.getInstance().main.initChatWindow();

			}
		}

	}

	private void permissions() {
		UsefulData.getInstance().main.clientControl.send(
				LoginWindow.this,
				new Message(Constant.SPECIFIC_USER_PERMISSION, IdentityUser
						.getIdentityUser().userName, IdentityUser
						.getIdentityUser().userName, "Server", -1));
	}

	@Override
	public Long donInBackground() {

		loadingPanel.setVisible(true);
		contentPanel.setVisible(false);


				UsefulData.getInstance().main.openConnection();

				UsefulData.getInstance().main.clientControl.sineIn(LoginWindow.this,
						loginWindow.userNameInputField.getText(), new String(
								loginWindow.passwordInputField.getPassword()));
			
		System.out.println("Okk");

		return null;
	}

	@Override
	public void finished() {
		loadingPanel.setVisible(false);
		contentPanel.setVisible(true);
	}

	@Override
	public void showError(final String error) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lblError.setText(error);
				

			}
		});

	}
}
