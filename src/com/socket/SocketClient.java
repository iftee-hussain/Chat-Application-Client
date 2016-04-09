package com.socket;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.utility.dutta.UsefulData;

class SocketClient extends Thread {

	public int port;
	public String serverAddr;
	private Socket socket = null;
	public ClientControl control;
	public ObjectInputStream In;
	public ObjectOutputStream Out;
	Thread runningThread = null;
	private boolean hasException = false;

	SocketClient(ClientControl control) {
		this.control = control;
		this.serverAddr = control.serverAddr;
		this.port = control.port;
		runningThread = new Thread(this);
	}

	@Override
	public void run() {

		while (true) {
			try {
				if (hasException) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {

					}
					hasException = false;
				}
				String msg = In.readUTF();
				System.out.println("Incomming : " + msg);
				control.handleMessage(msg);
			} catch (EOFException eof) {
				hasException = true;
				System.err.println("EOF Exception.");
			} catch (SocketException sc) {
				hasException = true;
				UsefulData.getInstance().setConnected(false);
				System.err.println("Socket Exception");
			} catch (IOException e) {
				hasException = true;
				e.printStackTrace();
				control.handleMessage(new Message(Constant.SERVER_ERROR,
						"Client", "Connection Failure,Please try again later",
						"Client", -1).toString());

			}
		}
	}

	void send(Message msg) {
		try {
			Out.writeUTF(msg.toString());
			Out.flush();
			System.out.println("Outgoing : " + msg.toString());
		} catch(SocketException sc){
			System.out.println("Socket Exception when Sending");
		}
		catch (IOException ex) {
			ex.printStackTrace();
			control.handleMessage(new Message(Constant.SERVER_ERROR, "Client",
					"Connection Failure,Please try again later", "Client", -1)
					.toString());
		}
	}

	void closeConnection() throws IOException {
		if (!socket.isClosed()) {

			try {
				if (In != null)
					In.close();
				if (Out != null)
					Out.close();
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void openConnection() throws UnknownHostException, IOException {
		if(socket!=null&&!socket.isClosed()){
			Out.close();
			In.close();
			socket.close();
		}
		socket = new Socket(InetAddress.getByName(serverAddr), port);
		In = new ObjectInputStream(socket.getInputStream());
		Out = new ObjectOutputStream(socket.getOutputStream());
		Out.flush();
		System.out.println("Thread status: Alive: "+runningThread.isAlive()+" Daemon: "
		+runningThread.isDaemon()+"  Interrupted: "+runningThread.isInterrupted());
		if(!runningThread.isAlive()){
			System.out.println("Network Thread Started..");
			runningThread.start();
		}

	}
}
