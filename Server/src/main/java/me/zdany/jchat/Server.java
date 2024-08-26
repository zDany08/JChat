package me.zdany.jchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
	
	private boolean running;
	private ServerSocket socket;
	private Set<Client> clients;
	
	public Server(int port) {
		try {
			socket = new ServerSocket(port);
		}catch(IOException e) {
			e.printStackTrace();
        }
		clients = new HashSet<>();
	}
	
	public void start() {
		running = true;
		while(running) {
			try {
				Socket clientSocket = socket.accept();
				Client client = new Client(this, clientSocket);
				client.start();
				clients.add(client);
			}catch(IOException e) {
				e.printStackTrace();
	        }
		}
	}
	
	public void stop() {
		running = false;
		disconnectAll();
		try {
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
        }
	}
	
	public void sendMessage(String message) {
		clients.forEach(client -> client.sendMessage(message));
	}
	
	public void disconnect(Client client) {
		try {
			client.getOutput().writeByte(1);
			client.getOutput().flush();
		}catch(IOException e) {
			e.printStackTrace();
        }
	}
	
	public void disconnectAll() {
		Set<Client> disconnection = new HashSet<>(clients);
		for(Client client : disconnection) disconnect(client);
		clients.clear();
	}

	public Set<Client> getClients() {
		return clients;
	}
}
