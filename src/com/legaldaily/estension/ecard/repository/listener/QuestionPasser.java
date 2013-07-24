package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;
import com.legaldaily.estension.ecard.cache.LatestSolvedQuestionIndex;

public class QuestionPasser implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		long qid = (Long) domainMessage.getEventSource();
		boolean rv = questionRepository.passQuestion(qid);
		LatestSolvedQuestionIndex.updateLatestSolvedQuestion(questionRepository.getQuestion(qid));
		domainMessage.setEventResult(rv);
	}

}
