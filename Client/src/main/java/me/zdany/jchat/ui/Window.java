package me.zdany.jchat.ui;

import me.zdany.jchat.Client;

import javax.swing.JFrame;

import java.awt.*;

public class Window extends JFrame {

	private final UserInterface ui;
	
	public Window(Client client) {
		this.add(ui = new UserInterface(client));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JChat | Typing as " + client.getUsername());
		this.setMinimumSize(new Dimension(500, 500));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public UserInterface getUI() {
		return this.ui;
	}
}
