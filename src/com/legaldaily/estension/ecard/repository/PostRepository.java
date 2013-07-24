package com.legaldaily.estension.ecard.repository;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.PostCondition;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;

public interface PostRepository {

	public int getPostCountFromCatid(int catid);
	
	public Post getPost(int postid);
	
//	public boolean addPost(Post post);
	
	public boolean removePost(Post post);
	
	public boolean removePost(int postid);
	
	public int savePost(Post post);
	
	public PostCategory getCategory(int catid);
	
	public List<Post> listPost(PostCondition condition);
	
	public List<Post> getAllPosts();
	public List<PostCategory> getAllPostCategorys();

	public boolean setPostStatus(int postid, String status);


	public boolean changeOrder(int postid, int order);
}
