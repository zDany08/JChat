package me.zdany.jchat.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.StrokeBorder;

import me.zdany.jchat.JChat;

public class UI extends JPanel implements ActionListener, KeyListener
{
	private JTextArea display;
    private JTextField input;

    public UI()
	{
		setPreferredSize(new Dimension(500, 500));
		setLayout(null);
		drawServerInfos();
		drawChatDisplay();
		drawMessageInput();
		drawSendButton();
	}
	
	private void drawServerInfos()
	{
		JLabel hostText = new JLabel();
		hostText.setText("Host: " + JChat.getInstance().getHost() + ":" + JChat.getInstance().getPort());
		hostText.setLocation(20, 20);
		hostText.setSize(460, 20);
		hostText.setFont(new Font("Arial", Font.PLAIN, 20));
		hostText.setForeground(new Color(0, 0, 0));
		hostText.setVisible(true);
		add(hostText);
	}
	
	private void drawChatDisplay()
	{
		display = new JTextArea();
		display.setBorder(new EmptyBorder(5, 5, 5, 5));
		display.setLocation(0, 0);
		display.setSize(460, 370);
		display.setFont(new Font("Arial", Font.PLAIN, 16));
		display.setForeground(new Color(0, 0, 0));
		display.setSelectedTextColor(new Color(255, 255, 255));
		display.setSelectionColor(new Color(0, 0, 0));
		display.setLineWrap(true);
		display.setVisible(true);
		display.setEditable(false);
        JScrollPane displayScroll = new JScrollPane(display);
		displayScroll.setBorder(new StrokeBorder(new BasicStroke(1)));
		displayScroll.setLocation(20, 60);
		displayScroll.setSize(460, 370);
		add(displayScroll);
	}
	
	private void drawMessageInput()
	{
		input = new JTextField();
		input.setBorder(new StrokeBorder(new BasicStroke(1)));
		input.setLocation(20, 450);
		input.setSize(340, 30);
		input.addKeyListener(this);
		input.setFont(new Font("Arial", Font.PLAIN, 16));
		input.setForeground(new Color(0, 0, 0));
		input.setDocument(new InputDocument());
		input.setSelectedTextColor(new Color(255, 255, 255));
		input.setSelectionColor(new Color(0, 0, 0));
		input.setVisible(true);
		add(input);
	}
	
	private void drawSendButton()
	{
        JButton send = new JButton();
		send.setText("Send");
		send.setLocation(380, 450);
		send.setSize(100, 30);
		send.addActionListener(this);
		send.setFocusable(false);
		send.setVisible(true);
		add(send);
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		Object obj = event.getSource();
		if (!(obj instanceof JButton)) return;
		sendMessage(input.getText().trim());
	}
	
	@Override
	public void keyTyped(KeyEvent event)
	{
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		int key = event.getKeyCode();
		if (key != KeyEvent.VK_ENTER) return;
		sendMessage(input.getText().trim());
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
	}
	
	public void sendMessage(String message)
	{
		if (message != null && !message.isEmpty()) JChat.getInstance().getClient().sendMessage("(" + JChat.getInstance().getUsername() + ") " + message);
		input.setText("");
	}
	
	public JTextArea getDisplay()
	{
		return display;
	}
}
