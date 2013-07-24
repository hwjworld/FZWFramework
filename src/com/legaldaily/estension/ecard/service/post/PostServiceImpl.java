package com.legaldaily.estension.ecard.service.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.model.condition.PostCondition;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.model.post.PostStatus;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.repository.PostRepository;
import com.legaldaily.estension.ecard.utils.Comparators;

public class PostServiceImpl implements PostServices {

	private PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Post getPost(PostCondition condition) {
		Post post = postRepository.getPost(condition.getPostid()[0]);
		return post;
	}

	@Override
	public boolean delPost(PostCondition condition) {
		return postRepository.removePost(condition.getPostid()[0]);
	}

	@Override
	public int savePost(PostCondition condition) {
		Post post = new Post();
		User user = new User();
		user.setUid(condition.getIntValue("author"));
		post.setAuthor(user);
		post.setContent(condition.getValue("content"));
		post.setTitle(condition.getValue("title"));
		PostCategory postCategory = new PostCategory();
		postCategory.setCatId(condition.getIntValue("categoryid"));
		post.setCategory(postCategory);
		post.setAttachmentUrl(condition.getValue("attachurl"));
		post.setLinkTitle(condition.getValue("linktitle"));
		post.setContentLink(condition.getValue("contentlink"));
		PostStatus status = new PostStatus();
		status.setPostStatus(condition.getValue("status"));
		post.setStatus(status);
		post.setPostid(condition.getIntValue("postid"));
		return postRepository.savePost(post);
	}

	@Override
	public List<Post> listPosts(PostCondition condition) {
		List<Post> posts = postRepository.listPost(condition); 
		if(StringUtils.isNotBlank(condition.getCheck())){
			int check = StringValueUtils.getInt(condition.getCheck());
			for (int i = posts.size()-1; i >= 0; i--) {
				if(posts.get(i).getStatus().getIsAudit() != check){
					posts.remove(i);
				}
			}
		}
		return posts;
	}

	@Override
	public PostCategory getPostCategory(PostCondition condition) {
		return postRepository.getCategory(condition.getCatid());
	}

	@Override
	public List<PostCategory> getAllPostCategory(PostCondition condition) {
		return postRepository.getAllPostCategorys();
	}

	@Override
	public boolean setPostStatus(PostCondition condition) {
		boolean rv = false;
		if(condition.getPostid()[0]>0 && ArrayUtils.contains(new String[]{"wait","publish"}, condition.getStatus())){
			rv = postRepository.setPostStatus(condition.getPostid()[0],condition.getStatus());
		}
		return rv;
	}

	@Override
	public List<PostCategory> getPostCategoryByParentid(PostCondition condition) {
		List<PostCategory> allList = getAllPostCategory(condition);
		List<PostCategory> rvlist = new ArrayList<PostCategory>();
		int id = StringValueUtils.getInt(condition.getValue("id"));
		if(id < 0)
			return rvlist;
		
		for (PostCategory postCategory : allList) {
			if(postCategory.getParentId() == id){
				rvlist.add(postCategory);
			}
		}
		if(CollectionUtils.isNotEmpty(rvlist)){
			Collections.sort(rvlist, Comparators.POSTCATEGORYT_COMPARATOR_ASC_ID);
		}
		return rvlist;
	}

	@Override
	public int listPostsCount(PostCondition condition) {
		condition.setCount(-1);
		return listPosts(condition).size();
	}

	@Override
	public boolean changeOrder(PostCondition condition) {
		int postid = condition.getIntValue("postid");
		int order = condition.getIntValue("order");
		Post post = postRepository.getPost(postid);
		boolean rv = false;
		if(post != null){
			post.getStatus().setOrder(order);
			DOMAIN_EVENTS.changePostOrder(postid,order);
			rv = true;
		}
		return rv;
	}

}
