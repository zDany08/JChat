package me.zdany.jchat;

import me.zdany.jchatapi.Logger;
import me.zdany.jchatapi.Utils;

public class Main {

	private static Server server;

	public static void main(String[] args) {
		Logger.info("Starting...");
		int port = Utils.DEFAULT_PORT;
		if(args.length >= 1) {
			try {
				port = Integer.parseInt(args[0]);
			}catch(Exception e) {
				Logger.warn("Invalid port, using " + port + " instead: " + e.getMessage());
			}
		}
		server = new Server(port);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));
		server.start();
	}
}
