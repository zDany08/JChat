package me.zdany.jchat;

public class Main
{
	private static Server server;

	public static void main(String[] args)
	{
		if (args.length < 1)
		{
			System.out.println("Please, provide a port.");
			System.exit(1);
		}
		int port = -1;
		try
		{
			port = Integer.parseInt(args[0]);
		}
		catch (Exception e)
		{
			Logger.error("Invalid port.");
			System.exit(1);
		}
		server = new Server(port);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));
		server.start();
	}
}
