package com.fzw.service.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fzw.connection.FZWService;
import com.fzw.model.condition.ServiceCondition;
import com.fzw.service.ServiceInvoker;

/**
 * 系统增加功能，需要扩展消息，要继承此类 继承后要做的工作：<br/>
 * 1.定义能处理的消息名<br/>
 * 2.定义消息执行类，一个command对应一个executor,executor类型为ServiceCommand<br/>
 * 3.定义消息在执行类里的方法，一个消息对应一个exectuor的方法 <br/>
 * 4.调用registeCommand()方法将消息注册到系统消息中<br/>
 * 5.设置ResultAdapter，放入connectionmessage，设置返回的类型
 * 
 * @author hwj
 * 
 */
public abstract class ServiceCommand implements CommandExecuter{
	

//	private static boolean isEnabled = false;
//	private static final List<String> registedCommands = new ArrayList<String>();
	
//	static{
//		try {
//			XMLConfiguration xmlConfiguration = new XMLConfiguration("ecommands.xml");
//			registedCommands.addAll(xmlConfiguration.getList("command[@name]"));
//		} catch (ConfigurationException e) {
//			LogUtils.error("no ecommands.xml found");
//			System.exit(0);
//		}
//		
//	}
	/**
	 * 消息,处理方法名 messageName,methodName
	 */
	private final Map<String, String> commands = new HashMap<String, String>();
	/**
	 * 消息，处理类 messageName,ServiceCommand
	 */
	private final Map<String, ServiceCommand> executors = new HashMap<String, ServiceCommand>();

	// private static void registeCommand(Class<?> clazz) {
	// try {
	// registedServices.add(Class.forName(clazz.getName()));
	// } catch (ClassNotFoundException e) {
	// System.out.println("registe an error command: " + clazz.getName());
	// e.printStackTrace();
	// }
	// }
//	protected static void registeCommand(ServiceCommand command) {
//		registedCommands.add(command);
//	}

	/**
	 * 注册消息
	 * 
	 * @param commandName
	 *            可执行的消息名
	 * @param excutor
	 *            消息执行类
	 * @param executeMethod
	 *            excutor中执行此消息的方法名
	 */
	public void registeCommand(String commandName,
			ServiceCommand excutor, String executeMethod) {
		if(!StringUtils.isBlank(commandName)
				&& !StringUtils.isBlank(executeMethod)
				&& excutor != null && !commands.containsKey(commandName)){
			executors.put(commandName, excutor);
			commands.put(commandName, executeMethod);	
		}
	}
	/**
	 * 注册消息
	 * 
	 * @param commandName
	 *            可执行的消息名,同时为执行的方法名
	 * @param excutor
	 *            消息执行类
	 */
	public void registeCommand(String commandName,
			ServiceCommand excutor) {
		registeCommand(commandName, excutor, commandName);
	}

	// static {
	// registeCommand(QACommands.class);
	// registeCommand(AdminCommand.class);
	// registeCommand(PostCommands.class);
	// }

	public boolean canProcess(String commandName) {
		return commands.containsKey(commandName);
	}

	public ServiceCommand getExecutor(String commandName) {
		return executors.get(commandName);
	}

	/**
	 * 返回系统可处理的所有消息
	 * 
	 * @return
	 */
	public String[] getAllCommands() {
		String[] comands = new String[commands.size()];
		return commands.keySet().toArray(comands);
	}

	/**
	 * 执行消息
	 * @param command 消息名
	 * @param service 消息处理对象
	 * @param condition 消息处理参数(条件)对象
	 * @return
	 */
	protected Object executeCommand(String command,
			FZWService service, ServiceCondition condition) {
		return ServiceInvoker.invokeServiceMethod(service,
				commands.get(command), condition);
	}
	public Map<String, String> getCommands() {
		return commands;
	}
	public Map<String, ServiceCommand> getExecutors() {
		return executors;
	}
	public void registeCommand(ServiceCommand command2) {
		commands.putAll(command2.getCommands());
		executors.putAll(command2.getExecutors());
	}

//	public static void enableAllCommands(){
//		if(isEnabled){
//			return;
//		}
//		
//		for (String command : registedCommands) {
//			try {
//				Class.forName(command);
//			} catch (ClassNotFoundException e) {
//				LogUtils.error("Error in registe command: [" + command + "]");
//			}
//		}
//		isEnabled = true;
//	}
}
