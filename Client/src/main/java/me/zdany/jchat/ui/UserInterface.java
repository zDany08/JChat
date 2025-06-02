package me.zdany.jchat.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import me.zdany.jchat.JChat;

public class UserInterface extends JPanel implements ActionListener, KeyListener {

	private JTextArea display;
    private JTextField input;

    public UserInterface() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.renderServerInfos();
		this.renderChatDisplay();
		this.renderInputPanel();
	}
	
	private void renderServerInfos() {
		JLabel hostText = new JLabel();
		hostText.setText("Host: " + JChat.getInstance().getHost() + ":" + JChat.getInstance().getPort());
		hostText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		hostText.setVisible(true);
		this.add(hostText, BorderLayout.NORTH);
	}
	
	private void renderChatDisplay() {
		JPanel margin = new JPanel();
		margin.setLayout(new BorderLayout());
		margin.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		this.display = new JTextArea();
		this.display.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		this.display.setLineWrap(true);
		this.display.setVisible(true);
		this.display.setEditable(false);
		margin.add(new JScrollPane(this.display), BorderLayout.CENTER);
		this.add(margin, BorderLayout.CENTER);
	}

	private void renderInputPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		this.renderMessageInput(panel);
		this.renderSendButton(panel);
		this.add(panel, BorderLayout.SOUTH);
	}
	
	private void renderMessageInput(JPanel panel) {
		JPanel margin = new JPanel();
		margin.setLayout(new BorderLayout());
		margin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		this.input = new JTextField();
		this.input.addKeyListener(this);
		this.input.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		this.input.setDocument(new InputDocument());
		this.input.setVisible(true);
		margin.add(this.input, BorderLayout.CENTER);
		panel.add(margin);
	}
	
	private void renderSendButton(JPanel panel) {
        JButton send = new JButton();
		send.setText("Send");
		send.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		send.setBorder(BorderFactory.createEmptyBorder(8, 32, 8, 32));
		send.addActionListener(this);
		send.setFocusable(false);
		send.setVisible(true);
		panel.add(send);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();
		if(!(obj instanceof JButton)) return;
		this.sendMessage(this.input.getText().trim());
	}
	
	@Override
	public void keyTyped(KeyEvent event) {}

	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		if(key != KeyEvent.VK_ENTER) return;
		this.sendMessage(this.input.getText().trim());
	}

	@Override
	public void keyReleased(KeyEvent event) {}
	
	public void sendMessage(String message) {
		if(message != null && !message.isEmpty()) JChat.getInstance().getClient().sendMessage("(" + JChat.getInstance().getUsername() + ") " + message);
		this.input.setText("");
	}
	
	public JTextArea getDisplay() {
		return this.display;
	}
}
