package com.fzw.service;

import com.legaldaily.estension.ecard.model.condition.Cond;

public class AdminCommandExecutorImpl implements AdminCommandExecutor {

	@Override
	public String shutdownServer(Cond cond) {
		if("shutdown".equals(cond.getMessage().getMessageName())){
			if(cond.getMessage().getParamMap().containsKey("auth=fzw")){
			}else{
				return "Untrust user";				
			}
		}
		return "Error shutdown";
	}

}
