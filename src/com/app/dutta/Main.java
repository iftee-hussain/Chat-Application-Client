package com.app.dutta;

import java.io.IOException;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.admin.AdminFrame;
import com.admin.UserManage;
import com.model.dutta.IdentityUser;
import com.model.dutta.TempHistory;
import com.socket.BackgroundConnectionTest;
import com.socket.ClientControl;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.GeneralErrorReportable;
import com.utility.dutta.UsefulData;
import com.view.chatWindow.ChatFrame;
import com.view.loginWindow.LoginWindow;
import com.view.settings.SettingFrame;

public class Main {

	// private LoginWindow loginWindow;
	public ChatFrame chatWindow;
	public AdminFrame adminWindow;
	public SettingFrame settingWindow;
	public LoginWindow loginWindow;
	public UserManage userManage;
	private Main main;
	public final ClientControl clientControl;
	//public String serverAddr = "103.230.62.158";
	public String serverAddr = "localhost";
	public int port = 13000;
	public int port_for_file = 13001;
	public static String infoFilePath = System.getProperty("user.home")
			+ "/d_info";
	public GeneralErrorReportable activeWindow;
	// public ProgressBarWindow progress;
	private Timer timer;
	public TempHistory tempHistory;
	private boolean isOpen = false;

	public static void main(String[] args) {

		new Main();

	}

	public Main() {

		UsefulData.getInstance().main = this;
		clientControl = new ClientControl(serverAddr, port);

		tempHistory = new TempHistory();
		loginWindow = new LoginWindow();
		loginWindow.setIconImage(new ImageIcon(getClass().getResource(
				"/resources/icon.png")).getImage());

		loginWindow.setVisible(true);

	}

	public void openConnection() {

		if (isOpen || !UsefulData.getInstance().isConnected()) {
			try {
				clientControl.openConnection();
				isOpen = true;
			} catch (IOException e) {
				if (loginWindow != null)
					loginWindow.done("Server not found!!!");
				closeConnection();
			}
			timer = new Timer();
			timer.scheduleAtFixedRate(new BackgroundConnectionTest(
					Main.this.clientControl), 100, 1000);
		}

	}

	private void closeConnection() {
		clientControl.closeConnection();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	public void signOut() {
		if (clientControl != null) {
			clientControl.send(new Message(Constant.BYE, IdentityUser
					.getIdentityUser().userName, ".bye", "", -1));
			UsefulData.getInstance().reconnectEnable = false;
		}
	}

	public void initLoginWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				loginWindow.setIconImage(new ImageIcon(getClass().getResource(
						"/resources/icon.png")).getImage());
				;
				loginWindow.refresh();
				activeWindow = loginWindow;
				loginWindow.setVisible(true);

			}
		});
	}

	public void initChatWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				loginWindow.dispose();
				chatWindow = new ChatFrame();
				activeWindow = chatWindow;
				chatWindow.setVisible(true);
				chatWindow.setIconImage(new ImageIcon(getClass().getResource(
						"/resources/icon.png")).getImage());
			}
		});
	}

	public void initSettingWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				settingWindow = new SettingFrame(chatWindow);
				settingWindow.setIconImage(new ImageIcon(getClass()
						.getResource("/resources/icon.png")).getImage());
				settingWindow.setVisible(true);
			}
		});
	}

	public void initAdminWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				adminWindow = new AdminFrame(chatWindow);
				adminWindow.setIconImage(new ImageIcon(getClass().getResource(
						"/resources/icon.png")).getImage());
				adminWindow.setVisible(true);
			}
		});
	}

	public void destroyAdminWindow() {
		adminWindow.dispose();

	}

	// public void initProgressBar() {
	// progress = new ProgressBarWindow();
	// }

	//
	// @Override
	// public Long donInBackground() {
	//
	// main.initServer();
	//
	// return null;
	// }

	// @Override
	// public void finished() {
	// // TODO Auto-generated method stub
	//
	// }
}
