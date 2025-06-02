package me.zdany.jchat;

import me.zdany.jchat.ui.Window;

public class JChat
{
	private static JChat instance;
	private final String username, host;
	private final int port;
	private final Window window;
	private final Client client;
	
	public JChat(String username, String host, int port)
	{
		instance = this;
		this.username = username;
		this.host = host;
		this.port = port;
		window = new Window();
		client = new Client();
	}
	
	public void start()
	{
		System.out.println("Setting up \"" + username + "\" user...");
		client.start();
	}
	
	public void stop()
	{
		System.out.println("Stopping...");
		client.stop(true);
	}
	
	public static JChat getInstance()
	{
		return instance;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getHost()
	{
		return host;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public Window getWindow()
	{
		return window;
	}
	
	public Client getClient()
	{
		return client;
	}
}
