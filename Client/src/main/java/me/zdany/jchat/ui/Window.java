package me.zdany.jchat.ui;

import javax.swing.JFrame;

import me.zdany.jchat.JChat;

import java.awt.*;

public class Window extends JFrame {

	private final UserInterface ui;
	
	public Window() {
		this.add(ui = new UserInterface());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JChat | Typing as " + JChat.getInstance().getUsername());
		this.setMinimumSize(new Dimension(500, 500));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public UserInterface getUI() {
		return this.ui;
	}
}
