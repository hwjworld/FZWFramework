package com.fzw.extension;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import com.fzw.model.DefaultJsonResultFormatter;
import com.fzw.model.ResultFormatter;
import com.fzw.repository.dao.DaoConfig;
import com.fzw.service.command.ServiceCommand;
import com.fzw.utils.LogUtils;
import com.fzw.utils.StringValueUtils;

public class ExtensionManager {
	private static boolean initFlag = false;

	private static final Map<String, Extension> extensions = new HashMap<String, Extension>();

	public static void addExtension(String name, Extension extension) {
		if (!containExtension(name)) {
			extensions.put(name, extension);
		}
	}

	/**
	 * 根据指定的插件名称处理
	 * 
	 * @param name
	 * @return
	 */
	public static Extension getExtension(String name) {
		return extensions.get(name);
	}

	public static boolean containExtension(String name) {
		return extensions.containsKey(name);
	}

	public static Collection<Extension> getAllExtensions() {
		return extensions.values();
	}

	/**
	 * 若无指定插件名称,可通过消息名自动判断分配处理插件,不一定准确.
	 * 
	 * @param command
	 * @return
	 */
	public static Extension getExtensionByCommand(String command) {
		Extension rv = null;
		for (Extension extension : getAllExtensions()) {
			if (extension.getServiceCommand().canProcess(command)) {
				rv = extension;
				break;
			}
		}
		return rv;
	}

	public static void initExtensions() {
		if (isInited())
			return;
		LogUtils.info("正在初始化插件，请稍候...");
		loadExtensionsInfo();
		initAllExtensions();
	}

	private static void loadExtensionsInfo() {
		try {
			XMLConfiguration xmlConfiguration = new XMLConfiguration("extensions.xml");
			List<?> extensionList = xmlConfiguration.configurationsAt("extension");
			for (Object object : extensionList) {
				HierarchicalConfiguration extensionConfiguration = (HierarchicalConfiguration) object;
				String name = StringUtils.trimToEmpty(extensionConfiguration.getString("name"));
				if (ExtensionManager.containExtension(name)) {
					LogUtils.warn("extension name [ " + name + " ] exists, skip");
					continue;
				}
				int enable = StringValueUtils.getInt(StringUtils.trimToEmpty(extensionConfiguration.getString("enable")));
				if(enable == 0){
					LogUtils.warn("extension ["+name+"] disabled, skip");
					continue;
				}
				if (StringUtils.isNotBlank(name)) {
					Extension extension = new Extension();
					extension.setName(name);
					extension.getConfig().setInitClass(StringUtils.trimToEmpty(extensionConfiguration.getString("init")));
					extension.getConfig().setDbConfigFile(StringUtils.trimToEmpty(extensionConfiguration.getString("dbconfiguration")));
					extension.getConfig().addAllCommandClasses(extensionConfiguration.getList("commands.command[@name]"));
					extension.getConfig().setResultFormatClass(StringUtils.trimToEmpty(extensionConfiguration.getString("resultformatter")));
					ExtensionManager.addExtension(name, extension);
				} else {
					LogUtils.warn("one extension doesn't has it own name, skip");
				}
			}
		} catch (ConfigurationException e) {
			LogUtils.error("no ecommands.xml found");
			System.exit(0);
		}
	}

	private static void initAllExtensions() {
		for (Extension extension : ExtensionManager.getAllExtensions()) {
			initExtensionDB(extension);
			initExtension(extension);
			initResultFormatter(extension);
			enableExtensionCommands(extension);
		}
		initFlag = true;
	}

	private static void initResultFormatter(Extension extension) {
		String formatClass = extension.getConfig().getResultFormatClass();
		ResultFormatter formatter;
		if (StringUtils.isNotBlank(formatClass)) {
			try {
				formatter = (ResultFormatter) Class.forName(formatClass).newInstance();
			} catch (Exception e) {
				LogUtils.warn("error in resultformatter: ["+formatClass+"], use default result formatter instead");
				formatter = DefaultJsonResultFormatter.getInstance();
			}
		}else {
			formatter = DefaultJsonResultFormatter.getInstance();			
		}
		extension.setResultFormatter(formatter);
	}

	private static boolean isInited() {
		if (initFlag) {
			return true;
		} else {
			return false;
		}
	}

	private static void initExtensionDB(Extension extension) {
		if (StringUtils.isNotBlank(extension.getConfig().getDbConfigFile())) {
			extension.setDbSessionFactory(DaoConfig.newDaoManager(extension.getConfig().getDbConfigFile()));
		}
	}

	private static void enableExtensionCommands(Extension extension) {
		for (String command : extension.getConfig().getCommandClasses()) {
			try {
				if (StringUtils.isNotBlank(command)) {
					ServiceCommand command2 = (ServiceCommand) Class.forName(command).newInstance();
					extension.getServiceCommand().registeCommand(command2);
					LogUtils.info("load command [" + command + "] successed");
				}
			} catch (ClassNotFoundException e) {
				LogUtils.error("load command [" + command + "] error");
			} catch (InstantiationException e) {
				LogUtils.error("error command [" + command + "]");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				LogUtils.error("command [" + command + "] is not yours");
				e.printStackTrace();
			}
		}
	}

	private static void initExtension(Extension extension) {
		try {
			if (StringUtils.isNotBlank(extension.getConfig().getInitClass())) {
				Class.forName(extension.getConfig().getInitClass());
				LogUtils.info("initialize extension [" + extension.getName() + "] successed");
			}
		} catch (ClassNotFoundException e) {
			LogUtils.error("initializing [" + extension.getName() + "] error");
		}
	}
}
