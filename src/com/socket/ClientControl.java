package com.socket;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.model.dutta.IdentityUser;
import com.model.dutta.UserDetails;
import com.utility.dutta.UsefulData;

public class ClientControl {
	private SocketClient client;
	public int port;
	public String serverAddr;
	public File file;

	public ClientControl(String serverAddr, int port) {
		this.serverAddr = serverAddr;
		this.port = port;
		client = new SocketClient(this);

	}

	public void sineIn(CallBackable c, String username, String password) {
		if (client == null) {
			invoke(c, "Server not found!!!");
		} else {
			IdentityUser.userName = username;
			int id = getFreeId();
			registerObject(c, id);
			client.send(new Message(Constant.LOGIN, username, password,
					"SERVER", id));

		}

	}

	public void sineIn(String username, String password) {
		if (client == null) {

		} else {
			client.send(new Message(Constant.LOGIN, username, password,
					"SERVER", -1));
		}

	}

	public void send(Message message) {
		try {
			client.send(message);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void send(CallBackable object, Message message) {
		int id = getFreeId();
		objects.put(id, object);
		message.callBackId = id;
		try {
			client.send(message);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void send(Message message, File file) {
		try {
			if (UsefulData.getInstance().isUploaderBusy
					|| UsefulData.getInstance().isDownloaderBusy) {
				JOptionPane.showMessageDialog(
						UsefulData.getInstance().main.chatWindow,
						"Uploader Busy! Try again leter");
				return;
			}
			this.file = file;
			long size = file.length();
			if (size < 1024 * 1024 * 1024) {
				send(message);
				UsefulData.getInstance().upload = new Upload(
						UsefulData.getInstance().main.serverAddr,
						UsefulData.getInstance().main.port_for_file, this.file,
						IdentityUser.userName);
				UsefulData.getInstance().upload.start();
			} else {
				JOptionPane.showMessageDialog(
						UsefulData.getInstance().main.chatWindow,
						"File Size Exceed limit 1GB.",
						"File Size Exceed limit", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleMessage(String message) {

		if (!message.equals("error")) {

			Message msg = new Message(message);
			if (msg.type == Constant.MESSAGE) {

				if (msg.recipient
						.equals(IdentityUser.getIdentityUser().userName)) {
					UsefulData.getInstance().main.chatWindow.chatLower
							.handleIncommingText(msg.sender, msg.content);
				}
			} else if (msg.type == Constant.UPDATE_MY_PROFILE) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.STATUS_CHECK) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			}

			else if (msg.type == Constant.SPECIFIC_USER_PERMISSION) {
				try {
					if (msg.callBackId == -1
							&& UsefulData.getInstance().main.chatWindow != null) {
						UsefulData.getInstance().main.chatWindow
								.updateSpecificUserPermission(msg); // When
						// chat
						// transter
						// occured.
					} else
						invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}

			} else if (msg.type == Constant.GET_FRIEND_REQ) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			// ///////////<<<<<<<<<<<<<<<<<<<<<<Group>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			else if (msg.type == Constant.GET_GROUP_NOTIFICATION) {
				if (!msg.content.equals("")) {
					String gName[] = msg.content
							.split(Message.SECOND_LEVEL_SEPERATOR);
					for (String string : gName) {
						UsefulData.getInstance().setNotifiedGroup(string, true);
					}
				}
			} else if (msg.type == Constant.GROUP_HISTORY) {
				String m[] = msg.content.split(Message.SECOND_LEVEL_SEPERATOR);
				if (msg.sender.equals(IdentityUser.getIdentityUser()
						.getSelectedFriendOrGroup())) {

					// UsefulData.getInstance().main.chatWindow.chatLower
					// .clearHistory();
					for (String str : m) {

						String s[] = str.split(Message.THIRD_LEVEL_SEPERATOR);
						if (s.length > 1) {

							UsefulData.getInstance().main.chatWindow.chatLower
									.addHistoryMessageForGroup(s[0], s[1],
											java.sql.Timestamp.valueOf(s[2]),
											msg.sender);
							// UsefulData.getInstance().main.tempHistory
							// .addTempHistoryForGroup(msg.sender, s[0],
							// s[1],
							// java.sql.Timestamp.valueOf(s[2]),
							// false);
						}

					}
				}
			} else if (msg.type == Constant.GROUP_MESSAGE) {
				boolean show = false;
				if (IdentityUser.getIdentityUser().getSelectedFriendOrGroup() != null) {
					if (IdentityUser.getIdentityUser()
							.getSelectedFriendOrGroup().equals(msg.recipient)) {
						UsefulData.getInstance().main.chatWindow.chatLower
								.handaleIncommingTextForGroup(msg.sender,
										msg.content, msg.recipient);
						show = true;
						UsefulData.getInstance().main.clientControl
								.send(new Message(
										Constant.SET_GROUP_NOTIFICATION,
										IdentityUser.userName, IdentityUser
												.getIdentityUser()
												.getSelectedFriendOrGroup(),
										"false", -1));
					}
				}
				if (!show) {
					UsefulData.getInstance().setNotifiedGroup(msg.recipient,
							true);
				}
			} else if (msg.type == Constant.DELETE_GROUP) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.CREATE_GROUP) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.GET_GROUPS_NAMES) {
				if (msg.callBackId == -1) {
					UsefulData.getInstance().groupList = msg.content
							.split(Message.SECOND_LEVEL_SEPERATOR);
				} else {
					try {
						UsefulData.getInstance().groupList = msg.content
								.split(Message.SECOND_LEVEL_SEPERATOR);

						invoke(msg.callBackId, msg);
					} catch (InvocationTargetException | IllegalAccessException
							| NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			} else if (msg.type == Constant.ADD_MEMBER_TO_GROUP) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.REMOVE_MEMBER_FROM_GROUP) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.GET_MEMBERS_FROM_GROUP) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			// /////////////////////////////////////////////////////////////////////
			else if (msg.type == Constant.UPDATE_FRIEND_REQUEST) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			}

			else if (msg.type == Constant.CHANGE_PASS) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.SPECIFIC_USERS) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.EDIT) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.DELETE_USERS) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.BLOCK) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}

			} else if (msg.type == Constant.FILTERED_TEXT) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.CHAT_PERMISSION) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.LOGIN) {
				try {

					if (msg.content.equalsIgnoreCase("TRUE")) {
						IdentityUser.getIdentityUser().setIdentityUser(
								new UserDetails(msg.sender));
						UsefulData.getInstance().reconnectEnable = true;
						UsefulData.getInstance().setConnected(true);
					}
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			// else if (msg.type == Constant.HELLO) {
			// System.out.println("Connectiong established.");
			//
			// }
			else if (msg.type == Constant.USER_LIST) {

				try {

					invoke(msg.callBackId, msg);

				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
				if (UsefulData.getInstance().main.chatWindow != null)
					UsefulData.getInstance().main.chatWindow.contactsPanel
							.updateFriendList(msg.content);

			} else if (msg.type == Constant.SINE_UP) {
				if (msg.content.equals("TRUE")) {
					try {
						invoke(msg.callBackId, msg);
					} catch (InvocationTargetException | IllegalAccessException
							| NoSuchMethodException e) {
						e.printStackTrace();
					}

				}
			} else if (msg.type == Constant.IS_CLIENT_ACTIVE) {
				send(new Message(Constant.IS_CLIENT_ACTIVE,
						IdentityUser.userName, "YES", "SERVER", -1));
			} else if (msg.type == Constant.HISTORY) {
				try {
					invoke(msg.callBackId, msg);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			} else if (msg.type == Constant.UPLOAD_REQUEST) {

				String str[] = msg.content
						.split(Message.SECOND_LEVEL_SEPERATOR);
				long fileSize = Long.parseLong(str[1]);
				if (UsefulData.getInstance().isDownloaderBusy
						|| UsefulData.getInstance().isUploaderBusy) {
					send(new Message(Constant.UPLOAD_RESPONSE,
							IdentityUser.userName,
							"User busy! Try again leter.", msg.sender, -1));
					return;
				}
				if (JOptionPane.showConfirmDialog(
						UsefulData.getInstance().main.chatWindow, ("Accept '"
								+ str[0] + "' from " + msg.sender + " ?"),
						"File Received!", JOptionPane.YES_NO_OPTION) == 0) {
					try {
						UIManager.setLookAndFeel(UIManager
								.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						e1.printStackTrace();
					}
					JFileChooser jf = new JFileChooser();
					jf.setSelectedFile(new File(str[0]));
					int returnVal = jf
							.showSaveDialog(UsefulData.getInstance().main.chatWindow);

					String saveTo = jf.getSelectedFile().getPath();
					if (saveTo != null
							&& returnVal == JFileChooser.APPROVE_OPTION) {
						UsefulData.getInstance().download = new Download(
								UsefulData.getInstance().main.serverAddr,
								UsefulData.getInstance().main.port_for_file,
								new File(saveTo), IdentityUser.userName,
								msg.sender, fileSize);
						UsefulData.getInstance().download.start();
						// progressbar

						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						send(new Message(Constant.UPLOAD_RESPONSE,
								IdentityUser.userName, "true", msg.sender,
								msg.callBackId));

					} else {
						send(new Message(
								Constant.UPLOAD_RESPONSE,
								IdentityUser.getIdentityUser().userName,
								IdentityUser.userName + " : Reject to receive.",
								msg.sender, msg.callBackId));
					}
				} else {
					send(new Message(Constant.UPLOAD_RESPONSE,
							IdentityUser.getIdentityUser().userName,
							IdentityUser.userName + " : Reject to receive.",
							msg.sender, msg.callBackId));
				}
			} else if (msg.type == Constant.UPLOAD_RESPONSE) {
				if (msg.content.equals("true")) {
					UsefulData.getInstance().upload.startUploader();
				} else {
					UsefulData.getInstance().main.chatWindow.chatLower
							.setProgressBarStatus(msg.content);
					UsefulData.getInstance().main.chatWindow.chatLower
							.hideProgressBar(false);
					if (UsefulData.getInstance().upload != null)
						UsefulData.getInstance().upload.stopUploader();
					UsefulData.getInstance().upload = null;
					try {
						invoke(msg.callBackId, msg);
					} catch (InvocationTargetException | IllegalAccessException
							| NoSuchMethodException e) {

						e.printStackTrace();
					}

				}
			} else if (msg.type == Constant.NOTIFICATION_LIST) {
				try {
					if (msg.callBackId != -1)
						invoke(msg.callBackId, msg);
					else
						UsefulData.getInstance().main.chatWindow.contactsPanel
								.setNotification(msg.sender, 1);

				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {

					e.printStackTrace();
				}
			} else if (msg.type == Constant.SERVER_ERROR) {
				invokeAll(msg);

			} else {

				System.err.println("[SERVER > Me] : Unknown message type\n");
			}
		} else {

			UsefulData.getInstance().main.activeWindow
					.showError("Failed to communicate with server, please try again later\n");
		}
	}

	private void invokeAll(Object... parameters) {
		for (int i = 0; i < 1000; i++) {
			if (space[i]) {
				try {
					invoke(i, parameters);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void invoke(CallBackable c, Object... parameters) {
		try {
			Method method = c.getClass().getMethod("done",
					getParameterClasses(parameters));
			method.invoke(c, parameters);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void invoke(int id, Object... parameters)
			throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {
		if (id != -1) {

			Object scope = getObject(id);
			unregisterObject(id);
			if (scope != null) {
				Method method = scope.getClass().getMethod("done",
						getParameterClasses(parameters));
				method.invoke(scope, parameters);
				scope = null;
			}
		}
	}

	private Class<?>[] getParameterClasses(Object... parameters) {
		Class<?>[] classes = new Class[parameters.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = parameters[i].getClass();
		}
		return classes;
	}

	private boolean[] space = new boolean[1000];
	private HashMap<Integer, Object> objects = new HashMap<>();

	private void registerObject(CallBackable arg0, int id) {
		objects.put(id, arg0);
	}

	private Object getObject(int id) {
		return objects.get(id);
	}

	private void unregisterObject(int i) {
		objects.remove(i);
		space[i] = false;
	}

	private int getFreeId() {
		for (int i = 0; i < 1000; i++) {
			if (!space[i]) {
				space[i] = true;
				return i;
			}
		}
		return -1;
	}

	//
	public void closeConnection() {

		UsefulData.getInstance().setConnected(false);
		if (client != null)
			try {
				client.closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public void openConnection() throws IOException {

		client.openConnection();
	}
}
