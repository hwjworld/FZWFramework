package com.legaldaily.estension.ecard.model.condition;

import java.util.Date;

import com.fzw.model.ConnectionMessage;
import com.fzw.utils.StringValueUtils;

public class AnswerCondition extends EcardServiceCondition {

	public AnswerCondition(ConnectionMessage message) {
		super(message);
	}
	private long answerId[];
	private Date startTime;
	private Date endTime;
	private String best;
	private String visible;
	private String check;
	private int score;
	private int scoreMark;
	private String orderby;
	private int startpos;
	private int count;
	private String satisfy;

	//回答的信息
	private String content;
	@Override
	public void buildCondition() {
		answerId = getIdsLong(params.get("answerid"));
		startTime = StringValueUtils.getWWPDate(params, "starttime","1970-01-01");
		endTime = StringValueUtils.getWWPDate(params, "endtime");
		best = StringValueUtils.getWWPString(params, "best", null);
		visible = StringValueUtils.getWWPString(params, "visible", null);
		check = StringValueUtils.getWWPString(params, "check", null);
		best = StringValueUtils.getWWPString(params, "best", null);
		setOrderby(getOrderBySql(StringValueUtils.getWWPString(params, "orderby",null)));
		int [] scoreStrings = getComparativeFiled(StringValueUtils.getWWPString(params, "score",null)) ;
		if(scoreStrings != null){
			scoreMark = scoreStrings[0];
			score = scoreStrings[1];
		}
		startpos = StringValueUtils.getWWPInt(params, "startpos");
		count = StringValueUtils.getWWPInt(params, "count");
		satisfy = StringValueUtils.getWWPString(params, "satisfy");
		content = StringValueUtils.getWWPString(params, "content");
	}

	public long[] getAnswerId() {
		return answerId;
	}

	public void setAnswerId(long[] answerId) {
		this.answerId = answerId;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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

	public String getBest() {
		return best;
	}

	public void setBest(String best) {
		this.best = best;
	}

	public int getScoreMark() {
		return scoreMark;
	}

	public void setScoreMark(int scoreMark) {
		this.scoreMark = scoreMark;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public int getStartpos() {
		return startpos;
	}

	public void setStartpos(int startpos) {
		this.startpos = startpos;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSatisfy() {
		return satisfy;
	}

	public void setSatisfy(String satisfy) {
		this.satisfy = satisfy;
	}

}
	