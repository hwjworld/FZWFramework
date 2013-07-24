package com.legaldaily.estension.ecard.model.question;

import java.util.Date;

import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.user.User;

public class Answer extends EcardModel {

	private long id;
	
	private String content;
	
	private Date answer_time;
	//回答用户ip
	private String ip;
	//得分
	private int score;
	
	//所属问题
	private Question question;
	//回答用户
	private User user;
	
	//q_stat
	private String visible;
	
	//审核标志
	private int check_flag;

	public Answer() {
		// TODO Auto-generated constructor stub
	}
	public Answer(long id, String content) {
		this.id = id;
		this.content = content;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getAnswer_time() {
		return answer_time;
	}
	public void setAnswer_time(Date answer_time) {
		this.answer_time = answer_time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Question getQuestion() {
		if(question == null)
			question = new Question();
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public User getUser() {
		if(user == null)
			user = new User();
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getCheck_flag() {
		return check_flag;
	}
	public void setCheck_flag(int check_flag) {
		this.check_flag = check_flag;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getVisible() {
		return visible;
	}
	
}
