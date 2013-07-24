/**
 * 
 */
package com.legaldaily.estension.ecard.model.question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.user.User;

/**
 * @author hwj
 *
 */
public class Question extends EcardModel {
	
	private long qid;
	private String title;
	private String content;
	//问题时间
	private Date createtime;
	//提问用户IP
	private String question_ip;
	/** 回复数 */
	private long replaycount;
	/** 是否审核 */
	private int isCheck;
	/** 问题排序,置顶 */
	private int order;
	//悬赏分值 q_value
	private int score;
	//是否可视 1|0 q_stat
	private String visible;
	//是否满意 1|0  q_lock
	private String satisfied;
	
	//组分类，q_id
	@Embedded
	private LawCategory lawCategory;
	@Embedded
	private Province province;
	//问题补充
	private String addContent;
	//问题补充状态
	private String addStats;
	//问题补充是否通过审核
	private String addCheck;
	private int pv;
	
	//提问用户
	@Embedded
	private User user;
	private List<Answer> answers;
	//最佳答案
	@Embedded
	private Answer bestAnswer;

	public Question() {
	}

	public Question(long qid, String title, String content) {
		this.qid = qid;
		this.title = title;
		this.content = content;
		createtime = new Date();
	}
	
	public void setBestAnswer(Answer bestAnswer) {
		this.bestAnswer = bestAnswer;
	}

	public List<Answer> getAnswers() {
		if(answers == null){
			answers = new ArrayList<Answer>();
		}
		return answers;
	}

	public void addAnswer(Answer answer) {
		if(answers == null){
			answers = new ArrayList<Answer>();
		}
		this.answers.add(answer);
	}
	public long getQid() {
		return qid;
	}
	public void setQid(long qid) {
		this.qid = qid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public User getUser() {
		if(user == null)
			user = new User();
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getQuestion_ip() {
		return question_ip;
	}
	public void setQuestion_ip(String question_ip) {
		this.question_ip = question_ip;
	}

	public Answer getBestAnswer() {
		return bestAnswer;
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public long getReplaycount() {
		return replaycount;
	}

	public void setReplaycount(long replaycount) {
		this.replaycount = replaycount;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getSatisfied() {
		return satisfied;
	}

	public void setSatisfied(String satisfied) {
		this.satisfied = satisfied;
	}

	public LawCategory getLawCategory() {
		if(lawCategory == null)
			lawCategory = new LawCategory();
		return lawCategory;
	}

	public void setLawCategory(LawCategory lawCategory) {
		this.lawCategory = lawCategory;
	}

	public Province getProvince() {
		if(province == null){
			province = new Province();
		}
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}
	
	public void clearAnswers(){
		answers.clear();
	}


	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String getAddContent() {
		return addContent;
	}

	public void setAddContent(String addContent) {
		this.addContent = addContent;
	}

	public String getAddStats() {
		return addStats;
	}

	public void setAddStats(String addStats) {
		this.addStats = addStats;
	}

	public String getAddCheck() {
		return addCheck;
	}

	public void setAddCheck(String addCheck) {
		this.addCheck = addCheck;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}
	public void addPv(){
		pv+=1;
	}
	
}
