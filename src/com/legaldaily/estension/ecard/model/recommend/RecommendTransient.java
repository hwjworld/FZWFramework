package com.legaldaily.estension.ecard.model.recommend;

import java.util.Date;

/**
 * 
 * 用于IBATIS查询ID中间存储类
 * @author hwj
 *
 */
public class RecommendTransient {

	private long qid = 0;
	private int userid = 0;
	private int order = 0 ;
	private Date recommendTime = null;
	public long getQid() {
		return qid;
	}
	public void setQid(long qid) {
		this.qid = qid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public Date getRecommendTime() {
		return recommendTime;
	}
	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

}
