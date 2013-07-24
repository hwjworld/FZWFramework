package com.legaldaily.estension.ecard.model.post;

import java.io.Serializable;

import com.fzw.domain.DomainEvents;
import com.fzw.domain.DomainMessage;

public class PostCategoryVO implements Serializable {

	private int postsCount = -1;

	private DomainMessage postsCountAsyncResult;

	private PostCategory postcat;

	public PostCategoryVO(PostCategory postcat) {
		super();
		this.postcat = postcat;
	}

	public int getPostsCount(DomainEvents domainEvents) {
		if (postsCount == -1) {
			if (postsCountAsyncResult == null) {
				postsCountAsyncResult = domainEvents.computePostsCount(postcat.getCatId());
			} else {
				postsCount = (Integer) postsCountAsyncResult.getEventResult();
			}
		}
		return postsCount;
	}

	public void update(int count) {
		if (postsCount != -1) {
			postsCount = postsCount + count;
		}

	}


}
