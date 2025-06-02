package me.zdany.jchat;

import me.zdany.jchatapi.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

	private boolean connected;
	private final Server server;
	private final Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	
	public ClientHandler(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		try {
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			Logger.error("Failed to connect a client: " + e.getMessage());
        }
	}
	
	@Override
	public void run() {
		this.connected = true;
		while(this.connected) {
			try {
				byte type = this.input.readByte();
				switch(type) {
					case 0:
						String join = this.input.readUTF() + " joined the chat!";
						this.server.sendMessage(join);
						System.out.println(join);
						break;
					case 1:
						String quit = this.input.readUTF() + " left the chat!";
						this.stopClient();
						this.server.sendMessage(quit);
						System.out.println(quit);
						break;
					case 2:
						String message = this.input.readUTF();
						this.server.sendMessage(message);
						System.out.println(message);
						break;
				}
			}catch(IOException e) {
				if(this.connected) Logger.warn("Failed to read a packet: " + e.getMessage());
				this.connected = false;
	        }
		}
	}
	
	public void stopClient() {
		this.connected = false;
		try {
			this.input.close();
			this.output.close();
			this.socket.close();
		}catch(IOException e) {
			Logger.error("Failed to disconnect a client: " + e.getMessage());
        }
		this.server.getClients().remove(this);
	}
	
	public void sendMessage(String message) {
		try {
			this.output.writeByte(2);
			this.output.writeUTF(message);
			this.output.flush();
		}catch(IOException e) {
			Logger.warn("Failed to send message \"" + message + "\": " + e.getMessage());
        }
	}
	
	public DataOutputStream getOutput() {
		return this.output;
	}
}
