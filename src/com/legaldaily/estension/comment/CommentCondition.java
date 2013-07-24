package com.legaldaily.estension.comment;

import com.fzw.model.ConnectionMessage;
import com.fzw.model.condition.ServiceCondition;

public class CommentCondition extends ServiceCondition {

	/**
	 * 1.idcard valid
	 * 2.ip valid
	 */
	private int type;
	public CommentCondition(ConnectionMessage message) {
		super(message);
	}

	@Override
	public void buildCondition() {
		
	}

}
