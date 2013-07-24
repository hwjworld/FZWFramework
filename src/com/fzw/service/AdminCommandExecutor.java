package com.fzw.service;

import com.fzw.connection.FZWService;
import com.legaldaily.estension.ecard.model.condition.Cond;

public interface AdminCommandExecutor extends FZWService{
	

	public String shutdownServer(Cond cond);

}
