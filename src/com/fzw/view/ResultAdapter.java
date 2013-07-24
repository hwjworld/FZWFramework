package com.fzw.view;

import java.util.Map;

public interface ResultAdapter {

	/**
	 * @param methodName 消息名
	 * @param methodParam 消息参数,为了适应有些情况下,仍需要根据消息参数以确定返回在值的情况,可以实现返回值不只是和消息一一对应,从而更加灵活.
	 * @param returnValue 方法返回值
	 */
	public Object adapt(String methodName,Map<String, String> methodParam, Object returnValue);
}
