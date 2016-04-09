package com.model.dutta;

public class TempMessage {

	
		private boolean isUserSender;
		private String content;
		private java.util.Date date;
		private boolean isGroup;
		private String sender;
		public TempMessage(String c, java.util.Date d, boolean isSender) {
			this(c, d, isSender, "", false);
		}
		public TempMessage(String c, java.util.Date d, boolean isSender, String sender, boolean isGroup) {
			content = c;
			date = d;
			isUserSender = isSender;
			this.isGroup = isGroup;
			this.setSender(sender);
		}
		@Override
		public String toString() {
		// TODO Auto-generated method stub
		return "Sender: "+sender+" Content: "+content+" Date: "+date.toLocaleString()+" isGroup: "+isGroup+" isUserSender: "+isUserSender+System.lineSeparator();
		}
		public boolean isUserSender() {
			return isUserSender;
		}

		public String getContent() {
			return content;
		}

		public java.util.Date getDate() {
			return date;
		}
		public boolean isGroup() {
			return isGroup;
		}
		public String getSender() {
			return sender;
		}
		public void setSender(String sender) {
			this.sender = sender;
		}


}
