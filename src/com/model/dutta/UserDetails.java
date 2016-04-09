package com.model.dutta;

import com.socket.Message;

public class UserDetails {
	public int userType;
	public String fullName;
	public String userName;
	public String ip;
	public String email;
	public String phone;
	public String postalAddress;
	public String country;
	public final boolean adminPanelAccess;
	public final boolean active;

	public UserDetails(String msg) {
		String[] str = new String[10];
		str = msg.split(Message.SECOND_LEVEL_SEPERATOR);
		this.userName = str[0];
		this.fullName = str[1];
		this.userType = Integer.parseInt(str[2]);
		this.ip = str[3];
		this.email = str[4];
		this.phone = str[5];
		this.postalAddress = str[6];
		this.country = str[7];
		this.adminPanelAccess = Boolean.parseBoolean(str[8]);
		this.active = Boolean.parseBoolean(str[9]);
	}

	public UserDetails(String userName, String fullName, int userType,
			String ip, String email, String phone, String postalAddress,
			String country, boolean adminPanelAccess, boolean active) {
		this.userName = userName;
		this.fullName = fullName;
		this.userType = userType;
		this.ip = ip;
		this.email = email;
		this.phone = phone;
		this.postalAddress = postalAddress;
		this.country = country;
		this.adminPanelAccess = adminPanelAccess;
		this.active = active;
	}

	@Override
	public String toString() {
		return userName + Message.SECOND_LEVEL_SEPERATOR + fullName
				+ Message.SECOND_LEVEL_SEPERATOR + userType
				+ Message.SECOND_LEVEL_SEPERATOR + ip
				+ Message.SECOND_LEVEL_SEPERATOR + email
				+ Message.SECOND_LEVEL_SEPERATOR + phone
				+ Message.SECOND_LEVEL_SEPERATOR + postalAddress
				+ Message.SECOND_LEVEL_SEPERATOR + country
				+ Message.SECOND_LEVEL_SEPERATOR + adminPanelAccess
				+ Message.SECOND_LEVEL_SEPERATOR + active;
	}

}
