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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.model.dutta.IdentityUser;
import com.model.dutta.UserDetails;
import com.modification.jpanel.InputField;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.BackgroundWorkable;
import com.utility.dutta.UsefulData;

public class MyProfile extends JDialog implements BackgroundWorkable, CallBackable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelX panel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Point initialClick;
	private InputField userIdField;
	private InputField userNameField;
	private InputField ipAddressField;
	private InputField emailIdField;
	private InputField moPhoneField;
	private InputField postalAddressField;
	private JPanel contentPanel;
	private JLabel dashLabel;
	private JLabel lblreport;
	private InputField countryField;
	UserDetails ud ;

	public MyProfile(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.setSize(400, screenSize.height - 100);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);
		getContentPane().setLayout(null);

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
			contentPanel.setBounds(43, 135, 341, 453);
			panel.add(contentPanel);
			contentPanel.setLayout(null);
			{
				dashLabel = new JLabel("");
				dashLabel.setIcon(new ImageIcon(MyProfile.class
						.getResource("/resources/dash.png")));
				dashLabel.setBounds(0, 30, 320, 4);
				contentPanel.add(dashLabel);
			}
			//
			JLabel lblMyProfile = new JLabel("MY PROFILE");
			lblMyProfile.setBounds(0, 5, 108, 22);
			contentPanel.add(lblMyProfile);
			lblMyProfile.setForeground(Color.WHITE);
			lblMyProfile.setFont(new Font("Arial", Font.PLAIN, 18));
			// <<<<<<<<<<<<<<<<<<<<<ONLY LEBEL
			// ADDED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			JLabel lblUserId = new JLabel("Name");
			lblUserId.setBounds(0, 35, 103, 50);
			contentPanel.add(lblUserId);
			lblUserId.setForeground(Color.WHITE);
			lblUserId.setFont(new Font("Arial", Font.PLAIN, 14));
			{
				JLabel label = new JLabel("User Name");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(0, 67, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("IP Address");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(3, 103, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Email ID");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(3, 133, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Mo/Phone");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(3, 168, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Postal Address");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(3, 202, 103, 50);
				contentPanel.add(label);
			}
			{
				JLabel label = new JLabel("Country");
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Arial", Font.PLAIN, 14));
				label.setBounds(3, 236, 103, 50);
				contentPanel.add(label);
			}

			{
				String[] countryList = { "Andorra", "United Arab Emirates",
						"Afghanistan", "Antigua and Barbuda", "Anguilla",
						"Albania", "Armenia", "Netherlands Antilles", "Angola",
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
						"Seychelles" };

				Arrays.sort(countryList);

			}

			{
				postalAddressField = new InputField(0, 0, 194, 30, "User ID");
				postalAddressField.setPlaceHolderString("Postal Address");
				postalAddressField.setBounds(125, 213, 194, 30);
				
				contentPanel.add(postalAddressField);

			}
			{
				moPhoneField = new InputField(0, 0, 194, 30, "User ID");
				moPhoneField.setPlaceHolderString("Mo/Phone");
				moPhoneField.setBounds(125, 180, 194, 30);
				
				contentPanel.add(moPhoneField);

			}

			{
				emailIdField = new InputField(0, 0, 194, 30, "User ID");
				emailIdField.setBounds(125, 146, 194, 30);
				contentPanel.add(emailIdField);
				
				emailIdField.setPlaceHolderString("Email ID");
			}

			{
				ipAddressField = new InputField(0, 0, 194, 30, "User ID");
				ipAddressField.setPlaceHolderString("IP Address");
				ipAddressField.setBounds(125, 113, 194, 30);
				
				contentPanel.add(ipAddressField);

			}
			{
				userNameField = new InputField(0, 0, 194, 30, "User ID");
				userNameField.setPlaceHolderString("User Name");
				userNameField.setBounds(125, 80, 194, 30);
				userNameField.setEditable(false);
				contentPanel.add(userNameField);

			}
			// <<<<<<<<<<<<<<<<<<<<LABEL ADDED
			// END<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			{
				userIdField = new InputField(0, 0, 194, 30, "User ID");
				userIdField.setBounds(125, 46, 194, 30);
				contentPanel.add(userIdField);
				
			}
			{
				countryField = new InputField(0, 0, 194, 30, "User ID");
				countryField.setPlaceHolderString("Country");
				countryField.setBounds(125, 246, 194, 30);
				
				contentPanel.add(countryField);
			}
			
			JButton btnSave = new JButton();
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ud = new UserDetails("", userIdField.getText(), 1, ipAddressField.getText(), emailIdField.getText(), moPhoneField.getText(), postalAddressField.getText(), countryField.getText(), false, false);
					String s = ud.toString();
					UsefulData.getInstance().main.clientControl.send(MyProfile.this,
							new Message(Constant.UPDATE_MY_PROFILE, IdentityUser.userName, s, "Server", -1));
				}
			});
			btnSave.setBounds(125, 312, 113, 40);
			
			btnSave.setOpaque(false);
			btnSave.setRolloverIcon(new ImageIcon(UserAccess.class
					.getResource("/resources/saver.png")));
			btnSave.setPressedIcon(new ImageIcon(UserAccess.class
					.getResource("/resources/savep.png")));
			btnSave.setIcon(new ImageIcon(UserAccess.class
					.getResource("/resources/save.png")));
			btnSave.setBorderPainted(false);
			btnSave.setContentAreaFilled(false);
			contentPanel.add(btnSave);
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
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				setInfo();
				repaint();
				validate();
				revalidate();
			}
		});

	}

	private void setInfo() {
		userNameField.setText(IdentityUser.getIdentityUser().userName);
		userIdField.setText(IdentityUser.getIdentityUser().userDetails.fullName);
		postalAddressField.setText(IdentityUser.getIdentityUser().userDetails.postalAddress);
		countryField.setText(IdentityUser.getIdentityUser().userDetails.country);
		moPhoneField.setText(IdentityUser.getIdentityUser().userDetails.phone);
		emailIdField.setText(IdentityUser.getIdentityUser().userDetails.email);
		ipAddressField.setText(IdentityUser.getIdentityUser().userDetails.ip);


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
		userIdField.refresh();
		userNameField.refresh();
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

				int thisX = MyProfile.this.getLocation().x;
				int thisY = MyProfile.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				MyProfile.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public Long donInBackground() {
//
//		loadingPanel.setVisible(true);
//		contentPanel.setVisible(false);
//		lblreport.setText("");
//		try {
//			UserDetails user = new UserDetails();
//			user.setUserName(userNameField.getText());
//			user.setFullName(userIdField.getText());
//			user.setEmail(emailIdField.getText());
//			user.setCountry(comboCountryList.getSelectedItem().toString());
//			user.setActive(chckbxActive.isSelected());
//			user.setAdminPanelAccess(checkBoxAdminPanelAccess.isSelected());
//			new Manager().updateUserDetails(user);
//			lblreport.setText("Insertion Successfully");
//			lblreport.setForeground(Color.GREEN);
//			refresh();
//		} catch (Exception e) {
//			lblreport.setText(e.getMessage());
//			lblreport.setForeground(new Color(102, 0, 105));
//		}
		return null;
	}

	@Override
	public void finished() {
		
		contentPanel.setVisible(true);

	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void done(Message msg) {
		if(msg.type==Constant.UPDATE_MY_PROFILE)
		{
			if(msg.sender.equalsIgnoreCase("true"))
			{
				IdentityUser.getIdentityUser().userDetails.fullName = ud.fullName;
				IdentityUser.getIdentityUser().userDetails.email= ud.email;
				IdentityUser.getIdentityUser().userDetails.country=ud.country;
				IdentityUser.getIdentityUser().userDetails.postalAddress=ud.postalAddress;
				IdentityUser.getIdentityUser().userDetails.phone=ud.phone;
				IdentityUser.getIdentityUser().userDetails.ip=ud.ip;
			}
			JOptionPane.showMessageDialog(null, msg.content);
		}
		
	}
}
