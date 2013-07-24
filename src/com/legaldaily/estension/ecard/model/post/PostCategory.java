package com.legaldaily.estension.ecard.model.post;

import java.util.Date;

import com.fzw.domain.DomainEvents;
import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.user.User;

public class PostCategory extends EcardModel{
	private int catId;
	private String catName;
	private Date creatTime;
	private int parentId;//栏目父ID
	private int posts_cnt;  //use DomainEvent instead
	private User[] editor;
	
	//为了数据库的字段才分出来的这一个属性，请勿使用，请使用editor
	private String editors;
	
	private final PostCategoryVO postCategoryVO;

	private DomainEvents domainEvent;
	
	public PostCategory() {
		postCategoryVO = new PostCategoryVO(this);
	}
	
	private int pv;
	
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public User[] getEditor() {
		return editor;
	}
	public void setEditor(User[] editor) {
		this.editor = editor;
	}
	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	/**
	 * @return Returns the postsCount.
	 */
	public int getPostsCount() {
		return postCategoryVO.getPostsCount(domainEvent);
	}
	public void updatePostsCount(int count) {
		postCategoryVO.update(count);
	}
	public PostCategoryVO getPostCategoryVO() {
		return postCategoryVO;
	}
	public String getEditors() {
		return editors;
	}
	public void setEditors(String editors) {
		this.editors = editors;
	}
	public int getPosts_cnt() {
		return posts_cnt;
	}
	public void setPosts_cnt(int posts_cnt) {
		this.posts_cnt = posts_cnt;
	}
}
