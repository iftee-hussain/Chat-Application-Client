package com.model.dutta;

import com.socket.Message;

public class UserPermissions {
	public String userName;
	public boolean active;
	public boolean adminPanelAccess;
	public boolean sendFreiendRequest;
	public boolean approveFriendRequest;
	public boolean approveRemoverequest;
	public boolean calltransfer;
	public boolean sendMessage;
	public boolean recieveMessage;
	public boolean filesharing;
	public boolean groupeMessage;
	
	
	public UserPermissions() {
	
	}
	
	public UserPermissions(String msg) {
		String[] str = new String[9];
		str = msg.split(Message.SECOND_LEVEL_SEPERATOR);
		
		
		this.userName = str[0];
		this.active = Boolean.parseBoolean(str[1]);
		this.adminPanelAccess = Boolean.parseBoolean(str[2]);
		this.sendFreiendRequest = Boolean.parseBoolean(str[3]);
		this.approveFriendRequest = Boolean.parseBoolean(str[4]);
		this.approveRemoverequest =Boolean.parseBoolean(str[5]);
		this.calltransfer = Boolean.parseBoolean(str[6]);
		this.sendMessage = Boolean.parseBoolean(str[7]);
		this.recieveMessage = Boolean.parseBoolean(str[8]);
		this.filesharing= Boolean.parseBoolean(str[9]);
		this.groupeMessage=Boolean.parseBoolean(str[10]);
		
		
	}
	public UserPermissions(String userName,boolean active,boolean adminPanelAccess,boolean sendFreiendRequest,
			boolean approveFriendRequest,boolean approveRemoverequest,boolean calltransfer,
			boolean sendMessage, boolean recieveMessage,boolean filesharing,boolean groupeMessage) {
		
		
		this.userName = userName;
		this.active = active;
		this.adminPanelAccess = adminPanelAccess;
		this.sendFreiendRequest = sendFreiendRequest;
		this.approveFriendRequest = approveFriendRequest;
		this.approveRemoverequest =approveRemoverequest;
		this.calltransfer = calltransfer;
		this.sendMessage = sendMessage;
		this.recieveMessage = recieveMessage;
		this.filesharing=filesharing;
		this.groupeMessage=groupeMessage;
		
		
	}
	@Override
	public String toString() {
		return userName + Message.SECOND_LEVEL_SEPERATOR + active
				+ Message.SECOND_LEVEL_SEPERATOR + adminPanelAccess
				+ Message.SECOND_LEVEL_SEPERATOR + sendFreiendRequest
				+ Message.SECOND_LEVEL_SEPERATOR + approveFriendRequest
				+ Message.SECOND_LEVEL_SEPERATOR + approveRemoverequest
				+ Message.SECOND_LEVEL_SEPERATOR + calltransfer
				+ Message.SECOND_LEVEL_SEPERATOR + sendMessage
				+ Message.SECOND_LEVEL_SEPERATOR + recieveMessage
				+Message.SECOND_LEVEL_SEPERATOR + filesharing
				+Message.SECOND_LEVEL_SEPERATOR + groupeMessage;
				
	}
	
	
}
