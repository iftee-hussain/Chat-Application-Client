package com.view.chatWindow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HomePanel extends JEditorPane {
	
	private Dimension defaultSize = new Dimension(600, 520);
	private Dimension maxSize = new Dimension(Toolkit.getDefaultToolkit()
			.getScreenSize().width - 330, 700);
	
	public HomePanel() {
		this.setEditable(false);
		JScrollPane pane = new JScrollPane(this);
		
		try {
		       this.setPage("http://103.230.62.158/Dutta%20Chat.html");
		      
		     }
		     catch (IOException e) {
		       this.setContentType("text/html");
		       this.setText("<html>Could not load http://www.oreilly.com </html>");
		     }
	}
	
	public void setMax()
	{
		setSize(maxSize);
		setLocation(304,51);
		
	}
	
	public void setDefault()
	{
		setSize(defaultSize);
		setLocation(304,51);
	}

}
