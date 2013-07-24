package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;

public class QuestionAddContentPasser implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		long qid = (Long) domainMessage.getEventSource();
		boolean rv = questionRepository.passQuestionsAddcontent(qid);
		domainMessage.setEventResult(rv);
	}

}
