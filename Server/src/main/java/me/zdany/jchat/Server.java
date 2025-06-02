package me.zdany.jchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server
{
	private boolean running;
	private ServerSocket socket;
	private final Set<ClientHandler> clients;
	
	public Server(int port)
	{
		try
		{
			socket = new ServerSocket(port);
		}
		catch (IOException e)
		{
			Logger.error("Error starting server.");
        }
		clients = new HashSet<>();
	}
	
	public void start()
	{
		running = true;
		while (running)
		{
			try
			{
				Socket clientSocket = socket.accept();
				ClientHandler client = new ClientHandler(this, clientSocket);
				client.start();
				clients.add(client);
			}
			catch (IOException e)
			{
				Logger.info("Error connecting a client.");
	        }
		}
	}
	
	public void stop()
	{
		running = false;
		disconnectAll();
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			Logger.info("Error stopping server.");
        }
	}
	
	public void sendMessage(String message)
	{
		clients.forEach(client -> client.sendMessage(message));
	}
	
	public void disconnect(ClientHandler client)
	{
		try
		{
			client.getOutput().writeByte(1);
			client.getOutput().flush();
		}
		catch (IOException e)
		{
			Logger.info("Error disconnecting a client.");
        }
	}
	
	public void disconnectAll()
	{
		Set<ClientHandler> disconnection = new HashSet<>(clients);
		for(ClientHandler client : disconnection) disconnect(client);
		clients.clear();
	}

	public Set<ClientHandler> getClients()
	{
		return clients;
	}
}
