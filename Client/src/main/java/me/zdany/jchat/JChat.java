package me.zdany.jchat;

import me.zdany.jchat.ui.Window;
import me.zdany.jchatapi.Logger;

public class JChat {

	private static JChat instance;
	private final String username, host;
	private final int port;
	private final Window window;
	private final Client client;
	
	public JChat(String username, String host, int port) {
		instance = this;
		this.username = username;
		this.host = host;
		this.port = port;
		this.window = new Window();
		this.client = new Client();
	}
	
	public void start() {
		Logger.info("Setting up \"" + this.username + "\" user...");
		this.client.start();
	}
	
	public void stop() {
		Logger.info("Stopping...");
		this.client.stop(true);
	}
	
	public static JChat getInstance() {
		return instance;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public Window getWindow() {
		return this.window;
	}
	
	public Client getClient() {
		return this.client;
	}
}
