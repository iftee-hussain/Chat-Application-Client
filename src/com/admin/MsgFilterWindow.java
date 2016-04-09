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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.model.dutta.IdentityUser;
import com.modification.jpanel.JPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class MsgFilterWindow extends JDialog implements CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel back_panel;
	private Point initialClick;
	private JTextArea textArea;
	private JLabel lblError;
	private JPanel panel;
	private JLabel lblNewLabel;

	public MsgFilterWindow(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		new JPanelX("/resources/backload.png", this);
		back_panel = new JPanelX("/resources/msgfilter.png", this, true);
		setContentPane(back_panel);
		back_panel.setLayout(null);
		// loadingPanel = getLoadingPanel();
		setResizable(false);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 550);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		addMoveListener(back_panel);

		JButton btnSave = new JButton("");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String txt = textArea.getText().replaceAll("\\s+", "");
				UsefulData.getInstance().filteredText = txt;
				UsefulData.getInstance().main.clientControl.send(
						MsgFilterWindow.this, new Message(
								Constant.FILTERED_TEXT, IdentityUser.userName,
								txt, "SET", -1));
			}
		});
		btnSave.setBounds(50, 400, 97, 36);
		btnSave.setOpaque(false);
		btnSave.setRolloverIcon(new ImageIcon(MsgFilterWindow.class
				.getResource("/resources/saver.png")));
		btnSave.setPressedIcon(new ImageIcon(MsgFilterWindow.class
				.getResource("/resources/savep.png")));
		btnSave.setIcon(new ImageIcon(MsgFilterWindow.class
				.getResource("/resources/save.png")));
		btnSave.setBorderPainted(false);
		btnSave.setContentAreaFilled(false);
		back_panel.add(btnSave);

		JButton btnCancel = new JButton("");
		btnCancel.setBounds(170, 400, 97, 36);
		back_panel.add(btnCancel);

		btnCancel.setRolloverIcon(new ImageIcon(MsgFilterWindow.class
				.getResource("/resources/cancelr.png")));
		btnCancel.setPressedIcon(new ImageIcon(MsgFilterWindow.class
				.getResource("/resources/cancelp.png")));
		btnCancel.setIcon(new ImageIcon(MsgFilterWindow.class
				.getResource("/resources/cancel.png")));
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						dispose();

					}
				});

			}
		});

		JButton crossButton = new JButton(new ImageIcon(
				MsgFilterWindow.class.getResource("/resources/cross.png")));
		crossButton.setBounds(400, 15, 24, 20);
		crossButton.setRolloverIcon(new ImageIcon(MsgFilterWindow.class
				.getResource("/resources/cra.png")));
		crossButton.setBorderPainted(false);
		crossButton.setContentAreaFilled(false);
		back_panel.add(crossButton);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(50, 230, 350, 130);
		back_panel.add(panel);
		panel.setLayout(null);

		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 15));
		textArea.setBounds(5, 5, 340, 120);
		panel.add(textArea);
		textArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		lblError = new JLabel("");
		lblError.setForeground(new Color(255, 153, 187));
		lblError.setFont(new Font("Arial", Font.PLAIN, 16));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(73, 512, 327, 20);
		back_panel.add(lblError);

		lblNewLabel = new JLabel(
				"Message Filter for Key Words(Seperated by Commas)");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Serif", Font.PLAIN, 14));
		lblNewLabel.setBounds(50, 200, 318, 20);
		back_panel.add(lblNewLabel);
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
		if (UsefulData.getInstance().filteredText.equals("")) {
			UsefulData.getInstance().main.clientControl.send(
					MsgFilterWindow.this, new Message(Constant.FILTERED_TEXT,
							IdentityUser.userName, "", "GET", -1));
		} else {
			textArea.setText(UsefulData.getInstance().filteredText);
		}
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

				int thisX = MsgFilterWindow.this.getLocation().x;
				int thisY = MsgFilterWindow.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				MsgFilterWindow.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public void done(String exception) {

	}

	@Override
	public void done(final Message msg) {
		if (msg.sender.equalsIgnoreCase("GET")) {
			if (msg.recipient.equalsIgnoreCase("ERROR"))
				lblError.setText("Error while loading data");
			else {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						UsefulData.getInstance().filteredText = msg.content;
						textArea.setText(msg.content);

					}
				});
			}
		} else if (msg.sender.equalsIgnoreCase("SET")) {
			if (msg.recipient.equalsIgnoreCase("SUCCESS")) {
				lblError.setText("Success");
			} else {
				lblError.setText("Error while set filter text");
			}
		}

	}
}
