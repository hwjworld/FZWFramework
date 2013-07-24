package com.fzw.extension;

import java.util.ArrayList;
import java.util.List;

public class ExtensionConfig {

	/**
	 * 插件初始化类
	 */
	private String initClass = null;
	/**
	 * 插件数据库配置　设置文件,mybatis的configuration.xml
	 */
	private String dbConfigFile = null;
	private List<String> commandClasses = new ArrayList<String>();
	private String resultFormatClass = null;

	public void addCommandClass(String command) {
		commandClasses.add(command);
	}
	public void addAllCommandClasses(List<String> list){
		commandClasses.addAll(list);
	}
	public List<String> getCommandClasses() {
		return commandClasses;
	}
	public String getInitClass() {
		return initClass;
	}
	public void setInitClass(String initClass) {
		this.initClass = initClass;
	}
	public String getDbConfigFile() {
		return dbConfigFile;
	}
	public void setDbConfigFile(String dbConfigFile) {
		this.dbConfigFile = dbConfigFile;
	}
	public String getResultFormatClass() {
		return resultFormatClass;
	}
	public void setResultFormatClass(String resultFormatClass) {
		this.resultFormatClass = resultFormatClass;
	}
	public void setCommandClasses(List<String> commandClasses) {
		this.commandClasses = commandClasses;
	}
}
