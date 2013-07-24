package com.fzw.config;


public class FZWFrameworkConfig {
	private static String version = "default version";
	private static int port = 0;
	private static String messageType = "json";
	private static int extension_dbconn_limit = 50;
	private static int extension_cache_limit = 20;
	private static int commandprocess = 1;
	public static String getVersion() {
		return version;
	}
	public static void setVersion(String version) {
		FZWFrameworkConfig.version = version;
	}
	public static int getPort() {
		return port;
	}
	public static void setPort(int port) {
		FZWFrameworkConfig.port = port;
	}
	public static String getMessageType() {
		return messageType;
	}
	public static void setMessageType(String messageType) {
		FZWFrameworkConfig.messageType = messageType;
	}
	public static int getExtension_dbconn_limit() {
		return extension_dbconn_limit;
	}
	public static void setExtension_dbconn_limit(int extension_dbconn_limit) {
		FZWFrameworkConfig.extension_dbconn_limit = extension_dbconn_limit;
	}
	public static int getExtension_cache_limit() {
		return extension_cache_limit;
	}
	public static void setExtension_cache_limit(int extension_cache_limit) {
		FZWFrameworkConfig.extension_cache_limit = extension_cache_limit;
	}
	public static int getCommandprocess() {
		return commandprocess;
	}
	public static void setCommandprocess(int commandprocess) {
		FZWFrameworkConfig.commandprocess = commandprocess;
	}
	
}
