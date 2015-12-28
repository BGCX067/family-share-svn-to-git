package com.familyshare.pre.utils;


/**
 * 日志类
 * @author cql
 * 下午11:20:38
 */
public class LogUtils {



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
