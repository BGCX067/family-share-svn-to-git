package com.familyshare.pre;

import javax.swing.JTextArea;

 class MyMessage {
	private static JTextArea messageField;

	public static void setMessageField(JTextArea text_ServerMessage) {
		messageField = text_ServerMessage;
	}

	public static void addMessage(String message) {
		if (messageField != null) {
			messageField.append(message + "\r\n");
		}
	}
	public static void showMessage(String message) {
		if (messageField != null) {
			messageField.setText(message + "\r\n");
		}
	}

}
