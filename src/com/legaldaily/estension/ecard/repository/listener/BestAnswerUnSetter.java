package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;
import com.legaldaily.estension.ecard.model.question.Answer;

public class BestAnswerUnSetter implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		Answer answer = (Answer) domainMessage.getEventSource();
		domainMessage.setEventResult(answerRepository.unsetBestAnswer(answer));
	}
}
