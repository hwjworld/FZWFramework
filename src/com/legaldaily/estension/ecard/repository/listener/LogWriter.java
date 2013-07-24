package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;
import com.fzw.utils.StringValueUtils;

public class LogWriter implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		String[] param = (String[]) domainMessage.getEventSource();
		int userid = StringValueUtils.getInt(param[0]);
		String userip = param[1];
		int objid = StringValueUtils.getInt(param[2]);
		String action = param[3];
		String category = param[4];
		sideRepository.writeLog(userid,userip,objid,action,category);
	}

}
