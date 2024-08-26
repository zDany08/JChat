package me.zdany.jchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
	
	private boolean connected;
	private Server server;
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	
	public Client(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		try {
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			e.printStackTrace();
        }
	}
	
	@Override
	public void run() {
		connected = true;
		while(connected) {
			try {
				byte type = input.readByte();
				switch(type) {
					case 0:
						String join = input.readUTF() + " joined the chat!";
						server.sendMessage(join);
						System.out.println(join);
						break;
					case 1:
						String quit = input.readUTF() + " left the chat!";
						stopClient();
						server.sendMessage(quit);
						System.out.println(quit);
						break;
					case 2:
						String message = input.readUTF();
						server.sendMessage(message);
						System.out.println(message);
						break;
				}
			}catch(IOException e) {
				if(connected) e.printStackTrace();
				connected = false;
	        }
		}
	}
	
	public void stopClient() {
		connected = false;
		try {
			input.close();
			output.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
        }
		server.getClients().remove(this);
	}
	
	public void sendMessage(String message) {
		try {
			output.writeByte(2);
			output.writeUTF(message);
			output.flush();
		}catch(IOException e) {
			e.printStackTrace();
        }
	}
	
	public DataOutputStream getOutput() {
		return output;
	}
}
