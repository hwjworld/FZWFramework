package com.legaldaily.estension.ecard.service.command;

import com.fzw.Globals;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;
import com.fzw.view.ResultAdapter;
import com.legaldaily.estension.ecard.model.condition.UserCondition;
import com.legaldaily.estension.ecard.service.EcardService;
import com.legaldaily.estension.ecard.view.adapt.php.PHPUserAdapter;

public class UserCommands extends ServiceCommand {

	public static final String GET_SCORE_RANK = "getScoreRank";
	public static final String GET_NONEHEADPIC_USERS = "getNoneHeadpicUsers";
	public static final String GET_USER = "getUser";
	public static final String SEARCH_USERS = "searchUsers";
	public static final String GET_ALL_USER = "getAllUser";	
	public static final String GET_USERGROUP = "getUserGroup";
	public static final String GET_ALL_USERGROUP = "getAllUserGroup";
	public static final EcardService SERVICES = (EcardService) Globals.getBean("userService");
//	public static final UserCommands EXCUTOR = new UserCommands();
	public static final ResultAdapter ADAPTER = new PHPUserAdapter();
	public UserCommands() {
		registeCommand(GET_SCORE_RANK, this);
		registeCommand(GET_USER, this);
		registeCommand(GET_ALL_USER, this);
		registeCommand(SEARCH_USERS, this);
		registeCommand("searchUsersCount", this);
		registeCommand("addUser", this);
		registeCommand("setUserGroup", this);
		registeCommand(GET_USERGROUP, this);
		registeCommand(GET_ALL_USERGROUP, this);
		registeCommand(GET_NONEHEADPIC_USERS, this);
		registeCommand("passHeadpic", this);
		registeCommand("forbidHeadpic", this);
	}
	
	@Override
	public Object execute(ConnectionMessage message) {
		message.setResultAdapter(ADAPTER);
		return executeCommand(message.getMessageName(), SERVICES, new UserCondition(message));
	}

}
