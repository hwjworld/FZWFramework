package com.legaldaily.estension.ecard.model.post;

import java.util.Date;

import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.user.User;

public class Post extends EcardModel{
	private int postid;
	private User author;
	private Date postDate;
	private String content;
	private String contentLink;
	private String title;
	private String linkTitle;
	private PostCategory category;
	private int commentCount;
	private String attachmentUrl;//附件URL
	private PostStatus status;
	
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public User getAuthor() {
		if(author == null)
			author = new User();
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getContentLink() {
		return contentLink;
	}
	public void setContentLink(String contentLink) {
		this.contentLink = contentLink;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public PostCategory getCategory() {
		if(category == null)
			category = new PostCategory();
		return category;
	}
	public void setCategory(PostCategory category) {
		this.category = category;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	public PostStatus getStatus() {
		if(status == null)
			status = new PostStatus();
		return status;
	}
	public void setStatus(PostStatus status) {
		this.status = status;
	}

	public int getPostCount(){
		return getCategory().getPostsCount();
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
