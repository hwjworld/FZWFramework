package com.legaldaily.estension.ecard.model.recommend;

import java.util.Date;

import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.user.User;

/**
 * 一个推荐
 */
public class Recommendation extends EcardModel {
	private Question question = null;
	private int order = 0;
	private User referees = null;
	private Date recommendTime = null;
	
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public User getReferees() {
		return referees;
	}
	public void setReferees(User referees) {
		this.referees = referees;
	}
	public Date getRecommendTime() {
		return recommendTime;
	}
	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}
	
}
