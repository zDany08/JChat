package me.zdany.jchat;

import me.zdany.jchatapi.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {

	private boolean running;
	private ServerSocket socket;
	private final Set<ClientHandler> clients;
	
	public Server(int port) {
		try {
			this.socket = new ServerSocket(port);
		}catch(IOException e) {
			Logger.error("Failed to start -> " + e.getMessage());
        }
		this.clients = new HashSet<>();
	}
	
	public void start() {
		Logger.info("Starting...");
		this.running = true;
		while(this.running) {
			try {
				Socket clientSocket = this.socket.accept();
				ClientHandler client = new ClientHandler(this, clientSocket);
				client.start();
				this.clients.add(client);
			}catch(IOException e) {
				Logger.error("Failed to connect a client -> " + e.getMessage());
			}
		}
	}
	
	public void stop() {
		Logger.info("Stopping...");
		this.running = false;
		this.disconnectAll();
		try {
			this.socket.close();
		}catch(IOException e) {
			Logger.error("Failed to stop -> " + e.getMessage());
        }
	}
	
	public void disconnect(ClientHandler client) {
		try {
			client.getOutput().writeByte(1);
			client.getOutput().flush();
		}catch(IOException e) {
			Logger.error("Failed to disconnect a client -> " + e.getMessage());
        }
	}
	
	public void disconnectAll() {
		Set<ClientHandler> disconnection = new HashSet<>(this.clients);
		for(ClientHandler client : disconnection) this.disconnect(client);
		this.clients.clear();
	}

	public Set<ClientHandler> getClients() {
		return this.clients;
	}
}
