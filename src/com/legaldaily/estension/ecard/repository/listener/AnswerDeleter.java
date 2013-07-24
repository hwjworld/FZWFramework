package com.legaldaily.estension.ecard.repository.listener;

import org.apache.commons.lang.ArrayUtils;

import com.fzw.domain.DomainMessage;

public class AnswerDeleter implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		long[] id = (long[]) domainMessage.getEventSource();
		id = ArrayUtils.removeElement(id, 0);
		if(ArrayUtils.isNotEmpty(id)){
			domainMessage.setEventResult(answerRepository.forbidAnswer(id));
		}
	}

}
