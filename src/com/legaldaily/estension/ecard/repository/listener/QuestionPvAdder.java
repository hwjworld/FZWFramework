package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;

public class QuestionPvAdder implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		long qid = (Long) domainMessage.getEventSource();
		if(qid>0){
			domainMessage.setEventResult(questionRepository.addQuestionPv(qid));
		}
	}

}
