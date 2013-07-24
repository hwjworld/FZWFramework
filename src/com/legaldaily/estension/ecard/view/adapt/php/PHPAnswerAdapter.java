package com.legaldaily.estension.ecard.view.adapt.php;

import java.util.List;
import java.util.Map;

import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.service.command.AnswerCommands;

public class PHPAnswerAdapter extends PHPAdapter {

	@Override
	public Object adapt(String methodName,Map<String, String> methodParam, Object returnValue) {
		if(returnValue == null){
			LogUtils.warn("No result in method\" [ "+methodName+" ]\"");
			return returnValue;
		}
		if(methodName.equals(AnswerCommands.GET_LATESTLAWYER_ANSWERS)
				|| methodName.equals(AnswerCommands.GET_LATESTNETFRIENDS_ANSWERS)
				|| methodName.equals(AnswerCommands.GET_ANSWERS)
				|| methodName.equals(AnswerCommands.GET_ANSWERS_BY_QUESTIONID)){
			returnValue = adaptAnswerList(returnValue);
		}
		return returnValue;
	}

	private Object adaptAnswerList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			Answer answer = (Answer) list.get(i);
			PHPAnswerModel model = new PHPAnswerModel();
			model.setA_content(answer.getContent());
			model.setA_id(answer.getId());
			model.setA_stats(answer.getVisible());
			model.setA_time(getDateString(answer.getAnswer_time()));
			model.setG_title(answer.getQuestion().getLawCategory().getCatName());
			model.setNick_name(answer.getUser().getNickname());
			model.setQ_title(answer.getQuestion().getTitle());
			model.setQuestion_id(answer.getQuestion().getQid());
			list.set(i, model);
		}
		return returnValue;
	}

}

class PHPAnswerModel{
	private long question_id,a_id;
	private String q_title,a_time,a_content,g_title,nick_name,a_stats;
	public long getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(long question_id) {
		this.question_id = question_id;
	}
	public long getA_id() {
		return a_id;
	}
	public void setA_id(long a_id) {
		this.a_id = a_id;
	}
	public String getQ_title() {
		return q_title;
	}
	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}
	public String getA_time() {
		return a_time;
	}
	public void setA_time(String a_time) {
		this.a_time = a_time;
	}
	public String getA_content() {
		return a_content;
	}
	public void setA_content(String a_content) {
		this.a_content = a_content;
	}
	public String getG_title() {
		return g_title;
	}
	public void setG_title(String g_title) {
		this.g_title = g_title;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getA_stats() {
		return a_stats;
	}
	public void setA_stats(String a_stats) {
		this.a_stats = a_stats;
	}
}