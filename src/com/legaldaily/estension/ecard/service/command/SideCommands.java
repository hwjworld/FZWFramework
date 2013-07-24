package com.legaldaily.estension.ecard.service.command;

import com.fzw.Globals;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;
import com.legaldaily.estension.ecard.model.condition.SideCondition;
import com.legaldaily.estension.ecard.service.EcardService;

public class SideCommands extends ServiceCommand {

	public SideCommands(){
		registeCommand("getDefaultSetting", this);
		registeCommand("getAllDefaultSetting", this);
		registeCommand("writeLog", this);
		registeCommand("pv", this);
	}
	public static final EcardService SERVICE = (EcardService) Globals.getBean("sideService");
	
	@Override
	public Object execute(ConnectionMessage message) {
		return executeCommand(message.getMessageName(), SERVICE, new SideCondition(message));
	}

}
