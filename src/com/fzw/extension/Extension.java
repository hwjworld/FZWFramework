package com.fzw.extension;

import org.apache.ibatis.session.SqlSessionFactory;

import com.fzw.model.ConnectionMessage;
import com.fzw.model.ResultFormatter;
import com.fzw.service.command.ServiceCommand;


public class Extension {
	/**
	 * 插件名称
	 */
	private String name = null;
	private ExtensionConfig config = null;
	
	private ResultFormatter resultFormatter = null;
	
	private SqlSessionFactory dbSessionFactory = null;
	
	private ServiceCommand serviceCommand = new ServiceCommand() {
		@Override
		public Object execute(ConnectionMessage message) {
			return null;
		}
	};
	public Extension() {
		config = new ExtensionConfig();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SqlSessionFactory getDbSessionFactory() {
		return dbSessionFactory;
	}
	public void setDbSessionFactory(SqlSessionFactory dbSessionFactory) {
		this.dbSessionFactory = dbSessionFactory;
	}
	public ServiceCommand getServiceCommand() {		
		return serviceCommand;
	}
//	public void setServiceCommand(ServiceCommand serviceCommand) {
//		this.serviceCommand = serviceCommand;
//	}
//	public void addServiceCommand(){
//		if(serviceCommand == null){
//			serviceCommand = new ServiceCommand() {
//			@Override
//				public Object execute(ConnectionMessage message) {
//					return null;
//				}
//			};
//		}
//		serviceCommand.
//			
//			
//	}
	public ResultFormatter getResultFormatter() {
		return resultFormatter;
	}
	public void setResultFormatter(ResultFormatter resultFormatter) {
		this.resultFormatter = resultFormatter;
	}
	public ExtensionConfig getConfig() {
		return config;
	}
	
}
