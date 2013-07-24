package com.legaldaily.estension.ecard.repository.listener;

import org.apache.commons.lang.ArrayUtils;

import com.fzw.domain.DomainMessage;

public class LawCaseDeleter implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		int[]caseid = (int[]) domainMessage.getEventSource();
		caseid = ArrayUtils.removeElement(caseid, 0);
		if(ArrayUtils.isNotEmpty(caseid)){
			domainMessage.setEventResult(lawRepository.deleteLawCase(caseid));
		}
	}

}
