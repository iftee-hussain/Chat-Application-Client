package com.modification.jpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.omg.PortableServer.AdapterActivator;

public class DisplayMessageBlock extends JPanel {
	private static final long serialVersionUID = 5445215664629654206L;
	private JPanel senderPanel;
	private JPanel datePanel;
	private JPanel otherPanel;
	private JTextArea textArea = new JTextArea();
	private int maxWidth = 590;
	private int minWidth = 590;
	private int height = 20;
	private int verticalGap = 5;
	private boolean min = false;
	private final boolean isTextPanel;
	private JLabel lblSender;
	private JLabel lblDate;
	private JPopupMenu pop;

	public DisplayMessageBlock(String sender, String message, String date,
			boolean isUser, boolean isSameUser) {

		this(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		// Text area
		setBackground(Color.white);
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setTabSize(4);
		textArea.setLineWrap(true);
		textArea.setMargin(new Insets(0, 5, 0, 10));
		textArea.setBackground(Color.WHITE);

		textArea.setText(message);
		height = textArea.getPreferredSize().height;
		add(textArea);

		// Add popup Menu
		pop = new JPopupMenu();
		JMenuItem cpy = new JMenuItem("Copy Selection");
		cpy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClipBoardHelper.setClipboardContents(textArea.getSelectedText());
			}
		});
		JMenuItem selectAll = new JMenuItem("Select All");
		selectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.selectAll();

			}
		});
		pop.add(cpy);
		pop.add(selectAll);

		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					pop.show(DisplayMessageBlock.this, arg0.getX(), arg0.getY());
				}
			}

		});

		this.lblSender.setText(sender);
		if (isSameUser)
			this.lblSender.setVisible(false);
		if (!isUser) {
			lblSender.setForeground(new Color(34, 144, 220));
			lblDate.setForeground(new Color(34, 144, 220));
		}
		this.lblDate.setText(date);
		adjustSize();
	}

	public DisplayMessageBlock(String sender, JPanel panel, String date) {
		this(false);
		setBackground(Color.WHITE);
		otherPanel = panel;
		otherPanel.setBackground(Color.WHITE);
		height = panel.getSize().height;
		otherPanel.validate();
		add(otherPanel);
		this.lblSender.setText(sender);
		this.lblDate.setText(date);
		lblSender.setForeground(new Color(34, 144, 220));
		lblDate.setForeground(new Color(34, 144, 220));
		adjustSize();
	}

	private DisplayMessageBlock(boolean isTextPanel) {
		this.isTextPanel = isTextPanel;
		setLayout(null);

		// Sender panel
		senderPanel = new JPanel();
		senderPanel.setBackground(Color.WHITE);
		add(senderPanel);
		senderPanel.setLayout(new BorderLayout(0, 0));

		lblSender = new JLabel("Name");
		lblSender.setFont(new Font("Arial", Font.PLAIN, 13));
		senderPanel.add(lblSender, BorderLayout.NORTH);

		// Date Panel
		datePanel = new JPanel();
		datePanel.setBackground(Color.WHITE);
		add(datePanel);
		datePanel.setLayout(new BorderLayout(0, 0));

		lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Arial", Font.PLAIN, 13));

		datePanel.add(lblDate, BorderLayout.NORTH);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (textArea != null)
					textArea.transferFocus();
			}
		});
		adjustSize();
	}

	public void setMax() {
		min = false;
		setSize(maxWidth, height + verticalGap);
		if (textArea != null)
			textArea.setBounds(maxWidth / 5, verticalGap, 3 * maxWidth / 5,
					height);
		if (otherPanel != null)
			otherPanel.setBounds(maxWidth / 5, verticalGap, 3 * maxWidth / 5,
					height);
		senderPanel.setBounds(0, verticalGap, maxWidth / 5, height);
		datePanel.setBounds((4 * maxWidth / 5), verticalGap, maxWidth / 5,
				height);

		repaint();
		validate();
	}

	public void setMin() {
		min = true;
		setSize(minWidth, height + verticalGap);
		if (textArea != null)
			textArea.setBounds(minWidth / 5, verticalGap, 3 * minWidth / 5,
					height);
		if (otherPanel != null)
			otherPanel.setBounds(minWidth / 5, verticalGap, 3 * minWidth / 5,
					height);
		senderPanel.setBounds(0, verticalGap, minWidth / 5, height);
		datePanel.setBounds((4 * minWidth) / 5, verticalGap, minWidth / 5,
				height);

		repaint();
		validate();
	}

	public void setText(String text) {
		textArea.setText(text);
		height = textArea.getPreferredSize().height;
		adjustSize();
	}

	public void appendText(String text) {
		textArea.append("\n" + text);
		height = textArea.getPreferredSize().height;
		adjustSize();
	}

	protected void adjustSize() {
		if (min)
			setMin();
		else
			setMax();
	}

	public boolean isTextPanel() {
		return isTextPanel;
	}

	public String getSender() {
		return lblSender.getText();
	}
}
