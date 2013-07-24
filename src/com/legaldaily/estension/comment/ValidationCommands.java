package com.legaldaily.estension.comment;

import com.fzw.connection.FZWService;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;

public class ValidationCommands extends ServiceCommand {

	private static final FZWService SERVICE = new ValidationSericeConvey();
//	private static final ServiceCommand EXECUTOR = new ValidationSericeConvey();
	public ValidationCommands() {
		registeCommand("valid", this);
		registeCommand("getIDCardCount", this);
		registeCommand("getIPCount", this);
		registeCommand("addIP", this);
		registeCommand("addIDCard", this);
		
		registeCommand("getYzm", this);
	}
	@Override
	public Object execute(ConnectionMessage message) {
		return executeCommand(message.getMessageName(), SERVICE, new CommentCondition(message));
	}

}
