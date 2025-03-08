package me.zdany.jchat;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main
{
	private static JChat app;

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			Logger.error("Error setting up UI style.");
		}
		String username = JOptionPane.showInputDialog(null, "Username", "JChat | Username", JOptionPane.PLAIN_MESSAGE);
		if (username == null || username.isEmpty()) username = "User";
		String host = JOptionPane.showInputDialog(null, "IP Address", "JChat | IP Address", JOptionPane.PLAIN_MESSAGE);
		if (host == null || host.isEmpty()) host = "localhost";
		int port;
		try
		{
			port = Integer.parseInt(JOptionPane.showInputDialog(null, "Port", "JChat | Port", JOptionPane.PLAIN_MESSAGE));
		}
		catch (Exception e)
		{
			port = 25565;
		}
		app = new JChat(username, host, port);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> app.stop()));
		app.start();
	}
}
