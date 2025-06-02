package me.zdany.jchat;

import me.zdany.jchatapi.Logger;
import me.zdany.jchatapi.Utils;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {

	private static JChat app;

	public static void main(String[] args) {
		Logger.info("Starting...");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			Logger.error("Failed to set up UI style: " + e.getMessage());
		}
		String username = JOptionPane.showInputDialog(null, "Username", "JChat | Username", JOptionPane.PLAIN_MESSAGE);
		if(username == null || username.isEmpty()) username = "User";
		String host = JOptionPane.showInputDialog(null, "IP Address", "JChat | IP Address", JOptionPane.PLAIN_MESSAGE);
		if(host == null || host.isEmpty()) host = "localhost";
		int port = Utils.DEFAULT_PORT;
		try {
			port = Integer.parseInt(JOptionPane.showInputDialog(null, "Port", "JChat | Port", JOptionPane.PLAIN_MESSAGE));
		}catch(Exception e) {
			Logger.warn("Invalid port, using " + port + " instead: " + e.getMessage());
		}
		app = new JChat(username, host, port);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> app.stop()));
		app.start();
	}
}
