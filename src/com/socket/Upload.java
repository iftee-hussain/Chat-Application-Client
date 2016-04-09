package com.socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.model.dutta.IdentityUser;
import com.utility.dutta.UsefulData;

public class Upload extends Thread {

	private Socket socket;
	private FileInputStream fileInputStream;
	private OutputStream outputStream;
	private InputStream inputStream;
	private static final int START = 1;
	private static final int OK = 2;
	private static final int COMPLETE = 3;
	private static final int ERROR = 4;
	private static final int HEADER = 10;
	private static final int CANCEL = 11;

	ObjectOutputStream objectOutputStream;
	private byte[] buffer = new byte[1024];
	private boolean stop = false;
	private long fileSize;
	private File file;
	int counter = 0;
	String userName;
	int newPer;
	int oldPer;
	public Upload(String addr, int port, File path, String userName)
			throws UnknownHostException, IOException {
		super();
		file = path;
		this.userName= userName;
		socket = new Socket(InetAddress.getByName(addr), port);
		outputStream = socket.getOutputStream();
		outputStream.flush();
		inputStream = socket.getInputStream();
		UsefulData.getInstance().isUploaderBusy = true;
		fileSize = file.length()/1024;
		System.out.println("File size: "+(fileSize/(1024))+" MB");
		fileInputStream = new FileInputStream(file);
		
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectOutputStream.flush();
		//For header
		this.outputStream.write(HEADER);
		objectOutputStream.writeUTF(userName);
		objectOutputStream.flush();
		UsefulData.getInstance().main.chatWindow.chatLower.addFileHistory(IdentityUser.userName, file, Calendar.getInstance().getTime().toString(), false);

	}

	private void send(int status, byte[] b, int len) throws IOException {
		outputStream.write(status);
		outputStream.flush();
		outputStream.write(b, 0, len);
		outputStream.flush();
	}
	public void cancel(){
		try {
			outputStream.write(CANCEL);
			outputStream.flush();
			UsefulData.getInstance().main.chatWindow.chatLower.setProgressBarStatus("Cancelled.");
			UsefulData.getInstance().main.chatWindow.chatLower.hideProgressBar(false);
			stop = true;
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void handle(int status) throws IOException {
		int size = -1;
		if (status == OK) {
			double perc = ((double)counter/(double)fileSize)*100.0;
			counter++;
			newPer = (int)perc;
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					oldPer = newPer;
					UsefulData.getInstance().main.chatWindow.chatLower.setProgressBarValue(oldPer);
				}
			});
			size = fileInputStream.read(buffer);
			if (size >= 0) {
				send(OK, buffer, size);
				
			} else {
				outputStream.write(COMPLETE);
				outputStream.flush();
				
				UsefulData.getInstance().main.chatWindow.chatLower.setFileTransferEnable(false, true);
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						UsefulData.getInstance().main.chatWindow.chatLower.setProgressBarStatus("File Sent.");
						UsefulData.getInstance().main.chatWindow.chatLower.hideProgressBar(true);
					}
				});
				close();
				
			}
		} else if (status == ERROR) {
			errorHandler();
		}else if(status==CANCEL){
			UsefulData.getInstance().main.chatWindow.chatLower.setProgressBarStatus("Cancelled.");
			UsefulData.getInstance().main.chatWindow.chatLower.hideProgressBar(false);
			stop = true;
			close();
		}

	}
	public void stopUploader(){
		stop = true;
	}
	public void close() {
	SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				UsefulData.getInstance().main.chatWindow.chatLower.setFileTransferEnable(false, true);
				
			}
		});
		try {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (socket != null) {
				socket.close();
			}
			UsefulData.getInstance().isUploaderBusy = false;

			stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (!stop) {
				int st = inputStream.read();
				handle(st);
			}
			outputStream.write(ERROR);
		} catch (IOException e) {
			errorHandler();

		} finally {
			close();
		}
		close();
	}
	public void errorHandler(){
		stop = true;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				UsefulData.getInstance().main.chatWindow.chatLower.setProgressBarStatus("Upload Failed.");
				UsefulData.getInstance().main.chatWindow.chatLower.hideProgressBar(false);

				
			}
		});
		close();
	}
	public void startUploader(){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				UsefulData.getInstance().main.chatWindow.chatLower.setProgressBarDeterminate();
				UsefulData.getInstance().main.chatWindow.chatLower.setProgressBarStatus("Uploading....");

			}
		});
		try {
			outputStream.write(START);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}