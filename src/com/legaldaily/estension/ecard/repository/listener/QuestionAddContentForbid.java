package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;

public class QuestionAddContentForbid implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		long qid = (Long) domainMessage.getEventSource();
		boolean rv = questionRepository.forbidQuestionAddcontent(qid);
		domainMessage.setEventResult(rv);
	}

}
