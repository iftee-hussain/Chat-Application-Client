package com.view.chatWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.model.dutta.IdentityUser;
import com.modification.jpanel.JPanelX;
import com.utility.dutta.UsefulData;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.ImageIcon;


public class MiniNotification extends JDialog {

	private JPanel contentPane;
	public JLabel label = new JLabel();

	
	/**
	 * Create the frame.
	 */
	public MiniNotification() {
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MiniNotification.class.getResource("/resources/icon.png")));

		setSize(250, 80);
		setResizable(false);
		setUndecorated(true);
		setAlwaysOnTop(true);
		contentPane = new JPanelX("/resources/back.png",this,true);;
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setForeground(Color.WHITE);
		label.setBounds(9, 27, 230, 27);
		contentPane.add(label);
		
		JLabel lblSendsAMessage = new JLabel("sends a message");
		lblSendsAMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblSendsAMessage.setForeground(Color.WHITE);
		lblSendsAMessage.setBounds(9, 48, 230, 27);
		contentPane.add(lblSendsAMessage);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(2, 2, 246, 21);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(MiniNotification.class.getResource("/resources/duttatop.png")));
		label_1.setBounds(89, 3, 84, 14);
		panel.add(label_1);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mousePressed(arg0);
				UsefulData.getInstance().main.chatWindow.setState(JFrame.NORMAL);
				UsefulData.getInstance().main.chatWindow.contactsPanel.selectFriend(label.getText());
				dispose();
			}
		});
	}
}
