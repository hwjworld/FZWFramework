package com.legaldaily.estension.ecard.view.adapt.php;

import java.util.List;
import java.util.Map;

import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.post.PostCategory;
import com.legaldaily.estension.ecard.service.command.PostCommands;

public class PHPPostAdapter extends PHPAdapter {

	@Override
	public Object adapt(String methodName,Map<String, String> methodParam, Object returnValue) {
		if(returnValue == null){
			LogUtils.warn("No result in method\" [ "+methodName+" ]\"");
			return returnValue;
		}
		if (methodName.equals(PostCommands.POST_LIST)) {
			returnValue = adaptPostList(returnValue);
		}else if (methodName.equals(PostCommands.POST_GET)) {
			returnValue = adaptPostInfo(returnValue);			
		}else if (methodName.equals(PostCommands.GET_POST_CATEGORY)) {
			returnValue = adaptPostCategory(returnValue);
		}else if (methodName.equals(PostCommands.GET_All_POST_CATEGORY)
				|| methodName.equals(PostCommands.GET_POST_CATEGORY_BYPARENTID)) {
			returnValue = adaptPostCategoryList(returnValue);
		}
		return returnValue;
	}
	
	private Object adaptPostCategoryList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			list.set(i, adaptPostCategory(list.get(i)));
		}
		return list;
	}

	private Object adaptPostCategory(Object returnValue) {
		PostCategory category = (PostCategory) returnValue;
		PHPPostCategoryModel model = new PHPPostCategoryModel();
		model.setCat_name(category.getCatName());
		model.setCat_id(category.getCatId());
		model.setEditors(category.getEditors());
		return model;
	}

	private Object adaptPostInfo(Object returnValue) {
		Post post = (Post)returnValue;
		PHPPostModel model = new PHPPostModel();
		model.setID(post.getPostid());
		model.setContent_link(post.getContentLink());
		model.setPost_title(post.getTitle());
		model.setLink_title(post.getLinkTitle());
		model.setAttachment_url(post.getAttachmentUrl());
		model.setPost_category(post.getCategory().getCatId());
		model.setCatname(post.getCategory().getCatName());
		model.setPost_content(post.getContent());
		model.setAuthor_name(post.getAuthor().getNickname());
		model.setPost_date(getDateString(post.getPostDate()));
		model.setPost_status(post.getStatus().getPostStatus());
		model.setPv_values(post.getStatus().getPv());
		model.setMenu_order(post.getStatus().getOrder());
		return model;
	}

	private Object adaptPostList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			list.set(i, adaptPostInfo(list.get(i)));
		}
		return returnValue;
	
	}

}
class PHPPostModel{
	private int ID;
	private String content_link;
	private String post_title;
	private String link_title;
	private String attachment_url;
	private int post_category;
	private String catname;
	private String post_content;
	private String author_name;
	private String post_date;
	private String post_status;
	private int pv_values;
	private float menu_order;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getContent_link() {
		return content_link;
	}
	public void setContent_link(String content_link) {
		this.content_link = content_link;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getLink_title() {
		return link_title;
	}
	public void setLink_title(String link_title) {
		this.link_title = link_title;
	}
	public String getAttachment_url() {
		return attachment_url;
	}
	public void setAttachment_url(String attachment_url) {
		this.attachment_url = attachment_url;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	public int getPost_category() {
		return post_category;
	}
	public void setPost_category(int post_category) {
		this.post_category = post_category;
	}
	public String getCatname() {
		return catname;
	}
	public void setCatname(String catname) {
		this.catname = catname;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public String getPost_date() {
		return post_date;
	}
	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}
	public String getPost_status() {
		return post_status;
	}
	public void setPost_status(String post_status) {
		this.post_status = post_status;
	}
	public int getPv_values() {
		return pv_values;
	}
	public void setPv_values(int pv_values) {
		this.pv_values = pv_values;
	}
	public float getMenu_order() {
		return menu_order;
	}
	public void setMenu_order(float menu_order) {
		this.menu_order = menu_order;
	}
}
class PHPPostCategoryModel{
	private String cat_name,editors;
	private int cat_id;

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public String getEditors() {
		return editors;
	}

	public void setEditors(String editors) {
		this.editors = editors;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
}