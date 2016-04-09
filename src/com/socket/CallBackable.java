package com.socket;

public interface CallBackable {
	public void done(String exception);
	public void done(Message msg);
}
