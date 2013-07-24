package com.fzw.service.command;

import com.fzw.model.ConnectionMessage;

public interface CommandExecuter {

	/**
	 * 
	 * @param message
	 * @return 消息处理结果对象
	 */
	public abstract Object execute(ConnectionMessage message);

}
