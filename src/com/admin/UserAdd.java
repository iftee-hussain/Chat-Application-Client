package com.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.model.dutta.IdentityUser;
import com.model.dutta.UserDetails;
import com.modification.jpanel.InputField;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.BackgroundWork;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.LoadingPanel;
import com.utility.dutta.UsefulData;

public class UserAdd extends JDialog implements BackgroundWorkable,
		CallBackable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelX panel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Point initialClick;
	private JCheckBox checkBoxAdminPanelAccess;
	private JCheckBox chckbxActive;
	private InputField nameField;
	private InputField userNameField;
	private InputField passwordField;
	private InputField confirmPasswordField;
	private InputField ipAddressField;
	private InputField emailIdField;
	private InputField moPhoneField;
	private InputField postalAddressField;
	private JPanel contentPanel;
	private JLabel dashLabel;
	private LoadingPanel loadingPanel;
	private JLabel lblreport;
	private JComboBox<?> comboUserType;
	private JComboBox<?> comboCountryList;
	private String[] countryLists;

	public UserAdd(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.setSize(400, screenSize.height - 100);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);
		try {
//			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception e) {
		}
		getContentPane().setLayout(null);
		{
			loadingPanel = new LoadingPanel();
			loadingPanel.setLocation(150, 250);
			loadingPanel.setVisible(false);
			getContentPane().add(loadingPanel);
		}

		panel = new JPanelX(new ImageIcon(
				CreateAccount.class.getResource("/resources/background1.png")),
				true);

		panel.setBounds(0, 0, 400, screenSize.height - 100);
		panel.add(addCrossButton());
		addMoveListener(panel);
		getContentPane().add(panel);
		{
			contentPanel = new JPanel();
			contentPanel.setOpaque(false);
			contentPanel.setBounds(43, 135, 341, 499);
			panel.add(contentPanel);
			contentPanel.setLayout(null);
			{
				dashLabel = new JLabel("");
				dashLabel.setIcon(new ImageIcon(UserAdd.class
						.getResource("/resources/dash.png")));
				dashLabel.setBounds(0, 30, 320, 4);
				contentPanel.add(dashLabel);
			}
			//
			JLabel lblMyProfile = new JLabel("USER ADD");
			lblMyProfile.setBounds(0, 5, 108, 22);
			contentPanel.add(lblMyProfile);
			lblMyProfile.setForeground(Color.WHITE);
			lblMyProfile.setFont(new Font("Arial", Font.PLAIN, 18));
			// <<<<<<<<<<<<<<<<<<<<<ONLY LEBEL
			// ADDED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			JLabel lblUserId = new JLabel("Full Name");
			lblUserId.setBounds(0, 70, 103, 50);
			contentPanel.add(lblUserId);
			lblUserId.setForeground(Color.WHITE);
			lblUserId.setFont(new Font("Arial", Font.PLAIN, 14));
			{
				JLabel label = new JLabel("User Name");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 104, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Password");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 138, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Confirm Password");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 172, 131, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("IP Address");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 206, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Email ID");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 240, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Mo/Phone");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 274, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Postal Address");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 306, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Country");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 342, 103, 50);
				contentPanel.add(label);
			}
			{
				JButton btnSave = new JButton("");
				btnSave.setBounds(115, 426, 97, 36);
				contentPanel.add(btnSave);
				btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						String s = "";
						if (nameField.getText().equals(""))
							s += "* Full name field is required<br>";
						if (userNameField.getText().equals(""))
							s += "* User name field is required<br>";
						if (new String(passwordField.getPassword()).equals(""))
							s += "* Password field is required<br>";
						if (new String(confirmPasswordField.getPassword())
								.equals(""))
							s += "* Confirm password field is required<br>";
						if (ipAddressField.getText().equals(""))
							s += "* Ip address field is required<br>";
						if (emailIdField.getText().equals(""))
							s += "* Email field is required<br>";
						if (moPhoneField.getText().equals(""))
							s += "* Phone number is required<br>";
						if (postalAddressField.getText().equals(""))
							s += "* Postal address is required<br>";

						String msg = "<html>" + s + "</html>";
						JLabel label = new JLabel(msg);
						label.setFont(new Font("serif", Font.BOLD, 14));
						if (!s.equals(""))
							JOptionPane.showMessageDialog(UserAdd.this, label);

						else {
							String p = new String(passwordField.getPassword());
							String cp = new String(confirmPasswordField
									.getPassword());
							if (!p.equals("") && !cp.equals("")
									&& !emailIdField.getText().equals("")
									&& !userNameField.getText().equals("")) {
								if (!p.equals(cp)) {
									lblreport
											.setText("Password doesn't match.");
									lblreport.setForeground(new Color(102, 0,
											105));
								} else {
									SwingUtilities.invokeLater(new Runnable() {

										public void run() {
											new BackgroundWork(UserAdd.this)
													.execute();

										}
									});
								}
							}
						}
					}
				});
				btnSave.setOpaque(false);
				btnSave.setRolloverIcon(new ImageIcon(UserAdd.class
						.getResource("/resources/saver.png")));
				btnSave.setPressedIcon(new ImageIcon(UserAdd.class
						.getResource("/resources/savep.png")));
				btnSave.setIcon(new ImageIcon(UserAdd.class
						.getResource("/resources/save.png")));
				btnSave.setBorderPainted(false);
				btnSave.setContentAreaFilled(false);
			}
			{
				JButton btnCancel = new JButton("");
				btnCancel.setBounds(222, 426, 97, 36);
				contentPanel.add(btnCancel);
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				btnCancel.setRolloverIcon(new ImageIcon(UserAdd.class
						.getResource("/resources/cancelr.png")));
				btnCancel.setPressedIcon(new ImageIcon(UserAdd.class
						.getResource("/resources/cancelp.png")));
				btnCancel.setIcon(new ImageIcon(UserAdd.class
						.getResource("/resources/cancel.png")));
				btnCancel.setBorderPainted(false);
				btnCancel.setContentAreaFilled(false);
			}

			{
				postalAddressField = new InputField(0, 0, 194, 30, "User ID");
				postalAddressField.setPlaceHolderString("Postal Address");
				postalAddressField.setBounds(125, 316, 194, 30);
				contentPanel.add(postalAddressField);

			}
			{
				moPhoneField = new InputField(0, 0, 194, 30, "User ID");
				moPhoneField.setPlaceHolderString("Mo/Phone");
				moPhoneField.setBounds(125, 284, 194, 30);
				contentPanel.add(moPhoneField);

			}

			{
				emailIdField = new InputField(0, 0, 194, 30, "User ID");
				emailIdField.setBounds(125, 250, 194, 30);
				contentPanel.add(emailIdField);
				emailIdField.setPlaceHolderString("Email ID");
			}

			{
				ipAddressField = new InputField(0, 0, 194, 30, "User ID");
				ipAddressField.setPlaceHolderString("IP Address");
				ipAddressField.setBounds(125, 216, 194, 30);
				contentPanel.add(ipAddressField);

			}

			{
				confirmPasswordField = new InputField(0, 0, 194, 30, "User ID",
						true);
				confirmPasswordField.setPlaceHolderString("Confirm Password");
				confirmPasswordField.setBounds(125, 182, 194, 30);
				contentPanel.add(confirmPasswordField);

			}

			{
				passwordField = new InputField(0, 0, 194, 30, "User ID", true);
				passwordField.setBounds(125, 148, 194, 30);
				contentPanel.add(passwordField);
				passwordField.setPlaceHolderString("Password");
			}
			{
				userNameField = new InputField(0, 0, 194, 30, "User ID");
				userNameField.setPlaceHolderString("User Name");
				userNameField.setBounds(126, 116, 194, 30);
				contentPanel.add(userNameField);

			}
			// <<<<<<<<<<<<<<<<<<<<LABEL ADDED
			// END<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			{
				nameField = new InputField(0, 0, 194, 30, "Full FName");
				nameField.setBounds(125, 82, 194, 30);
				contentPanel.add(nameField);

			}

			{
				checkBoxAdminPanelAccess = new JCheckBox("Admin Panel Access");
				checkBoxAdminPanelAccess.setBounds(125, 395, 124, 20);
				contentPanel.add(checkBoxAdminPanelAccess);
				checkBoxAdminPanelAccess.setFont(new Font("Arial", Font.PLAIN,
						10));
				checkBoxAdminPanelAccess.setForeground(Color.WHITE);
				checkBoxAdminPanelAccess.setOpaque(false);
			}
			{
				chckbxActive = new JCheckBox("Active");
				chckbxActive.setBounds(260, 395, 77, 20);
				contentPanel.add(chckbxActive);
				chckbxActive.setForeground(Color.WHITE);
				chckbxActive.setFont(new Font("Arial", Font.PLAIN, 10));
				chckbxActive.setOpaque(false);
			}

			JLabel lblUserType = new JLabel("User Type");
			lblUserType.setForeground(Color.WHITE);
			lblUserType.setFont(new Font("Arial", Font.PLAIN, 14));
			lblUserType.setBounds(0, 35, 103, 50);
			contentPanel.add(lblUserType);
			{
				String[] list = { "Superuser", "Admin", "Staff", "Customer" };
				comboUserType = new JComboBox<Object>(list);
				comboUserType.setOpaque(false);
				comboUserType.setBounds(125, 45, 195, 30);
				comboUserType.setBorder(BorderFactory.createEmptyBorder());
				contentPanel.add(comboUserType);

			}
			{
				countryLists = new String[] { "Andorra",
						"United Arab Emirates", "Afghanistan",
						"Antigua and Barbuda", "Anguilla", "Albania",
						"Armenia", "Netherlands Antilles", "Angola",
						"Antarctica", "Argentina", "American Samoa", "Austria",
						"Australia", "Aruba", "Åland Islands", "Azerbaijan",
						"Bosnia and Herzegovina", "Barbados", "Bangladesh",
						"Belgium", "Burkina Faso", "Bulgaria", "Bahrain",
						"Burundi", "Benin", "Saint Barthélemy", "Bermuda",
						"Brunei", "Bolivia",
						"Bonaire, Sint Eustatius and Saba", "Brazil",
						"Bahamas", "Bhutan", "Bouvet Island", "Botswana",
						"Belarus", "Belize", "Canada", "Cocos Islands",
						"The Democratic Republic Of Congo",
						"Central African Republic", "Congo", "Switzerland",
						"Côte d'Ivoire", "Cook Islands", "Chile", "Cameroon",
						"China", "Colombia", "Costa Rica", "Cuba",
						"Cape Verde", "Curaçao", "Christmas Island", "Cyprus",
						"Czech Republic", "Germany", "Djibouti", "Denmark",
						"Dominica", "Dominican Republic", "Algeria", "Ecuador",
						"Estonia", "Egypt", "Western Sahara", "Eritrea",
						"Spain", "Ethiopia", "Finland", "Fiji",
						"Falkland Islands", "Micronesia", "Faroe Islands",
						"France", "Gabon", "United Kingdom", "Grenada",
						"Georgia", "French Guiana", "Guernsey", "Ghana",
						"Gibraltar", "Greenland", "Gambia", "Guinea",
						"Guadeloupe", "Equatorial Guinea", "Greece",
						"South Georgia And The South Sandwich Islands",
						"Guatemala", "Guam", "Guinea-Bissau", "Guyana",
						"Hong Kong", "Heard Island And McDonald Islands",
						"Honduras", "Croatia", "Haiti", "Hungary", "Indonesia",
						"Ireland", "Israel", "Isle Of Man", "India",
						"British Indian Ocean Territory", "Iraq", "Iran",
						"Iceland", "Italy", "Jersey", "Jamaica", "Jordan",
						"Japan", "Kenya", "Kyrgyzstan", "Cambodia", "Kiribati",
						"Comoros", "Saint Kitts And Nevis", "North Korea",
						"South Korea", "Kuwait", "Cayman Islands",
						"Kazakhstan", "Laos", "Lebanon", "Saint Lucia",
						"Liechtenstein", "Sri Lanka", "Liberia", "Lesotho",
						"Lithuania", "Luxembourg", "Latvia", "Libya",
						"Morocco", "Monaco", "Moldova", "Montenegro",
						"Saint Martin", "Madagascar", "Marshall Islands",
						"Macedonia", "Mali", "Myanmar", "Mongolia", "Macao",
						"Northern Mariana Islands", "Martinique", "Mauritania",
						"Montserrat", "Malta", "Mauritius", "Maldives",
						"Malawi", "Mexico", "Malaysia", "Mozambique",
						"Namibia", "New Caledonia", "Niger", "Norfolk Island",
						"Nigeria", "Nicaragua", "Netherlands", "Norway",
						"Nepal", "Nauru", "Niue", "New Zealand", "Oman",
						"Panama", "Peru", "French Polynesia",
						"Papua New Guinea", "Philippines", "Pakistan",
						"Poland", "Saint Pierre And Miquelon", "Pitcairn",
						"Puerto Rico", "Palestine", "Portugal", "Palau",
						"Paraguay", "Qatar", "Reunion", "Romania", "Serbia",
						"Russia", "Rwanda", "Saudi Arabia", "Solomon Islands",
						"Seychelles", };

				Arrays.sort(countryLists);

				comboCountryList = new JComboBox<Object>(countryLists);
				comboCountryList.setBounds(125, 355, 194, 30);
				contentPanel.add(comboCountryList);

			}
		}
		{
			lblreport = new JLabel("");
			lblreport.setHorizontalAlignment(SwingConstants.CENTER);
			lblreport.setForeground(new Color(102, 0, 51));
			lblreport.setFont(new Font("Arial", Font.PLAIN, 14));
			lblreport.setBounds(43, 599, 356, 35);
			panel.add(lblreport);
		}

		refresh();
	}

	private JButton addCrossButton() {

		JButton crossButton = new JButton(new ImageIcon(
				CreateAccount.class.getResource("/resources/cross.png")));
		crossButton.setBounds(400 - 40, 15, 24, 20);
		crossButton.setRolloverIcon(new ImageIcon(CreateAccount.class
				.getResource("/resources/cra.png")));
		crossButton.setBorderPainted(false);
		crossButton.setContentAreaFilled(false);
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

		return crossButton;
	}

	public void refresh() {
		nameField.refresh();
		userNameField.refresh();
		passwordField.refresh();
		confirmPasswordField.refresh();
		ipAddressField.refresh();
		emailIdField.refresh();
		moPhoneField.refresh();
		postalAddressField.refresh();
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

				int thisX = UserAdd.this.getLocation().x;
				int thisY = UserAdd.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				UserAdd.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public Long donInBackground() {
		loadingPanel.setVisible(true);
		contentPanel.setVisible(false);
		lblreport.setText("");

		try {
			UserDetails ud = new UserDetails(userNameField.getText(),
					nameField.getText(), comboUserType.getSelectedIndex() + 1,
					ipAddressField.getText(), emailIdField.getText(),
					moPhoneField.getText(), postalAddressField.getText(),
					countryLists[comboCountryList.getSelectedIndex()],
					checkBoxAdminPanelAccess.isSelected(),
					chckbxActive.isSelected());
			UsefulData.getInstance().main.clientControl.send(
					UserAdd.this,
					new Message(Constant.SINE_UP, IdentityUser.userName, ud
							.toString(), IdentityUser.getSecureHash(new String(
							passwordField.getPassword())), -1));
		
			UsefulData.getInstance().main.chatWindow.contactsPanel.refresh();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void finished() {
		loadingPanel.setVisible(false);
		contentPanel.setVisible(true);
	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(final Message msg) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (msg.content.equalsIgnoreCase("TRUE")) {
					lblreport.setText("Insertion Successfully");
					lblreport.setForeground(Color.GREEN);
				}

			}
		});
		refresh();
	}

}
