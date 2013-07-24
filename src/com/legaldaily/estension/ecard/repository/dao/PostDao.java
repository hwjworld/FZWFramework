package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.PostCondition;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.model.post.PostStatus;

public interface PostDao {

	public PostStatus getPostStatus(int postid);
	public Post getPost(int postid);
	public int insertPost(Post post);
	public int updatePost(Post post);
	public int deletePost(Post post);
	public PostCategory getCategory(int catid);
	public List<Post> listPost(PostCondition condition);

	public List<Post> getAllPosts();
	public List<PostCategory> getAllPostCategories();
	public int setPostStatus(int postid, String status);
	public boolean changeOrder(int postid, int order);
}
