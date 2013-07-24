package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;

public class PvUpdater implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		String date = (String) domainMessage.getEventSource();
		sideRepository.increasePv(date);
		domainMessage.setEventResult(true);		
	}

}
