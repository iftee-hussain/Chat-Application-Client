package com.view.chatWindow;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

import com.admin.CreateAccount;
import com.model.dutta.IdentityUser;
import com.model.dutta.MeToWhom;
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

public class CallTransferWindow extends JDialog implements BackgroundWorkable,
		CallBackable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelX panel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Point initialClick;
	private JPanel contentPanel;
	private JLabel dashLabel;
	private LoadingPanel loadingPanel;
	private JLabel lblreport;
	private JComboBox<?> comboUserType;
	private int selecteditemCombo1;
	private JComboBox<String> comboUserName;
	private String[] countryLists;
	private String[] userList;

	public CallTransferWindow(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.setSize(400, 387);
		this.setLocationRelativeTo(owner);
		this.setUndecorated(true);
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			}
		} catch (Exception e) {
		}
		getContentPane().setLayout(null);
		{
			loadingPanel = new LoadingPanel();
			loadingPanel.setLocation(156, 170);
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
			contentPanel.setBounds(43, 124, 341, 245);
			panel.add(contentPanel);
			contentPanel.setLayout(null);
			{
				dashLabel = new JLabel("");
				dashLabel.setIcon(new ImageIcon(CallTransferWindow.class
						.getResource("/resources/dash.png")));
				dashLabel.setBounds(0, 30, 320, 4);
				contentPanel.add(dashLabel);
			}
			//
			JLabel lblMyProfile = new JLabel("Call Transfer");
			lblMyProfile.setBounds(0, 5, 108, 22);
			contentPanel.add(lblMyProfile);
			lblMyProfile.setForeground(Color.WHITE);
			lblMyProfile.setFont(new Font("Arial", Font.PLAIN, 18));
			{
				JLabel lblUserName = new JLabel("User Name");
				lblUserName.setForeground(Color.WHITE);
				lblUserName.setFont(new Font("Arial", Font.PLAIN, 14));
				lblUserName.setBounds(0, 76, 103, 50);
				contentPanel.add(lblUserName);
			}
			{
				JButton btnSave = new JButton("");
				btnSave.setBounds(125, 132, 97, 36);
				contentPanel.add(btnSave);
				btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String selectUser = "";
						if(comboUserName.getSelectedIndex()!=-1){
							selectUser = (String) comboUserName.getSelectedItem();
				
						}
						if(!selectUser.equals("")&&!IdentityUser.getIdentityUser().getSelectedFriendOrGroup().equals("")){
							UsefulData.getInstance().main.clientControl.send(
									new Message(Constant.CALL_TRANSFER, IdentityUser.getIdentityUser().userName, 
											IdentityUser.getIdentityUser().getSelectedFriendOrGroup()
											,selectUser, 1));
						}
						else{
							lblreport.setText("Please select a user");
						}
					}
				});
				btnSave.setOpaque(false);
				btnSave.setRolloverIcon(new ImageIcon(CallTransferWindow.class
						.getResource("/resources/saver.png")));
				btnSave.setPressedIcon(new ImageIcon(CallTransferWindow.class
						.getResource("/resources/savep.png")));
				btnSave.setIcon(new ImageIcon(CallTransferWindow.class
						.getResource("/resources/save.png")));
				btnSave.setBorderPainted(false);
				btnSave.setContentAreaFilled(false);
			}
			{
				JButton btnCancel = new JButton("");
				btnCancel.setBounds(232, 132, 97, 36);
				contentPanel.add(btnCancel);
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				btnCancel.setRolloverIcon(new ImageIcon(
						CallTransferWindow.class
								.getResource("/resources/cancelr.png")));
				btnCancel.setPressedIcon(new ImageIcon(CallTransferWindow.class
						.getResource("/resources/cancelp.png")));
				btnCancel.setIcon(new ImageIcon(CallTransferWindow.class
						.getResource("/resources/cancel.png")));
				btnCancel.setBorderPainted(false);
				btnCancel.setContentAreaFilled(false);
			}

			JLabel lblUserType = new JLabel("User Type");
			lblUserType.setForeground(Color.WHITE);
			lblUserType.setFont(new Font("Arial", Font.PLAIN, 14));
			lblUserType.setBounds(0, 35, 103, 50);
			String[] list = { "Superuser", "Admin", "Staff", "Customer" };
			contentPanel.add(lblUserType);
			{
				comboUserType = new JComboBox<Object>(list);
				comboUserType.setOpaque(false);
				comboUserType.setBounds(125, 45, 195, 30);
				comboUserType.setBorder(BorderFactory.createEmptyBorder());
				comboUserType.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						addComboUserList(comboUserType.getSelectedIndex()+1);
						
					}
				});
				contentPanel.add(comboUserType);

			}
			{

				comboUserName = new JComboBox<>();
				comboUserName.setBounds(125, 89, 194, 30);
				contentPanel.add(comboUserName);

			}
		}
		{
			lblreport = new JLabel("");
			lblreport.setHorizontalAlignment(SwingConstants.CENTER);
			lblreport.setForeground(new Color(102, 0, 51));
			lblreport.setFont(new Font("Arial", Font.PLAIN, 14));
			lblreport.setBounds(20, 330, 356, 35);
			panel.add(lblreport);
		}

		refresh();
	}

	private void addComboUserList(int type) {

		final ArrayList<String> userArrayList = new ArrayList<>();
		userArrayList.clear();
		for (MeToWhom data : UsefulData.getInstance().chatwiths) {

			if (data.yesOrNo
					&& UsefulData.getInstance().find(data.chatWith).isOnline
					&& !IdentityUser.getIdentityUser().getSelectedFriendOrGroup()
							.equals(UsefulData.getInstance()
									.find(data.chatWith).userName)
					&& UsefulData.getInstance().find(data.chatWith).userType == type) {
				userArrayList.add(data.chatWith);
			}
		}
		Collections.sort(userArrayList);
		comboUserName.removeAllItems();
		for (String string : userArrayList) {
			comboUserName.addItem(string);
		}

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

				int thisX = CallTransferWindow.this.getLocation().x;
				int thisY = CallTransferWindow.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				CallTransferWindow.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public Long donInBackground() {
		loadingPanel.setVisible(true);
		contentPanel.setVisible(false);
		lblreport.setText("");

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
