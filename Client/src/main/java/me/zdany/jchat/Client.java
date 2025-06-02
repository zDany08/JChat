package me.zdany.jchat;

import me.zdany.jchatapi.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class Client {

	private boolean running, newLine;
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	
	public Client() {
		this.newLine = false;
		try {
			this.socket = new Socket(JChat.getInstance().getHost(), JChat.getInstance().getPort());
			this.input = new DataInputStream(this.socket.getInputStream());
			this.output = new DataOutputStream(this.socket.getOutputStream());
			this.connect(JChat.getInstance().getUsername());
		}catch(UnknownHostException e) {
			String message = "Provided host not found";
			JOptionPane.showMessageDialog(null, message + ".", "JChat", JOptionPane.ERROR_MESSAGE);
			Logger.error(message + ": " + e.getMessage());
		}catch(IOException e) {
			Logger.error("Failed to start: " + e.getMessage());
        }
	}
	
	public void start() {
		this.running = true;
		while(this.running) {
			try {
				byte type = this.input.readByte();
				switch(type) {
					case 1:
						this.stop(false);
						break;
					case 2:
						String message = this.input.readUTF();
						JTextArea display = JChat.getInstance().getWindow().getUI().getDisplay();
						if(this.newLine) {
							display.setText(display.getText() + "\n" + message);
							return;
						}
						display.setText(display.getText() + message);
						this.newLine = true;
						break;
				}
			}catch(IOException e) {
				if(this.running) Logger.warn("Failed to read a packet: " + e.getMessage());
				this.running = false;
			}
		}
	}
	
	public void stop(boolean sendRequest) {
		this.running = false;
		if(sendRequest) this.disconnect(JChat.getInstance().getUsername());
		try {
			this.input.close();
			this.output.close();
			this.socket.close();
		}catch(IOException e) {
			Logger.error("Failed to stop: " + e.getMessage());
        }
		System.exit(0);
	}
	
	public void connect(String username) {
		try {
			this.output.writeByte(0);
			this.output.writeUTF(username);
			this.output.flush();
		}catch(IOException e) {
			Logger.error("Failed to connect \"" + username + "\" user: " + e.getMessage());
        }
	}
	
	public void disconnect(String username) {
		try {
			this.output.writeByte(1);
			this.output.writeUTF(username);
			this.output.flush();
		}catch(IOException e) {
			Logger.error("Failed to disconnect \"" + username + "\" user: " + e.getMessage());
        }
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
}
