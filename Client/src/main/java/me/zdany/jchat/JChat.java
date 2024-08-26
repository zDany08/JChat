package me.zdany.jchat;

import me.zdany.jchat.ui.Window;

public class JChat {

	private static JChat instance;
	private final String USERNAME, HOST;
	private final int PORT;
	private final Window WINDOW;
	private final Client CLIENT;
	
	public JChat(String username, String host, int port) {
		instance = this;
		this.USERNAME = username;
		this.HOST = host;
		this.PORT = port;
		WINDOW = new Window();
		CLIENT = new Client();
	}
	
	public void start() {
		System.out.println("Setting up \"" + USERNAME + "\" user...");
		CLIENT.start();
	}
	
	public void stop() {
		System.out.println("Stopping...");
		CLIENT.stop(true);
	}
	
	public static JChat getInstance() {
		return instance;
	}
	
	public String getUsername() {
		return USERNAME;
	}
	
	public String getHost() {
		return HOST;
	}
	
	public int getPort() {
		return PORT;
	}
	
	public Window getWindow() {
		return WINDOW;
	}
	
	public Client getClient() {
		return CLIENT;
	}
}
