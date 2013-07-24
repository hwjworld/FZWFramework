package com.legaldaily.estension.ecard.view.adapt.php;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.mina.filter.reqres.Request;


import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.service.command.QuestionCommands;

public class PHPQuestionListAdapter extends PHPAdapter {

	@Override
	public Object adapt(String methodName,Map<String, String> methodParam, Object returnValue) {
		if(returnValue == null){
			LogUtils.warn("No result in method\" [ "+methodName+" ]\"");
			return returnValue;
		}
		if (methodName.equals(QuestionCommands.GET_SOLVED_QUESTIONS)) {
			returnValue = adaptSlovedQuestionList(returnValue);
//		}else if (methodName.equals(QuestionCommands.GET_UNSOLVED_QUESTIONS)) {
//			returnValue = adaptUnSlovedQuestionList(returnValue);
//		}else if (methodName.equals(QuestionCommands.GET_NONEANSWER_QUESTIONS)) {
//			returnValue = adaptUnSlovedQuestionList(returnValue);
//		}else if (methodName.equals(QuestionCommands.GET_HIGHSCORE_QUESTIONS)) {
//			returnValue = adaptUnSlovedQuestionList(returnValue);
		}else if (methodName.equals(QuestionCommands.GET_LATEST_SOLVED_QUESTIONS)) {
			returnValue = adaptSlovedQuestionList(returnValue);
		}else if (methodName.equals(QuestionCommands.QUESTION_GET)) {
			returnValue = adaptQuestionInfo(returnValue);
		}else if (methodName.equals(QuestionCommands.GET_QUESTIONS)) {
//			if(QuestionsType.exist(methodParam.get("type"))){
				returnValue = adaptSlovedQuestionList(returnValue);
//			}else {
//				returnValue = adaptQuestionsList(returnValue);
//			}
		}else if (methodName.equals(QuestionCommands.GET_MEMBER_QUESTIONS)||
				methodName.equals(QuestionCommands.GET_NETFRIENDS_QUESTIONS)||
				methodName.equals(QuestionCommands.GET_ADDCONTENT_QUESTIONS)) {
			returnValue = adaptQuestionsList(returnValue);
		}
		return returnValue;
	}

	private Object adaptQuestionsList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
//			Question question = (Question) list.get(i);
//			PHPQuestionModel model = new PHPQuestionModel();
//			model.setQ_id(question.getQid());
//			model.setU_id(question.getUser().getUid());
//			model.setU_ip(question.getQuestion_ip());
//			model.setG_id(question.getLawCategory().getCatId());			
//			model.setQ_title(question.getTitle());
//			model.setQ_time(getDateString(question.getCreatetime()));
//			model.setQ_stats(question.getVisible());			
//			model.setQ_value(question.getScore());
//			model.setRep_values(question.getReplaycount());
//			model.setQ_lock(question.getSatisfied());
//			model.setQ_content(question.getContent());
//			model.setIn_turn(question.getOrder());
//			model.setNick_name(question.getUser().getNickname());
//			list.set(i, model);
			list.set(i, adaptQuestionInfo( list.get(i)));
		}
		return returnValue;
	}

	private Object adaptQuestionInfo(Object returnValue) {
		Question question = (Question) returnValue;
		PHPQuestionModel model = new PHPQuestionModel();
		model.setQ_id(question.getQid());
		model.setQ_title(question.getTitle());
		model.setQ_time(getDateString(question.getCreatetime()));
		model.setQ_value(question.getScore());
		model.setG_id(question.getLawCategory().getCatId());
		model.setU_name(question.getUser().getNickname());
		model.setG_name(question.getLawCategory().getCatName());
		model.setNick_name(question.getUser().getNickname());
		model.setPro_name(question.getProvince().getAreaName());
		model.setProvince_id(question.getProvince().getAreaId());
		model.setQ_content(question.getContent());
		model.setU_group(question.getUser().getUserGroup().getGroupname());
		model.setU_id(question.getUser().getUid());
		model.setQ_lock(question.getSatisfied());
		model.setAdd_check(question.getAddCheck());
		model.setAdd_content(question.getAddContent());
		model.setAdd_stats(question.getAddStats());
		model.setU_ip(question.getQuestion_ip());
		model.setG_title(question.getLawCategory().getCatName());
		model.setQ_stats(question.getVisible());
		model.setPv_values(question.getPv());
		if(question.getBestAnswer() == null){
			model.setAn_time(getDateString(null));
		}else {
			model.setAn_time(getDateString(question.getBestAnswer().getAnswer_time()));
		}
		return model;	
	}

	private Object adaptUnSlovedQuestionList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			Question question = (Question) list.get(i);
