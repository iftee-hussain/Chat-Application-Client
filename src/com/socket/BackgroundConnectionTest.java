package com.socket;

import java.io.IOException;
import java.util.TimerTask;

import com.model.dutta.IdentityUser;
import com.utility.dutta.UsefulData;

public class BackgroundConnectionTest extends TimerTask implements CallBackable{
	ClientControl clientControl;
	public BackgroundConnectionTest(ClientControl clientControl) {
		this.clientControl = clientControl;
	}
	@Override
	public void run() {
		if(UsefulData.getInstance().reconnectEnable&&!UsefulData.getInstance().isConnected()){
			try {
				clientControl.openConnection();
				UsefulData.getInstance().setConnected(true);
			} catch (IOException e) {
				System.err.println("BackgroundConnectionTest: Trying to connect.....");
			}
			clientControl.sineIn(IdentityUser.userName, UsefulData.getInstance().userpass);
		}
			
	}
	@Override
	public void done(String exception) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void done(Message msg) {
		//System.out.println(msg.content);
		
	}
}
