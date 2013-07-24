package com.legaldaily.estension.ecard.repository.listener;

import com.fzw.domain.DomainMessage;
import com.fzw.utils.StringValueUtils;

public class PostOrderChanger implements EcardMessageListener {

	@Override
	public void action(DomainMessage domainMessage) {
		String po[] = (String[]) domainMessage.getEventSource();
		int postid = StringValueUtils.getInt(po[0]);
		int order = StringValueUtils.getInt(po[1]);
		postRepository.changeOrder(postid, order);
	}

}
