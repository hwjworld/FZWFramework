package com.fzw.service.command;

import com.fzw.model.ConnectionMessage;
import com.fzw.view.ResultAdapter;

public class AdminCommand extends ServiceCommand {

	public static final String ADMIN_SHUTDOWN = "shutdown";
	public static final String ADMIN_VERSION = "version";
	
	private static final String EXECUTE_BEAN = "qarecommendService";

	private static final ResultAdapter ADAPTER = null;
	@Override
	public Object execute(ConnectionMessage message) {
		message.setResultAdapter(ADAPTER);
		return null;
	}

}
