package me.zdany.jchat.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class InputDocument extends PlainDocument {

	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if(str == null) return;
		if(getLength() + str.length() <= 1024) {
			super.insertString(offset, str, attr);
			return;
		}
		super.insertString(offset, str.substring(0, 1024 - getLength()), attr);
	}
}
