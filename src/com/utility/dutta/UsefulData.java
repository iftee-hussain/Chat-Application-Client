package com.utility.dutta;

import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.admin.groupchat.CreateGroup;
import com.app.dutta.Main;
import com.model.dutta.IdentityUser;
import com.model.dutta.MeToWhom;
import com.model.dutta.UserPermissions;
import com.socket.Constant;
import com.socket.Download;
import com.socket.Message;
import com.socket.Upload;
import com.view.chatWindow.FriendInfo;
import com.view.chatWindow.InfoPanel;

public class UsefulData {
	private static final UsefulData oSingletonClass = new UsefulData();
	public ArrayList<String> current_all_users = new ArrayList<>();
	public ArrayList<FriendInfo> current_all_usersinfo = new ArrayList<>();
	public String[] countryList;
	public Main main;
	public ArrayList<FriendInfo> friendInfoList = new ArrayList<>();
	public ArrayList<MeToWhom> chatwiths = new ArrayList<>();
	public UserPermissions userPermissions = new UserPermissions();
	public InfoPanel selectedPanel = null;
	public String userpass = "";
	public String filteredText = "";
	public String[] groupList = null;
	public HashMap<String, ArrayList<String>> groupAndMembers = new HashMap<>();
	private HashMap<String, Boolean> notificationMap = new HashMap<>();
	private int notifiedGroup = 0;
	public boolean isGroupSelected = false;
	public Upload upload;
	public Download download;
	public boolean reconnectEnable = false;
	private boolean isConnected = false;
	public boolean isUploaderBusy = false;
	public boolean isDownloaderBusy = false;

	private UsefulData() {

	}
//Permission Method
	public boolean haveFilePermission(){
		int userType = IdentityUser.getIdentityUser().userDetails.userType;
		if(UsefulData.getInstance().userPermissions.filesharing||userType==1||userType==2||userType==3){
			return true;
		}
		return false;
	}
	public boolean haveGroupPermission(){
		if(UsefulData.getInstance().userPermissions.groupeMessage||IdentityUser.getIdentityUser().userDetails.userType==1)
			return true;
		return false;
	}
	public boolean haveChatTransferPermission(){
		if(UsefulData.getInstance().userPermissions.calltransfer||IdentityUser.getIdentityUser().userDetails.userType==1)
			return true;
		return false;
	}
	public boolean isNotifiedGroup(String groupName) {
		if (notificationMap.get(groupName) != null) {
			return notificationMap.get(groupName);
		}
		return false;
	}

	public void setNotifiedGroup(String groupName, boolean flag) {
		if (flag)
			notifiedGroup++;
		else if (notifiedGroup != 0)
			notifiedGroup--;

		if (notifiedGroup == 0) {
			UsefulData.getInstance().main.chatWindow.chatUpper.plusButton
					.setIcon(new ImageIcon(CreateGroup.class
							.getResource("/resources/plus.png")));

		} else {
			UsefulData.getInstance().main.chatWindow.chatUpper.plusButton
					.setIcon(new ImageIcon(CreateGroup.class
							.getResource("/resources/plusnotified.png")));
		}
		notificationMap.put(groupName, flag);
	}

	public static synchronized UsefulData getInstance() {
		return oSingletonClass;
	}

	public void addGroupName(String name) {

		String[] n = new String[groupList.length + 1];

		int i = 0;
		for (String item : groupList) {
			n[i++] = item;
		}
		n[i] = name;
		Arrays.sort(n);
		groupList = n;
	}

	public void deleteGroupName(String groupName) {

		String[] n = new String[groupList.length - 1];
		int i = 0;
		for (String item : groupList)
			if (!groupName.equals(item)) {
				n[i++] = item;
			}
		Arrays.sort(n);
		groupList = n;
	}

	public FriendInfo find(String s) {
		for (FriendInfo e : friendInfoList) {
			if (e.userName.equals(s)) {
				return e;
			}

		}
		return null;

	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public void sendFileWindowShow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.showDialog(UsefulData.getInstance().main.chatWindow, "Send File");
		File file = fileChooser.getSelectedFile();
		if (file != null) {
			long fileSize = file.length() / 1024;
			UsefulData.getInstance().main.clientControl.send(
					new Message(Constant.UPLOAD_REQUEST, IdentityUser.userName,
							file.getName() + Message.SECOND_LEVEL_SEPERATOR
									+ fileSize, IdentityUser
									.getIdentityUser().getSelectedFriendOrGroup(), -1), file);

		}

	}

}
