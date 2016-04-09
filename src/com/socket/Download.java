package com.socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.model.dutta.IdentityUser;
import com.utility.dutta.UsefulData;

public class Download extends Thread {

	private Socket socket;
	private FileOutputStream fileOutputStream;
	public OutputStream outputStream;
	private InputStream inputStream;
	private static final int START = 1;
	private static final int OK = 2;
	private static final int COMPLETE = 3;
	private static final int ERROR = 4;
	private static final int HEADER = 10;
	private static final int CANCEL = 11;
	private byte[] buffer = new byte[1024];
	private boolean stop = false;
	private File file;
	private ObjectOutputStream objectOutputStream;
	private boolean complete = false;
	String userName;
	String sender;
	int cnt = 0;
	long fileSize;
	int newPer;
	int oldPer;
	private boolean isClose = false;
	private boolean comShow = false;

	public Download(String addr, int port, File filepath, String userName, String sender,
			long fz) {
		super();
		try {
			this.sender = sender;
			comShow = false;
			fileSize = fz;
			this.userName = userName;
			file = filepath;
			socket = new Socket(InetAddress.getByName(addr), port);
			outputStream = socket.getOutputStream();
			outputStream.flush();
			UsefulData.getInstance().isDownloaderBusy = true;
			inputStream = socket.getInputStream();
			fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(
					socket.getOutputStream());
			objectOutputStream.flush();
			// For header
			this.outputStream.write(HEADER);
			objectOutputStream.writeUTF(userName);
			objectOutputStream.flush();
		} catch (Exception ex) {
			close();
		}
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
	private void send(int status) throws IOException {
		outputStream.write(status);
		outputStream.flush();
	}

	private void handle(int status) throws IOException {
		if (status == OK) {
			int count = inputStream.read(buffer);
			fileOutputStream.write(buffer, 0, count);

			double perc = ((double) cnt / (double) fileSize) * 100.0;
			newPer = (int) perc;
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					oldPer = newPer;
					UsefulData.getInstance().main.chatWindow.chatLower
							.setProgressBarValue(oldPer);
					UsefulData.getInstance().main.chatWindow.chatLower
							.setProgressBarValue(oldPer);

					int x = oldPer;
					if (oldPer == 100 && comShow == false) {
						comShow = true;
						UsefulData.getInstance().main.chatWindow.chatLower
								.setProgressBarStatus("File Received.");
						UsefulData.getInstance().main.chatWindow.chatLower
								.hideProgressBar(true);

					}

				}
			});
			cnt++;
			send(OK);
		} else if (status == COMPLETE) {
			complete = true;
			fileOutputStream.flush();
			UsefulData.getInstance().main.chatWindow.chatLower
					.setFileTransferEnable(false, false);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					UsefulData.getInstance().main.chatWindow.chatLower
							.setProgressBarStatus("File Received.");
					UsefulData.getInstance().main.chatWindow.chatLower
							.hideProgressBar(true);
					
				}
			});
			this.close();

		} else if (status == ERROR) {
			errorHandler();
		}else if(status==CANCEL){
			UsefulData.getInstance().main.chatWindow.chatLower.setProgressBarStatus("Cancelled.");
			UsefulData.getInstance().main.chatWindow.chatLower.hideProgressBar(false);
			stop = true;
			close();
		}
		else {
			System.out.println(status + " " + "Unknown status");
		}
	}

	public void errorHandler() {

		if (file != null && file.exists() && file.isFile()) {
			file.delete();
		}
		stopDownloader();
		close();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				UsefulData.getInstance().main.chatWindow.chatLower
						.setProgressBarStatus("Download Failed.");
				UsefulData.getInstance().main.chatWindow.chatLower.hideProgressBar(false);

			}
		});

	}

	public void stopDownloader() {
		stop = true;
	}

	public void close() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				UsefulData.getInstance().main.chatWindow.chatLower
						.setFileTransferEnable(false, false);

			}
		});
		try {
			stop = true;

			if (!complete && file != null && file.exists()) {
				file.delete();
			}
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (socket != null) {
				socket.close();
			}
			UsefulData.getInstance().isDownloaderBusy = false;

			stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			int i = inputStream.read();
			if (i == START) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						UsefulData.getInstance().main.chatWindow.chatLower
								.addFileHistory(Download.this.sender, file,
										Calendar.getInstance().getTime()
												.toString(), true);
						UsefulData.getInstance().main.chatWindow.chatLower
								.setProgressBarDeterminate();
						UsefulData.getInstance().main.chatWindow.chatLower
								.setProgressBarStatus("Downloading....");

					}
				});
				this.send(OK);
				while (!stop) {
					int st = inputStream.read();
					this.handle(st);
				}
				outputStream.write(ERROR);
			} else {
				System.err.println("Unknown command: ");
				close();
			}
		} catch (Exception ioe) {
			errorHandler();
		} finally {
			close();
		}
	}

}