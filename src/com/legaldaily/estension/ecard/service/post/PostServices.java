package com.legaldaily.estension.ecard.service.post;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.PostCondition;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.service.EcardService;

public interface PostServices extends EcardService{

	public Post getPost(PostCondition condition);
	public boolean delPost(PostCondition condition);
	public int savePost(PostCondition condition);
	public List<Post> listPosts(PostCondition condition);
	public int listPostsCount(PostCondition condition);
	public PostCategory getPostCategory(PostCondition condition);
	public List<PostCategory> getPostCategoryByParentid(PostCondition condition);
	public List<PostCategory> getAllPostCategory(PostCondition condition);
	public boolean setPostStatus(PostCondition condition);
	public boolean changeOrder(PostCondition condition);
}
