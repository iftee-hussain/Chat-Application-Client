package com.view.chatWindow;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.annotation.Generated;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class JScrollPanelForContacts extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private JPanel contentPanel;
	private int horizontalGap = 0;
	private int verticalGap = 0;
	private Dimension size = new Dimension(0, 0);
	private Dimension scrollPaneSize = new Dimension();
	
	public JScrollPanelForContacts() {
		
		setBackground(Color.WHITE);
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setLayout(null);
		contentPanel.setSize(size);
		contentPanel.setPreferredSize(size);
		contentPanel.setBorder(null);
		setViewportView(contentPanel);
		setBorder(null);
		
	}
	@Override
	public Component add(Component arg0) {

				arg0.setLocation(size.width, size.height);
				size.height = size.height + getVerticalGap()
						+ arg0.getSize().height;

			contentPanel.setPreferredSize(size);
			contentPanel.setSize(size);
			Component result = contentPanel.add(arg0);
			contentPanel.validate();
			validate();
			JScrollBar vsb = getVerticalScrollBar();
			//JScrollBar hsb = getHorizontalScrollBar();

		//	if (getVerticalScrollBarPostion() == JScrollPanelX.VERTICAL_SCROLLBAR_POSITION_BOTTOM) {
		//		vsb.setValue(vsb.getMaximum());
			//} else if (getVerticalScrollBarPostion() == JScrollPanelX.VERTICAL_SCROLLBAR_POSITION_TOP) {
				vsb.setValue(0);
		//	}

//			if (getHorizontalScrollBarPosition() == JScrollPanelX.HORIZONTAL_SCROLLBAR_POSITION_RIGHT) {
//				hsb.setValue(hsb.getMaximum());
//			} else if (getHorizontalScrollBarPosition() == JScrollPanelX.HORIZONTAL_SCROLLBAR_POSITION_LEFT) {
//				hsb.setValue(0);
//
//			}
			return result;
	}
	public int getHorizontalGap() {
		return horizontalGap;
	}
	public void clear(){
		contentPanel.removeAll();
		size = new Dimension(0, 0);
		scrollPaneSize = new Dimension();
		contentPanel.validate();
	}
	public void refresh(){
		Component[] con = contentPanel.getComponents();
		for (Component component : con) {
			add(component);
		}
	}
	public void setHorizontalGap(int horizontalGap) {
		this.horizontalGap = horizontalGap;
	}

	public int getVerticalGap() {
		return verticalGap;
	}

	public void setVerticalGap(int verticalGap) {
		this.verticalGap = verticalGap;
	}
	public void setVerticalScrollBarPolicy(int arg0, boolean internal) {
		setVerticalScrollBarPolicy(arg0);
	}
	@Override
	public void setHorizontalScrollBarPolicy(int arg0) {
		super.setHorizontalScrollBarPolicy(arg0);
	}

	@Override
	public void setSize(Dimension arg0) {
		super.setSize(arg0);
		scrollPaneSize.setSize(arg0);
	}

	@Override
	public void setSize(int arg0, int arg1) {
		super.setSize(arg0, arg1);
		scrollPaneSize.setSize(new Dimension(arg0, arg1));
	}

	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3) {
		super.setBounds(arg0, arg1, arg2, arg3);
		scrollPaneSize.setSize(new Dimension(arg2, arg3));
	}

}
