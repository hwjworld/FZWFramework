package com.fzw.service;

import com.fzw.model.ConnectionMessage;
import com.fzw.model.ResultFormatter;
import com.fzw.view.ResultAdapter;

/**
 * 代理ServiceInvoker，接收可识别消息对象与转换返回类型
 * 取代aop转换结果对象，因为使用aop不允许改变返回类型,在应用getPost等不可转变,所以增加此代理层进行转换
 * 
 * @author hwj
 * 
 */
public class ServiceInvokerProxy {

	private static ServiceInvoker invoker = ServiceInvoker.getInvokerInstance();

	public static void request(ConnectionMessage message) {
		Object result = invoker.request(message);
		// 处理返回结果对象，
		ResultAdapter adapter = message.getResultAdapter();
		if (adapter != null) {
			result = adapter.adapt(message.getMessageName(), message.getParamMap(), result);
		}
		// 转换结果,默认json
		ResultFormatter formatter = message.getExtension().getResultFormatter();
		message.setResultValue(formatter.format(result));
		// message.setResultValue(FZWService.gson.toJson(result));
	}
}
