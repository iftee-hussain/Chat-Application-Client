package com.model.dutta;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class IdentityUser {
	public  UserDetails userDetails;
	public static String userName;
	private static String selectedFriendOrGroup = null;
	private static IdentityUser identyUser;

	public static IdentityUser getIdentityUser() {
		if (identyUser == null){
			identyUser = new IdentityUser(null);
		}
		return identyUser;
		
	}

	private IdentityUser(UserDetails userD) {
		userDetails = userD;
	}

	public  void setIdentityUser(UserDetails user) {
		userDetails = user;
	}

	public static String getSecureHash(String passwordToHash) {

		String generatedPassword = null;
		try {
			String salt = "duttatex";
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public String getSelectedFriendOrGroup() {

		return selectedFriendOrGroup;
	}

	public void setSelectedFriendOrGroup(String selectedFriendOrGroup) {

		
		UsefulData.getInstance().main.chatWindow.chatLower.setVisible(true);
		UsefulData.getInstance().main.chatWindow.chatUpper.setVisible(true);
		UsefulData.getInstance().main.chatWindow.homePanel.setVisible(false);
		UsefulData.getInstance().main.chatWindow.chatLower.clearHistory();
		UsefulData.getInstance().isGroupSelected = false;
		UsefulData.getInstance().main.chatWindow.chatLower.textArea
				.setFocusable(true);
		UsefulData.getInstance().main.chatWindow.chatLower
				.setLostFocusTextArea(false);
		UsefulData.getInstance().main.chatWindow.chatUpper.nameLabel
				.setText(selectedFriendOrGroup);
		UsefulData.getInstance().main.chatWindow.chatLower.clearHistory();
		IdentityUser.selectedFriendOrGroup = selectedFriendOrGroup;
	}

	public void setSelectedFriendOrGroup(String selectedFriendOrGroup,
			boolean isGroup) {
		UsefulData.getInstance().main.clientControl.send(new Message(
				Constant.SET_GROUP_NOTIFICATION, IdentityUser.userName,
				selectedFriendOrGroup, "false", -1));
		UsefulData.getInstance().setNotifiedGroup(selectedFriendOrGroup, false);
		UsefulData.getInstance().main.chatWindow.chatLower.clearHistory();
		UsefulData.getInstance().isGroupSelected = true;
		if (UsefulData.getInstance().selectedPanel != null)
			UsefulData.getInstance().selectedPanel.setDefault();
		UsefulData.getInstance().main.chatWindow.chatLower.textArea
				.setFocusable(true);
		UsefulData.getInstance().main.chatWindow.chatLower
				.setLostFocusTextArea(false);
		UsefulData.getInstance().main.chatWindow.chatUpper.nameLabel
				.setText(selectedFriendOrGroup);
		UsefulData.getInstance().main.chatWindow.chatLower.clearHistory();

		IdentityUser.selectedFriendOrGroup = selectedFriendOrGroup;
	}
}
