package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;
import com.fzw.domain.MessageListener;

public class PostCounter implements EcardMessageListener{
//	private final static Logger logger = Logger.getLogger(PostCounter.class);

	public void action(DomainMessage eventMessage) {
		int catid = (Integer) eventMessage.getEventSource();
		int count = postRepository.getPostCountFromCatid(catid);
		eventMessage.setEventResult(count);
	}

}
