package com.cql.familyshare.pc.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 日志类
 * 
 * @author cql 下午11:20:38
 */
public class LogUtils {

	// private static
	private static ObservableList<String> messageList = FXCollections
			.observableArrayList();

	public static void logToListView(String message) {
		messageList.add(message);
	}

	public static ObservableList<String> getMessageList() {
		return messageList;
	}

	public static void logError(String tag, String message) {
		LogUtils.logError(tag + ":" + message);
	}

	public static void logError(String message) {
		System.out.println("Error:" + message);
	}

	public static void logWarning(String tag, String message) {
		LogUtils.logWarning(tag + ":" + message);
	}

	public static void logWarning(String message) {
		System.out.println("Waring:" + message);
	}

	public static void logDebug(String tag, String message) {
		LogUtils.logDebug(tag + ":" + message);
	}

	public static void logDebug(String message) {
		System.out.println("Debug:" + message);
	}

}
