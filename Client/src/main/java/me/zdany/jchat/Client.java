package me.zdany.jchat;

import me.zdany.jchat.ui.Window;
import me.zdany.jchatapi.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

public class Client {

	private final String username, host;
	private final int port;
	private final Window window;
	private boolean running, newLine;
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean sendDisconnectRequest;
	
	public Client(String username, String host, int port) {
		this.username = username;
		this.host = host;
		this.port = port;
		this.window = new Window(this);
		this.newLine = false;
		try {
			this.socket = new Socket(host, port);
			this.input = new DataInputStream(this.socket.getInputStream());
			this.output = new DataOutputStream(this.socket.getOutputStream());
			this.connect(username);
		}catch(IOException e) {
			Logger.error("Failed to start -> " + e.getMessage());
		}
		this.sendDisconnectRequest = true;
	}
	
	public void start() {
		Logger.info("Starting...");
		this.running = true;
		while(this.running) {
			try {
				byte type = this.input.readByte();
				switch(type) {
					case 0:
						this.sendDisconnectRequest = false;
						System.exit(0);
						break;
					case 2:
						String message = this.input.readUTF();
						JTextArea display = this.window.getUI().getDisplay();
						if(this.newLine) {
							display.setText(display.getText() + "\n" + message);
						}else {
							display.setText(display.getText() + message);
						}
						this.newLine = true;
						break;
				}
			}catch(IOException e) {
				if(this.running) Logger.warn("Failed to read a packet -> " + e.getMessage());
				this.running = false;
			}
		}
	}
	
	public void stop() {
		Logger.info("Stopping...");
		this.running = false;
		if(this.sendDisconnectRequest) this.disconnect(username);
		try {
			this.input.close();
			this.output.close();
			this.socket.close();
		}catch(IOException e) {
			Logger.error("Failed to stop -> " + e.getMessage());
        }
	}
	
	public void connect(String username) {
		try {
			this.output.writeByte(0);
			this.output.writeUTF(username);
			this.output.flush();
		}catch(IOException e) {
			Logger.error("Failed to connect \"" + username + "\" user -> " + e.getMessage());
        }
	}
	
	public void disconnect(String username) {
		try {
			this.output.writeByte(1);
			this.output.writeUTF(username);
			this.output.flush();
		}catch(IOException e) {
			Logger.error("Failed to disconnect \"" + username + "\" user -> " + e.getMessage());
        }
	}
	
	public void sendMessage(String message) {
		try {
			this.output.writeByte(2);
			this.output.writeUTF("(" + this.username + ") " + message);
			this.output.flush();
		}catch(IOException | NullPointerException e) {
			Logger.warn("Failed to send message \"" + message + "\" -> " + e.getMessage());
        }
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
}
