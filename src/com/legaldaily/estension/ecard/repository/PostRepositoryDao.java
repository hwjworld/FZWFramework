package com.legaldaily.estension.ecard.repository;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.legaldaily.estension.ecard.cache.PostIndex;
import com.legaldaily.estension.ecard.model.condition.PostCondition;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.model.post.PostStatus;
import com.legaldaily.estension.ecard.repository.dao.PostDao;
import com.legaldaily.estension.ecard.repository.dao.PostDaoSql;

public class PostRepositoryDao extends RepositoryDao implements PostRepository {

	private static final PostDao postDao = new PostDaoSql();

	@Override
	public Post getPost(int postid) {
		if(!containKeyInCache(postCache, postid)){
			Post post = postDao.getPost(postid);
			if (post == null) {
				return null;
			}
			setPost(post);
//			updateStatus(post);
			storePost(post);
		}
		return (Post) getValueFromCache(postCache, postid);
	}


	private void updateStatus(Post post) {
		// TODO Auto-generated method stub
		
	}

	private PostStatus getPostStatus(int postid) {
		return postDao.getPostStatus(postid);
	}

	@Override
	public boolean removePost(Post post) {
		int rv = postDao.deletePost(post);
		if(rv > 0){
			removeFromCache(postCache, post.getPostid());
			PostIndex.removePostFromIndex(post);
		}
		return rv>0?true:false;
	}

	@Override
	public boolean removePost(int postid) {
		return removePost(getPost(postid));
	}

	@Override
	public int savePost(Post post) {
		int rv = 0;
		if(post.getPostid() > 0 && !StringUtils.isBlank(post.getTitle())){
			rv = postDao.updatePost(post);
			if(rv > 0){
				rv = post.getPostid();
				removeFromCache(postCache, rv);
				PostIndex.reindex(getPost(rv));
			}
		}else if(post.getPostid() <= 0 && !StringUtils.isBlank(post.getTitle())){
			rv  = postDao.insertPost(post);
			if(rv > 0){
				PostIndex.index(getPost(rv));
			}
		}
		return rv;
	}

	@Override
	public int getPostCountFromCatid(int catid) {
		return 5;
	}

	@Override
	public PostCategory getCategory(int catid) {
		if(catid<=0)
			return null;
		if(!containKeyInCache(postCategoryCache, catid)){
			PostCategory category = postDao.getCategory(catid);
			storePostCategory(category);
		}
		return (PostCategory) getValueFromCache(postCategoryCache, catid);
	}


	@Override
	public List<Post> listPost(PostCondition condition) {
		List<Post> list = PostIndex.listPost(condition);
		if (list == null) {
			list = postDao.listPost(condition);
//			for (Post post : list) {
//				storePost(post);
//			}
		}

		return list;
	}

	@Override
	public List<Post> getAllPosts() {
		List<Post> list = (List<Post>) getAllCacheValues(postCache);
		if(CollectionUtils.isNotEmpty(list) ){//&& list.size()==getAllLawyersCount()){
			return list;
		}
		list = postDao.getAllPosts();
		for (Post post : list) {
			setPost(post);
			storePost(post);
		}
		return list;
	}

	@Override
	public List<PostCategory> getAllPostCategorys() {
		List<PostCategory> list = (List<PostCategory>) getAllCacheValues(postCategoryCache);
		if(CollectionUtils.isNotEmpty(list) ){//&& list.size()==getAllLawyersCount()){
			return list;
		}
		list = postDao.getAllPostCategories();
		for (PostCategory category : list) {
			setPostCategory(category);
			storePostCategory(category);
		}
		return list;
	}


	@Override
	public boolean setPostStatus(int postid, String status) {
		boolean rv = false;
		Post post = getPost(postid);
		if(post != null){
			post.getStatus().setPostStatus(status);
			rv = postDao.setPostStatus(postid,status)>0?true:false;
		}
		return rv;
	}


	@Override
	public boolean changeOrder(int postid, int order) {
		return postDao.changeOrder(postid,order);
	}
}
