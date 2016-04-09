package com.model.dutta;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import com.modification.jpanel.JScrollPanelX;
import com.socket.Message;


public class TempHistory {
	//HashMap<String, ArrayList<TempMessage>> historyMem;
	HashMap<String, JScrollPanelX> newHistoryMem;
	public TempHistory() {
		//historyMem = new HashMap<>();
		newHistoryMem = new HashMap<String, JScrollPanelX>();
	}
	public void addTempHistory(String toWhom, JScrollPanelX panel){
		newHistoryMem.put(toWhom, panel);
	}
//	public void addTempHistory(String whom, String content, java.util.Date date, boolean isUserSender){
//		if(historyMem.get(whom)!=null){
//			historyMem.get(whom).add(new TempMessage(content, date, isUserSender));
//		}else{
//			ArrayList<TempMessage>temp = new ArrayList<>();
//			temp.add(new TempMessage(content, date, isUserSender));
//			historyMem.put(whom, temp);
//		}
//	}
//	public void addTempHistoryForGroup(String groupName, String sender, String content, java.util.Date date, boolean isUserSender){
//		if(historyMem.get(groupName)!=null){
//			historyMem.get(groupName).add(new TempMessage(content, date, isUserSender, sender, true));
//		}else{
//			ArrayList<TempMessage>temp = new ArrayList<>();
//			temp.add(new TempMessage(content, date, isUserSender, sender, true));
//			historyMem.put(groupName, temp);
//		}
//	}
	public JScrollPanelX getScrollPanel(String whom){
		return newHistoryMem.get(whom);
	}
	public boolean isExists(String whom){
		if(newHistoryMem.get(whom)!=null)
			return true;
		return false;
//		if(historyMem.get(whom)!=null)
//			return true;
//		return false;
	}
//	public ArrayList<TempMessage> getTempHistory(String whom) throws Exception{
//		
//		if(historyMem.get(whom)!=null)
//			return historyMem.get(whom);
//		else{
//			throw new Exception("No history found for user: "+whom);
//		}
//	}
	

}
