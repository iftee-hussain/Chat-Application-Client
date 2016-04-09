package com.view.chatWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sun.awt.shell.ShellFolder;

import com.model.dutta.IdentityUser;
import com.modification.jpanel.ClipBoardHelper;
import com.modification.jpanel.DisplayMessageBlock;
import com.modification.jpanel.JPanelX;
import com.modification.jpanel.JScrollPanelX;
import com.socket.CallBackable;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class ChatLower extends JPanel implements CallBackable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension defaultSize = new Dimension(600, 450);
	private Dimension maxSize = new Dimension(600, Toolkit.getDefaultToolkit()
			.getScreenSize().height - 140);
	public JButton sendButton;
	private JPanelX container;
	public JTextArea textArea;
	private JScrollPane pane;
	private HistoryPanel historyManager;
	private JScrollPanelX historyPanel;
	public FileHistoryPanel fileHistoryPanel;
	final ImageIcon icoActive = new ImageIcon(
			ChatLower.class.getResource("/resources/chateditboxactive.png"));
	final ImageIcon ico = new ImageIcon(
			ChatLower.class.getResource("/resources/chateditbox.png"));
	private JProgressBar progressBar;
	private JScrollPane scrollPane;
	private JLabel progFileSending = new JLabel("File Sending......");
	private JLabel progFileRecei = new JLabel("File receiving......");
	private JButton progFileCancelButton = new JButton(new ImageIcon(
			ChatLower.class.getResource("/resources/fc.png")));
	private DisplayMessageBlock lastDisplayMessageBlock = null;
	private boolean clear = false;

	public ChatLower() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setLayout(null);
		setLocation(304, 51 + 55);
		setOpaque(false);

		// >>>>>>>>>>>Send Button>>>>>>>>>>>>>>>>>
		sendButton = new JButton(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/send.png")));
		sendButton.setBorderPainted(false);
		sendButton.setContentAreaFilled(false);
		sendButton.setRolloverIcon(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/sendr.png")));
		sendButton.setPressedIcon(new ImageIcon(ChatUpper.class.getClass()
				.getResource("/resources/sendp.png")));

		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				sendMessage();

			}
		});
		add(sendButton);
		// Progress Bar
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progFileRecei.setFont(new Font("Arial", Font.BOLD, 12));
		progFileSending.setFont(new Font("Arial", Font.BOLD, 12));
		progFileCancelButton.setBorderPainted(false);
		progFileCancelButton.setRolloverIcon(new ImageIcon(ChatLower.class
				.getResource("/resources/fcr.png")));
		progFileCancelButton.setContentAreaFilled(false);
		progFileCancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (UsefulData.getInstance().download != null) {
					UsefulData.getInstance().download.stopDownloader();
					UsefulData.getInstance().download = null;
				}
				if (UsefulData.getInstance().upload != null) {
					UsefulData.getInstance().upload.stopUploader();
					UsefulData.getInstance().upload = null;
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ChatLower.this.setFileTransferEnable(false, false);
					}
				});

			}
		});
		progressBar.setFont(new Font("Arial", Font.BOLD, 12));
		// add(progFileRecei);
		// add(progFileSending);
		// add(progressBar);
		// add(progFileCancelButton);
		// >>>>>>>>>>>>>>History Panel>>>>>>>>>>>>>>>>>>>
		container = new JPanelX("/resources/chateditbox.png", this);
		container.setSize(600, 135);
		// container.setPressedIcon("/resources/chatEditBoxActive.png", this);

		// >>>>>>>>>>textArea>>>>>>>>>>>>>>>>>>
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(3, 3, 594, 129);
		textArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		textArea.setDropTarget(new DropTarget() {
			@Override
			public synchronized void drop(DropTargetDropEvent arg0) {
				if (UsefulData.getInstance().main.chatWindow.contactsPanel
						.isOnline(IdentityUser.getIdentityUser()
								.getSelectedFriendOrGroup()))
					new DropFileSending(IdentityUser.getIdentityUser()
							.getSelectedFriendOrGroup()).drop(arg0);
				else {
					JOptionPane.showMessageDialog(
							UsefulData.getInstance().main.chatWindow,
							"Error!!! Please select online user.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
		InputMap inputMap = textArea.getInputMap();
		inputMap.put(enter, "none");
		// add shift+enter keybinding can be on pressed or released i.e false or
		// true
		textArea.getInputMap(JComponent.WHEN_FOCUSED)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
						KeyEvent.SHIFT_DOWN_MASK, true), "Shift+Enter released");
		textArea.getActionMap().put("Shift+Enter released",
				new AbstractAction() {
					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent ae) {
						textArea.setText(textArea.getText() + "\n");
					}
				});
		textArea.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				"Enter released");
		textArea.getActionMap().put("Enter released", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				sendMessage();
			}
		});
		final JPopupMenu pop = new JPopupMenu();
		JMenuItem cpy = new JMenuItem("Copy Selection");
		cpy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClipBoardHelper.setClipboardContents(textArea.getSelectedText());
			}
		});
		JMenuItem paste = new JMenuItem("Paste");
		paste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = ClipBoardHelper.getClipboardContents();
				textArea.paste();
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
		pop.add(paste);
		pop.add(selectAll);

		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					pop.show(textArea, arg0.getX(), arg0.getY());
				}
			}

		});
		setFocusable(false);
		textArea.setFocusable(false);

		// >>>>>>>>>>>history panel<<<<<<<<<<<<<<<
		historyManager = new HistoryPanel();

		historyPanel = new JScrollPanelX();
		historyPanel.setBounds(20, 20, defaultSize.width, defaultSize.height);
		historyPanel
				.setHorizontalScrollBarPolicy(JScrollPanelX.HORIZONTAL_SCROLLBAR_NEVER);
		historyPanel.setVerticalGap(5);
		historyPanel.setLocation(0, 0);
		historyPanel.setBorder(null);

		add(historyPanel);

		// historyPanel.refresh();

		pane = new JScrollPane(textArea);
		pane.setBounds(3, 3, 594, 129);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBorder(BorderFactory.createEmptyBorder());
		container.add(pane);
		add(container);
		setDefatult();
		if (UsefulData.getInstance().userPermissions.sendMessage == false) {
			sendButton.setVisible(false);
			textArea.setVisible(false);
			container.setVisible(false);

		}
		setFileTransferEnable(false, false);

	}

	public void setProgressBarValue(final int value) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				ChatLower.this.fileHistoryPanel.setValueOfProgressBar(value);

			}
		});
	}

	public void setProgressBarDeterminate() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				ChatLower.this.fileHistoryPanel
						.setIndeterminateProgressBar(false);

			}
		});
	}

	public void setProgressBarStatus(final String status) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				ChatLower.this.fileHistoryPanel.setStatusText(status);

			}
		});
	}

	public void hideProgressBar(boolean isShow) {

		ChatLower.this.fileHistoryPanel.hideProgressBar(isShow);

	}

	public void setFileTransferEnable(boolean fileTransferEnable,
			boolean isSending) {
		// progressBar.setVisible(fileTransferEnable);
		// progFileRecei.setVisible(fileTransferEnable&&!isSending);
		// progFileSending.setVisible(fileTransferEnable&&isSending);
		// progFileCancelButton.setVisible(fileTransferEnable);

	}

	private void sendMessage() {
		if (textArea.getText().length() > 0) {
			if (!UsefulData.getInstance().isGroupSelected) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss dd, MMM");
				historyManager.addMessage(
						IdentityUser.getIdentityUser().userName,
						textArea.getText(), df.format(cal.getTime()));
				// UsefulData.getInstance().main.tempHistory.addTempHistory(
				// IdentityUser.getIdentityUser()
				// .getSelectedFriendOrGroup(),
				// textArea.getText(), cal.getTime(), true);

				UsefulData.getInstance().main.clientControl.send(new Message(
						Constant.MESSAGE,
						IdentityUser.getIdentityUser().userName, textArea
								.getText(), IdentityUser.getIdentityUser()
								.getSelectedFriendOrGroup(), -1));
				textArea.setText("");
			} else {

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss dd, MMM");
				historyManager.addMessage(
						IdentityUser.getIdentityUser().userName,
						textArea.getText(), df.format(cal.getTime()));
				// UsefulData.getInstance().main.tempHistory.addTempHistory(
				// IdentityUser.getIdentityUser()
				// .getSelectedFriendOrGroup(),
				// textArea.getText(), cal.getTime(), true);
				UsefulData.getInstance().main.clientControl.send(new Message(
						Constant.GROUP_MESSAGE, IdentityUser.userName, textArea
								.getText(), IdentityUser.getIdentityUser()
								.getSelectedFriendOrGroup(), -1));
				textArea.setText("");
			}
		}
	}

	public void setDefatult() {
		// historyButton.setLocation(defaultSize.width-162, 13);
		// progFileCancelButton.setBounds(572, defaultSize.height - 30, 26, 30);
		sendButton.setBounds(0, defaultSize.height - 28, 59, 27);
		// progressBar.setBounds(240, defaultSize.height - 26, 320, 23);
		// progFileRecei.setBounds(130, defaultSize.height - 28, 90, 26);
		// progFileSending.setBounds(130, defaultSize.height - 28, 100, 26);
		container.setLocation(0, defaultSize.height - 28 - 135 - 15);
		historyPanel.setSize(defaultSize.width, 235);

		historyPanel.refresh();
		// scrollPane.setSize(defaultSize.width, 255);
		setLocation(304, 106);
		setSize(defaultSize);
		repaint();
	}

	public void setLostFocusTextArea(boolean f) {
		if (f) {
			container.setBackgroundImage(ico);
			sendButton.requestFocus();
		} else {
			container.setBackgroundImage(icoActive);
			textArea.requestFocus();
		}

	}

	public void setMax() {
		int con = (Toolkit.getDefaultToolkit().getScreenSize().width - 960) / 2;

		// progFileCancelButton.setBounds(572, maxSize.height - 30, 26, 30);
		sendButton.setBounds(0, maxSize.height - 28, 59, 27);
		// progressBar.setBounds(240, maxSize.height - 26, 320, 23);
		// progFileRecei.setBounds(130, maxSize.height - 28, 90, 26);
		// progFileSending.setBounds(130, maxSize.height - 28, 100, 26);

		sendButton.setBounds(0, maxSize.height - 28, 59, 27);
		container.setLocation(0, maxSize.height - 28 - 135 - 15);
		historyPanel.setSize(defaultSize.width, 400);
		historyPanel.refresh();
		// scrollPane.setSize(defaultSize.width, 430);
		setLocation(304 + con, 106);
		setSize(maxSize);
		repaint();
	}

	public void handleIncommingText(String sender, String message) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a dd, MMM");
		if (sender.equals(IdentityUser.getIdentityUser()
				.getSelectedFriendOrGroup()))
			historyManager
					.addMessage(sender, message, df.format(cal.getTime()));
		// UsefulData.getInstance().main.tempHistory.addTempHistory(sender,
		// message, cal.getTime(), false);

	}

	public void handaleIncommingTextForGroup(String sender, String message,
			String groupName) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a dd, MMM");
		historyManager.addMessageForGroup(sender, message,
				df.format(cal.getTime()), groupName);
		// UsefulData.getInstance().main.tempHistory.addTempHistoryForGroup(
		// groupName, sender, message, cal.getTime(), false);

	}

	public void handaleFile(String sender, String filePath, boolean download) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a dd, MMM");
		historyManager.addFileHistory(sender, filePath,
				df.format(cal.getTime()), download);
		// if (download)
		// UsefulData.getInstance().main.tempHistory.addTempHistory(sender,
		// "Download completed: " + filePath, cal.getTime(), true);
		// else
		// UsefulData.getInstance().main.tempHistory.addTempHistory(sender,
		// "Upload completed: ", cal.getTime(), true);
	}

	public void addHistoryMessage(String sender, String message, Date date) {
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a dd, MMM");
		historyManager.addMessage(sender, message, df.format(date.getTime()));
	}

	public void addHistoryMessageForGroup(String sender, String message,
			Date date, String groupName) {
		SimpleDateFormat df = new SimpleDateFormat("hh:mm a dd, MMM");
		historyManager.addMessageForGroup(sender, message,
				df.format(date.getTime()), groupName);
	}

	public void addFileHistory(String username, File file, String time,
			boolean isDownloader) {
		historyManager.addFileHistoryTOScrollPanel(username, file, time,
				isDownloader);
	}

	class HistoryPanel {
		private static final long serialVersionUID = 1L;

		void addFileHistory(String sender, String filePath, String time,
				boolean download) {
			// if (download)
			// addHTMLmsg(sender, "Download completed: " + filePath, time);
			// else
			// addHTMLmsg(sender, "Upload completed: " + filePath, time);

		}

		void addMessage(String sender, String message, String time) {

			if (IdentityUser.getIdentityUser().getSelectedFriendOrGroup() != null) {
				addMessageToScrollPanel(sender, message, time, false);
			}

		}

		public void addFileHistoryTOScrollPanel(String sender, File file,
				String time, boolean isDownloader) {
			ShellFolder sf = null;
			fileHistoryPanel = new FileHistoryPanel(file, isDownloader);
			try {
				sf = ShellFolder.getShellFolder(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ImageIcon icon = new ImageIcon(sf.getIcon(true));

			fileHistoryPanel.setIcon(icon);
			fileHistoryPanel.setImageName(file.getName());
			fileHistoryPanel.setStatusText("Waiting...");

			fileHistoryPanel.setSize(defaultSize.width, 60);
			fileHistoryPanel.setPreferredSize(new Dimension(defaultSize.width,
					60));

			DisplayMessageBlock messageBlock = new DisplayMessageBlock(sender,
					fileHistoryPanel, time);
			messageBlock.setSize(defaultSize.width, 60);
			lastDisplayMessageBlock = messageBlock;

			historyPanel.add(messageBlock);
			historyPanel.repaint();
			messageBlock.repaint();
			messageBlock.validate();

		}

		private void addMessageToScrollPanel(String sender, String message,
				String time, boolean isGroup) {

			if (!isGroup) {

				if (IdentityUser.getIdentityUser().userDetails.userType != 1
						&& UsefulData.getInstance().find(sender).userType != 1) {

					String fill[] = UsefulData.getInstance().filteredText
							.split(",");
					boolean flag = false;
					for (String string : fill) {
						if (message.contains(string)) {
							flag = true;
							break;
						}
					}
					if (flag)
						message = "******";
				}
			} else {

				String fill[] = UsefulData.getInstance().filteredText
						.split(",");
				boolean flag = false;
				for (String string : fill) {
					if (message.contains(string)) {
						flag = true;
						break;
					}
				}
				if (flag)
					message = "******";
			}

			DisplayMessageBlock messageBlock;
			if (lastDisplayMessageBlock != null) {
				messageBlock = new DisplayMessageBlock(sender, message, time,
						IdentityUser.userName.equals(sender),
						lastDisplayMessageBlock.getSender().equals(sender));
				messageBlock.appendText(message);
			} else {
				messageBlock = new DisplayMessageBlock(sender, message, time,
						IdentityUser.userName.equals(sender), false);

			}

			messageBlock.setSize(defaultSize.width, 0);
			messageBlock.setText(message);

			historyPanel.add(messageBlock);

			lastDisplayMessageBlock = messageBlock;

			messageBlock.repaint();
			messageBlock.validate();
		}

		void addMessageForGroup(String sender, String message, String time,
				String groupName) {
			if (!IdentityUser.getIdentityUser().getSelectedFriendOrGroup()
					.equals("")
					&& !message.equals("")
					&& (IdentityUser.getIdentityUser()
							.getSelectedFriendOrGroup().equals(groupName) || IdentityUser.userName
							.equals(sender))) {
				addMessageToScrollPanel(sender, message, time, true);
			}
		}

	}

	public void clearHistory() {
		lastDisplayMessageBlock = null;
		historyPanel.clear();

	}

	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void done(Message msg) {

	}

}

class FileHistoryPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblImage = new JLabel();
	private JLabel lblImageName = new JLabel();
	private JLabel lblStatus = new JLabel();
	private JProgressBar progressBar = new JProgressBar();
	private JButton btnFileCrossButton = new JButton();
	private JLabel showButton = new JLabel("Show");
	private File file;
	private boolean isDownloader;

	public FileHistoryPanel(File f, boolean isDownloader) {
		this.file = f;
		this.isDownloader = isDownloader;
		setLayout(null);
		setBackground(Color.WHITE);
		lblImage.setBounds(0, 0, 32, 32);
		add(lblImage);

		lblImageName.setFont(new Font("Arial", Font.PLAIN, 11));
		lblImageName.setBounds(45, 0, 200, 16);
		add(lblImageName);

		lblStatus.setFont(new Font("Arial", Font.PLAIN, 11));
		lblStatus.setBounds(45, 15, 81, 16);
		add(lblStatus);

		showButton.setBounds(250, 0, 50, 32);
		showButton.setVisible(false);
		showButton.setFont(new Font("Arial", Font.PLAIN, 13));
		showButton.setForeground(new Color(0, 144, 229));

		showButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// JFileChooser fs = new JFileChooser();
				// fs.setCurrentDirectory(file);
				// fs.setSelectedFile(file);
				// fs.showOpenDialog(UsefulData.getInstance().main.chatWindow);
				FileDialog fd = new FileDialog(
						UsefulData.getInstance().main.chatWindow,
						"File Explorer", FileDialog.LOAD);
				fd.setFile(file.getAbsolutePath());
				System.out.println(file.getAbsolutePath());

				fd.setVisible(true);

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				showButton.setForeground(new Color(0, 120, 200));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				showButton.setForeground(new Color(0, 144, 229));

			}
		});
		add(showButton);

		progressBar.setBounds(150, 15, 159, 12);
		add(progressBar);
		progressBar.setStringPainted(false);
		progressBar.setIndeterminate(true);

		btnFileCrossButton.setBounds(322, 14, 18, 15);
		btnFileCrossButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!FileHistoryPanel.this.isDownloader)
					UsefulData.getInstance().upload.cancel();
				else
					UsefulData.getInstance().download.cancel();

			}
		});
		btnFileCrossButton.setIcon(new ImageIcon(ChatLower.class
				.getResource("/resources/fcb.png")));
		btnFileCrossButton.setRolloverIcon((new ImageIcon(ChatLower.class
				.getResource("/resources/fcbr.png"))));
		add(btnFileCrossButton);
	}

	public void hideProgressBar(boolean isShow) {
		progressBar.setVisible(false);
		btnFileCrossButton.setVisible(false);
		if (isShow)
			showButton.setVisible(true);

	}

	void setIcon(ImageIcon icon) {
		lblImage.setIcon(icon);
	}

	public void setStatusText(String text) {
		lblStatus.setText(text);
	}

	void setImageName(String imageName) {
		lblImageName.setText(imageName);
	}

	public void setIndeterminateProgressBar(boolean flag) {
		progressBar.setIndeterminate(flag);
		progressBar.setStringPainted(true);
	}

	public void setValueOfProgressBar(int value) {
		progressBar.setValue(value);
	}
}
