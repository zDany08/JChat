package me.zdany.jchat.ui;

import javax.swing.JFrame;

import me.zdany.jchat.JChat;

public class Window extends JFrame
{
	private final UI UI;
	
	public Window()
	{
		add(UI = new UI());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("JChat | Typing as " + JChat.getInstance().getUsername());
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public UI getUI()
	{
		return UI;
	}
}
