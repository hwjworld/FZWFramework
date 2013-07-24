package com.legaldaily.estension.ecard.model.condition;

import java.util.Date;

import com.fzw.model.ConnectionMessage;
import com.fzw.utils.StringValueUtils;

public class PostCondition extends EcardServiceCondition {

	public PostCondition(ConnectionMessage message) {
		super(message);
	}
	
	private int postid[];
	private Date starttime;
	private Date endtime;
	private int catid;
	private int count;
	private String status;
//	private int titlelen;
	private int benpos;
	private String check;
	private String orderby;
	
	//用于更新和增加
	private String content;
	
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public int getCatid() {
		return catid;
	}
	public void setCatid(int catid) {
		this.catid = catid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

//	public int getTitlelen() {
//		return titlelen;
//	}
//	public void setTitlelen(int titlelen) {
//		this.titlelen = titlelen;
//	}
	public int getBenpos() {
		return benpos;
	}
	public void setBenpos(int benpos) {
		this.benpos = benpos;
	}

	/**
	 * [field],[desc|asc]...
	 * @return
	 */
	public String[] getOrderby() {
		return getOrderBy(orderby);
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public int[] getPostid() {
		return postid;
	}
	public void setPostid(int[] postid) {
		this.postid = postid;
	}
	@Override
	public void buildCondition() {

//		String[] ids = StringUtils.split(params.get("postid"), ";");
		
		String[] ids = getIds(params.get("postid"));
		postid = StringValueUtils.convert2intArrays(ids);
		
		
		catid = StringValueUtils.getWWPInt(params, "catid");
		count = StringValueUtils.getWWPInt(params, "count");
		status = StringValueUtils.getWWPString(params, "status");
		starttime = StringValueUtils.getWWPDate(params, "starttime","19000101");
		endtime = StringValueUtils.getWWPDate(params, "endtime");
//		titlelen = StringValueUtils.getWWPInt(params, "titlelen");
		benpos = StringValueUtils.getWWPInt(params, "benpos");
		orderby = StringValueUtils.getWWPString(params, "orderby");
		content = StringValueUtils.getWWPString(params, "content");
		check = StringValueUtils.getWWPString(params, "check");
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
}
