package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.PostCondition;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.model.post.PostStatus;

public class PostDaoSql extends EcardDao implements PostDao {
	

	@Override
	public PostStatus getPostStatus(int postid) {
		return (PostStatus) selectOne("selectPostStatus",postid);
	}

	@Override
	public Post getPost(int postid) {
		return (Post) selectOne("selectPostInfo", postid);
	}

	@Override
	public PostCategory getCategory(int catid) {
		return (PostCategory) selectOne("selectPostCategory",catid);
	}

	@Override
	public List<Post> listPost(PostCondition condition) {
		return (List<Post>)selectList("listPost", condition);
	}

	@Override
	public List<Post> getAllPosts() {
		return (List<Post>)selectList("selectAllPosts");		
	}

	@Override
	public List<PostCategory> getAllPostCategories() {
		return (List<PostCategory>)selectList("selectAllPostCategories");
	}

	@Override
	public int insertPost(Post post) {
		int rv  = insert("insertPost", post);
		if(rv > 0){
			insert("increaseOnePostCount",post.getCategory().getCatId());
		}
		return rv;
	}
	
	@Override
	public int updatePost(Post post) {
		return update("updatePost", post);
	}

	@Override
	public int deletePost(Post post) {
		int rv  = delete("deletePost", post.getPostid());
		if(rv > 0){
			insert("decreaseOnePostCount",post.getCategory().getCatId());
		}
		return rv;
	}

	@Override
	public int setPostStatus(int postid, String status) {
		return update("updatePostStatus",genMabatisMapParameter(postid, status));
	}

	@Override
	public boolean changeOrder(int postid, int order) {
		return update("changeorder",genMabatisMapValues(postid,order))>0?true:false;
	}
}
