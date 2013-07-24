package com.fzw;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.fzw.config.FZWFrameworkConfig;
import com.fzw.extension.ExtensionManager;
import com.fzw.utils.LogUtils;

public class SystemInit {
	public static void init() throws ConfigurationException {
		LogUtils.info("系统正在初始化，请稍候...");
		loadFrameworkInfo();
		Globals.printVersion();
		ExtensionManager.initExtensions();
	}

	private static void loadFrameworkInfo() throws ConfigurationException {
		XMLConfiguration xmlConfiguration = new XMLConfiguration("fzwFramework.xml");
		FZWFrameworkConfig.setCommandprocess(xmlConfiguration.getInt("commandprocess", 1));
		FZWFrameworkConfig.setExtension_cache_limit(xmlConfiguration.getInt("extension.dbconn-limit", 50));
		FZWFrameworkConfig.setExtension_dbconn_limit(xmlConfiguration.getInt("extension.cache-limit", 20));
		FZWFrameworkConfig.setMessageType(xmlConfiguration.getString("messagetype", "json"));
		FZWFrameworkConfig.setPort(xmlConfiguration.getInt("port", 8410));
		FZWFrameworkConfig.setVersion(xmlConfiguration.getString("version"));
	}

}
