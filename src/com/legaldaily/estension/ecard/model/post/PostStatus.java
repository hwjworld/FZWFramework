package com.legaldaily.estension.ecard.model.post;

import java.io.Serializable;

public class PostStatus implements Serializable{

	private String postStatus;//文章状态 'publish','wait','delete'
	private String commentStatus;//评论状态 'open','closed','registered_only'
	private int isAudit;//是否审核 '1','0'
	private int pv;//浏览量
	private float order;//文章排序
	
	public String getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
	public String getCommentStatus() {
		return commentStatus;
	}
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}
	public int getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(int isAudit) {
		this.isAudit = isAudit;
	}
	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public float getOrder() {
		return order;
	}
	public void setOrder(float order) {
		this.order = order;
	}
}
