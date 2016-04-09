package com.view.chatWindow;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import com.model.dutta.IdentityUser;
import com.socket.Constant;
import com.socket.Message;
import com.utility.dutta.UsefulData;

public class DropFileSending extends DropTarget {
	private static final long serialVersionUID = 1L;
	private String receiver;
	public DropFileSending(String r) {
		receiver = r;
	}
	public synchronized void drop(DropTargetDropEvent evt) {
		try {
			evt.acceptDrop(DnDConstants.ACTION_COPY);
			List<File> droppedFiles = (List<File>) evt.getTransferable()
					.getTransferData(DataFlavor.javaFileListFlavor);
			if (droppedFiles.size() > 1) {
				JOptionPane.showMessageDialog(null,
						"Multiple files can't be transferred....", "Information", JOptionPane.INFORMATION_MESSAGE);

			} else {
				File file = droppedFiles.get(0);
				if (file != null) {
					long fileSize = file.length() / 1024;
					UsefulData.getInstance().main.clientControl.send(
							new Message(Constant.UPLOAD_REQUEST, IdentityUser.userName,
									file.getName() + Message.SECOND_LEVEL_SEPERATOR
											+ fileSize, receiver, -1), file);

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
