package com.legaldaily.estension.ecard.model.condition;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

import com.fzw.model.ConnectionMessage;
import com.fzw.utils.StringValueUtils;

public class QuestionCondition extends EcardServiceCondition {

	private long[] qid;
	private int startpos;
	private int count;
	
	//maybe for search fields below..
	//q_time
	private Date endTime;
	//q_time
	private Date startTime;
	//q_title
	private String title;
	//q_stats
	private String visible;
	//q_lock
	private String satisfy;
	//check_flag
	private String check;
	private String orderby;
	private int userId;
	//req_value
	private int replyCount;
	//1-=, 2-<, 3->
	private int replyCountMark;
	//q_value
	private int scoreValue;
	private int scoreValueMark;
	
	//问题正文内容
	private String content;
	private int groupId;
	private int provinceId;

	public QuestionCondition(ConnectionMessage message) {
		super(message);
	}

	public QuestionCondition() {
	}

	public long[] getQid() {
		return qid;
	}
	public void setQid(long[] qid) {
		this.qid = qid;
	}
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getStartpos() {
		return startpos;
	}
	public void setStartpos(int startpos) {
		this.startpos = startpos;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(String satisfy) {
		this.satisfy = satisfy;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getOrderby() {
		return orderby;
	}

	/**
	 * 单独调用请使用
	 * @param orderby
	 */
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	
	@Override
	public void buildCondition() {
		qid = getIdsLong(params.get("questionid"));
		if(ArrayUtils.isEmpty(qid)){
			qid = getIdsLong(params.get("qid"));
		}
		startpos = StringValueUtils.getWWPInt(params, "startpos");
		count = StringValueUtils.getWWPInt(params, "count");
//		if(params.containsKey("endtime")){
			endTime = StringValueUtils.getWWPDate(params, "endtime");
//		}else {
//			endTime = null;
//		}if(params.containsKey("startTime")){
			startTime = StringValueUtils.getWWPDate(params, "starttime","1970-01-01");
//		}else {
//			startTime = null;
//		}
		title = StringValueUtils.getWWPString(params, "title", null);
		visible = StringValueUtils.getWWPString(params, "visible", null);
		satisfy = StringValueUtils.getWWPString(params, "satisfy", null);
		check = StringValueUtils.getWWPString(params, "check", null);
		setOrderby(getOrderBySql(StringValueUtils.getWWPString(params, "orderby",null)));
		int [] replyCountStrings = getComparativeFiled(StringValueUtils.getWWPString(params, "replyCount",null)) ;
		if(replyCountStrings != null){
			replyCountMark = replyCountStrings[0];
			replyCount = replyCountStrings[1];
		}
		int [] scoreValueStrings = getComparativeFiled(StringValueUtils.getWWPString(params, "scoreValue",null));
		if(scoreValueStrings != null){
			scoreValueMark = scoreValueStrings[0];
			scoreValue = scoreValueStrings[1];
		}
		groupId = StringValueUtils.getWWPInt(params, "groupId");
		provinceId = StringValueUtils.getWWPInt(params, "provinceid");
		content = StringValueUtils.getWWPString(params, "content");
	}


	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public int getScoreValue() {
		return scoreValue;
	}

	public int getReplyCountMark() {
		return replyCountMark;
	}

	public void setReplyCountMark(int replyCountMark) {
		this.replyCountMark = getMark(replyCountMark);
	}

	public int getScoreValueMark() {
		return scoreValueMark;
	}

	public void setScoreValueMark(int scoreValueMark) {
		this.scoreValueMark = getMark(scoreValueMark);
	}
	
	private int getMark(int mark){
		if(mark == 1 || mark == '='){
			return 1;
		}else if (mark == 2 || mark == '<') {
			return 2;
		}else if (mark == 3 || mark == '>') {
			return 3;
		}else {
			return 1;
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
}