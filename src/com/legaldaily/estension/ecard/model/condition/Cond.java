package com.legaldaily.estension.ecard.model.condition;

import java.util.Date;

import com.fzw.model.ConnectionMessage;
import com.fzw.utils.StringValueUtils;

/**
 * 问答推荐查询条件
 * 
 * @author hwj
 * 
 */
public class Cond extends EcardServiceCondition {

	private static final int defaultRecommendCount = 15;
	private static final int defaultWaitRecommendCount = 100;

	public Cond(ConnectionMessage message) {
		super(message);
	}
	private long[] qid;
	private int operatorid;
	private Date recommendTime;
	private Date startTime;
	private Date endTime;
	private String text;
	private int count;


	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean buildRecommendCond() {
		Cond cond = this;
		cond.count = StringValueUtils.getWWPInt(cond.params, "count", defaultRecommendCount);
		return true;
	}
	
	public boolean buildQAWaitRCond() {
		Cond cond = this;
		cond.text = StringValueUtils.getWWPString(cond.params, "text","%");
		cond.startTime = StringValueUtils.getWWPDate(cond.params, "starttime","20070101");
		cond.endTime = StringValueUtils.getWWPDate(cond.params, "endtime");
		cond.count = StringValueUtils.getWWPInt(cond.params, "count", defaultWaitRecommendCount);
		return true;
	}

	public boolean buildAddRecommendCond() {
		String[] ids = getIds(params.get("qid"));
		qid = StringValueUtils.convert2longArrays(ids);
		operatorid = StringValueUtils.getWWPInt(params, "operatorid");
		recommendTime = StringValueUtils.getWWPDate(params, "recommendTime");
		
		if(operatorid == 0 || qid.length<=0){
			return false;
		}
		return true;
	}

	public boolean buildRemoveRecommendCond() {
		String[] ids = getIds(params.get("qid"));
		qid = StringValueUtils.convert2longArrays(ids);
		if(qid.length <= 0){
			return false;
		}
		return true;
	}

	public boolean buildMoveRecommendPositionCond() {
		buildRemoveRecommendCond();
		if(qid.length>2 || qid.length<=0){
			return false;
		}
		return true;
	}

	public int getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(int operatorid) {
		this.operatorid = operatorid;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

	public long[] getQid() {
		return qid;
	}

	public void setQid(long[] qid) {
		this.qid = qid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ConnectionMessage getMessage() {
		return message;
	}

	public void setMessage(ConnectionMessage message) {
		this.message = message;
	}

	@Override
	public void buildCondition() {
		// TODO Auto-generated method stub
		
	}
}
