package com.legaldaily.estension.ecard.service.command;

import com.fzw.Globals;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;
import com.fzw.view.ResultAdapter;
import com.legaldaily.estension.ecard.model.condition.AreaCondition;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;
import com.legaldaily.estension.ecard.service.EcardService;
import com.legaldaily.estension.ecard.view.adapt.php.PHPAreaAdapter;

public class AreaCommands extends ServiceCommand {

	public static final String CITY_GET = "getCity";
	public static final String PROVINCE_GET = "getProvince";
	public static final String CITY_LIST = "listCity";
	public static final String PROVINCE_LIST = "listProvince";
	private static final String EXECUTE_BEAN_NAME = "areaService";

	private static final EcardService SERVICE = (EcardService) Globals
			.getBean(EXECUTE_BEAN_NAME);
//	private static final ServiceCommand EXECUTOR = new AreaCommands();

	private static final ResultAdapter ADAPTER = new PHPAreaAdapter();
	public AreaCommands(){
		registeCommand(CITY_GET, this);
		registeCommand(PROVINCE_GET, this);
		registeCommand(CITY_LIST, this);
		registeCommand(PROVINCE_LIST, this);
	}
	
	@Override
	public Object execute(ConnectionMessage message) {
		message.setResultAdapter(ADAPTER);
		String messageName = message.getMessageName();
		EcardServiceCondition cond = new AreaCondition(message);
		Object result = executeCommand(messageName, SERVICE, cond);
		return result;
	}

}