//			PHPQuestionModel model = new PHPQuestionModel();
//			model.setQ_id(question.getQid());
//			model.setQ_title(question.getTitle());
//			model.setQ_time(getDateString(question.getCreatetime()));
//			model.setQ_value(question.getScore());
//			model.setG_id(question.getLawCategory().getCatId());
//			list.set(i, model);
			PHPQuestionModel object = (PHPQuestionModel) adaptQuestionInfo( list.get(i));
			list.set(i, object);
		}
		return returnValue;
	}

	private Object adaptSlovedQuestionList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			
			Question question = (Question) list.get(i);
			PHPQuestionModel model = new PHPQuestionModel();
			model.setQ_lock(question.getSatisfied());
			
			if(question.getBestAnswer() != null){
				model.setAn_time(getDateString(question.getBestAnswer().getAnswer_time()));
				model.setNick_name(question.getBestAnswer().getUser().getNickname());
				model.setU_group(question.getBestAnswer().getUser().getUserGroup().getGroupname());
				model.setU_id(question.getBestAnswer().getUser().getUid());
			}else {
				model.setNick_name(question.getUser().getNickname());
				model.setU_group(question.getUser().getUserGroup().getGroupname());
				model.setU_id(question.getUser().getUid());
			}
			model.setQ_stats(question.getVisible());
			model.setQ_id(question.getQid());
			model.setQ_value(question.getScore());
			model.setQ_time(getDateString(question.getCreatetime()));
			model.setQ_title(question.getTitle());
			model.setG_name(question.getLawCategory().getCatName());
			model.setG_id(question.getLawCategory().getCatId());
			int repvalue = 0;
			if(CollectionUtils.isNotEmpty(question.getAnswers())){
				for (Answer answer : question.getAnswers()) {
					if(answer.getVisible().equals("1")){
						repvalue++;
					}
				}
			}
			if(question.getBestAnswer() != null){
				repvalue++;
			}
//			model.setRep_values(question.getReplaycount());
			model.setRep_values(repvalue);
			model.setG_title(question.getLawCategory().getCatName());
			model.setIn_turn(question.getOrder());
			list.set(i, model);

//			因为这儿的nick_name为解答人的,而不是问题的,所以不适用下面的方法
//			list.set(i, adaptQuestionInfo( list.get(i)));
		}
		return returnValue;
	}

}
class PHPQuestionModel{
	private long q_id,rep_values;
	//score, lowcategory
	private int u_id,q_value,g_id,in_turn,pv_values,province_id;
	private String an_time,q_time,q_title,nick_name,u_group,q_stats,u_ip,g_title;
	//fields below used for question detail information 
	private String u_name,q_content,g_name,pro_name,q_lock,add_content,add_stats,add_check;
	
	
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getAn_time() {
		return an_time;
	}
	public void setAn_time(String an_time) {
		this.an_time = an_time;
	}
	public String getQ_title() {
		return q_title;
	}
	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
		setU_name(nick_name);
	}
	public String getU_group() {
		return u_group;
	}
	public void setU_group(String u_group) {
		this.u_group = u_group;
	}
	public long getQ_id() {
		return q_id;
	}
	public void setQ_id(long q_id) {
		this.q_id = q_id;
	}
	public int getQ_value() {
		return q_value;
	}
	public void setQ_value(int q_value) {
		this.q_value = q_value;
	}
	public int getG_id() {
		return g_id;
	}
	public void setG_id(int g_id) {
		this.g_id = g_id;
	}
	public String getQ_time() {
		return q_time;
	}
	public void setQ_time(String q_time) {
		this.q_time = q_time;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getQ_content() {
		return q_content;
	}
	public void setQ_content(String q_content) {
		this.q_content = q_content;
	}
	public String getG_name() {
		return g_name;
	}
	public void setG_name(String g_name) {
		this.g_name = g_name;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getQ_lock() {
		return q_lock;
	}
	public void setQ_lock(String q_lock) {
		this.q_lock = q_lock;
	}
	public String getAdd_content() {
		return add_content;
	}
	public void setAdd_content(String add_content) {
		this.add_content = add_content;
	}
	public String getAdd_stats() {
		return add_stats;
	}
	public void setAdd_stats(String add_stats) {
		this.add_stats = add_stats;
	}
	public String getAdd_check() {
		return add_check;
	}
	public void setAdd_check(String add_check) {
		this.add_check = add_check;
	}
	public long getRep_values() {
		return rep_values;
	}
	public void setRep_values(long rep_values) {
		this.rep_values = rep_values;
	}
	public String getQ_stats() {
		return q_stats;
	}
	public void setQ_stats(String q_stats) {
		this.q_stats = q_stats;
	}
	public String getU_ip() {
		return u_ip;
	}
	public void setU_ip(String u_ip) {
		this.u_ip = u_ip;
	}
	public int getIn_turn() {
		return in_turn;
	}
	public void setIn_turn(int in_turn) {
		this.in_turn = in_turn;
	}
	public String getG_title() {
		return g_title;
	}
	public void setG_title(String g_title) {
		this.g_title = g_title;
	}
	public int getPv_values() {
		return pv_values;
	}
	public void setPv_values(int pv_values) {
		this.pv_values = pv_values;
	}
	public int getProvince_id() {
		return province_id;
	}
	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}
	
}