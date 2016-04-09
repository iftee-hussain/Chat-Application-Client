package com.view.chatWindow;

public class FriendInfo {
	public String fullName;
	public String country;
	public String userName;
	public boolean isOnline;
	public int userType;
	public FriendInfo(String userName, String fullName, String country, boolean isOnline,int type) {
		this.fullName = fullName;
		this.userName = userName;
		this.country = country;
		this.isOnline = isOnline;
		this.userType= type;
	}
}
