package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;
import com.fzw.utils.StringValueUtils;

public class LawCategoryUpdater implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		String []params = (String[]) domainMessage.getEventSource();
		String name = params[0];
		int parentid = StringValueUtils.getInt(params[1]);
		int catid = StringValueUtils.getInt(params[2]);
		domainMessage.setEventResult(lawRepository.updateLawCategory(name,parentid,catid));
	}

}
