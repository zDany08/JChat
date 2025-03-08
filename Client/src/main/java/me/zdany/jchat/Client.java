package me.zdany.jchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class Client
{
	private boolean running, newLine;
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	
	public Client()
	{
		this.newLine = false;
		try
		{
			socket = new Socket(JChat.getInstance().getHost(), JChat.getInstance().getPort());
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			connect(JChat.getInstance().getUsername());
		}
		catch (UnknownHostException e)
		{
			JOptionPane.showMessageDialog(null, "Provided host not found.", "JChat", JOptionPane.ERROR_MESSAGE);
        }
		catch (IOException e)
		{
			Logger.error("Error starting client.");
        }
	}
	
	public void start()
	{
		running = true;
		while (running)
		{
			try
			{
				byte type = input.readByte();
				switch (type)
				{
					case 1:
						stop(false);
						System.exit(0);
						break;
					case 2:
						String message = input.readUTF();
						JTextArea display = JChat.getInstance().getWindow().getUI().getDisplay();
						if (newLine)
						{
							display.setText(display.getText() + "\n" + message);
						}
						else
						{
							display.setText(display.getText() + message);
							newLine = true;
						}
						break;
				}
			}
			catch (IOException e)
			{
				if(running) Logger.error("Error receiving a packet.");
				running = false;
			}
		}
	}
	
	public void stop(boolean sendRequest)
	{
		running = false;
		if (sendRequest) disconnect(JChat.getInstance().getUsername());
		try
		{
			input.close();
			output.close();
			socket.close();
		}
		catch (IOException e)
		{
			Logger.error("Error stopping client.");
        }
	}
	
	public void connect(String username)
	{
		try
		{
			output.writeByte(0);
			output.writeUTF(username);
			output.flush();
		}
		catch (IOException e)
		{
			Logger.error("Error connecting \"" + username + "\" user.");
        }
	}
	
	public void disconnect(String username)
	{
		try
		{
			output.writeByte(1);
			output.writeUTF(username);
			output.flush();
		}
		catch (IOException e)
		{
			Logger.error("Error disconnecting \"" + username + "\" user.");
        }
	}
	
	public void sendMessage(String message)
	{
		try
		{
			output.writeByte(2);
			output.writeUTF(message);
			output.flush();
		}
		catch(IOException e)
		{
			Logger.error("Error sending message \"" + message + "\".");
        }
	}
}
