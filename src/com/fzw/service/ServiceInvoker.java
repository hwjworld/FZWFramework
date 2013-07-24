package com.fzw.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.reflect.MethodUtils;

import com.fzw.connection.FZWService;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;

/**
 * 负责具体分发系统 "可识别消息对象"
 * @author hwj
 *
 */
public class ServiceInvoker {

	private static ServiceInvoker invoker = null;

	public static ServiceInvoker getInvokerInstance() {
		if (invoker == null)
			invoker = new ServiceInvoker();
		return invoker;
	}
	
	private ServiceInvoker() {
	}

	public Object request(ConnectionMessage message) {
		Object result = null;
		if(message.getExtension() == null){
			result = "cannot find extension to process message [" + message.getMessageName() + "]";
		}else {
			ServiceCommand serviceCommand = message.getExtension().getServiceCommand();
			if (serviceCommand.canProcess(message.getMessageName())){
				result = serviceCommand.getExecutor(message.getMessageName())
						.execute(message);
			}else
				result = ("Error Message");
		}
		return result;
	}

	public static Object invokeServiceMethod(FZWService service,
			String methodName, Object param) {
		Object object = null;
		try {
			object = MethodUtils.invokeMethod(service, methodName, param);
		} catch (NoSuchMethodException e) {
			object = "Error method name";
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			object = e.getMessage();
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			object = e.getMessage();
			e.printStackTrace();
		}
		return object;
	}

}
