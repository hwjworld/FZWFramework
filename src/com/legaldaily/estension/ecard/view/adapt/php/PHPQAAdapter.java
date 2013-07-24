package com.legaldaily.estension.ecard.view.adapt.php;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.recommend.Recommendation;
import com.legaldaily.estension.ecard.model.user.User;

public class PHPQAAdapter extends PHPAdapter {

	@Override
	public Object adapt(String methodName,Map<String, String> methodParam, Object returnValue) {

		if(returnValue == null){
			LogUtils.warn("No result in method\" [ "+methodName+" ]\"");
			return returnValue;
		}
//		if (methodName.equals("getQARList")) {// aop
		if (methodName.equals("listQARecommend")) {// ServiceInvokerProxy
			returnValue = adaptQAList(returnValue);
//		} else if (methodName.equals("getQARWaitList")) {
		} else if (methodName.equals("listWaitQARecommend")) {
			returnValue = adaptQAWaitList(returnValue);
		}
		return returnValue;
	}

	// 适配成　a.q_id,a.order_by,b.q_title,b.q_time
	private Object adaptQAList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			Recommendation recommendation = (Recommendation) list.get(i);
			PHPQAModel model = new PHPQAModel();
			model.setQ_id(recommendation.getQuestion().getQid());
			model.setQ_time(getDateString(recommendation.getQuestion().getCreatetime()));
			model.setQ_title(recommendation.getQuestion().getTitle());
			model.setOrder_by(recommendation.getOrder());
			
			Answer bestAnswer = recommendation.getQuestion().getBestAnswer();
			if(bestAnswer != null){
				User bestAnswerUser = bestAnswer.getUser();
				model.setU_id(bestAnswerUser.getUid());
				model.setU_group(bestAnswerUser.getUserGroup().getGroupid());
				model.setNick_name(bestAnswerUser.getNickname());
				
			}else {
				model.setU_id(0);
				model.setU_group(0);
				model.setNick_name("no answer");
			}
			list.set(i, model);
		}
		return returnValue;
	}

	private Object adaptQAWaitList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			Question recommendation = (Question) list.get(i);
			PHPQAWaitModel model = new PHPQAWaitModel();
			model.setQ_id(recommendation.getQid());
			model.setQ_time(DateFormatUtils.format(
					recommendation.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
			model.setQ_title(recommendation.getTitle());
			model.setU_id(recommendation.getUser().getUid());
			list.set(i, model);
		}
		return returnValue;
	}
}

class PHPQAModel {
	//stat_tuijian.php needs fields below
	long q_id;
	int order_by;
	String q_title;
	String q_time;
	//index.php needs fields below
	//a.q_id,a.u_id,b.u_group,b.nick_name,c.q_title,DATE_FORMAT(c.q_time,'%m月%d日') as q_time
	int u_id;
	int u_group;
	String nick_name;
	

	public long getQ_id() {
		return q_id;
	}

	public void setQ_id(long q_id) {
		this.q_id = q_id;
	}

	public int getOrder_by() {
		return order_by;
	}

	public void setOrder_by(int order_by) {
		this.order_by = order_by;
	}

	public String getQ_title() {
		return q_title;
	}

	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}

	public String getQ_time() {
		return q_time;
	}

	public void setQ_time(String q_time) {
		this.q_time = q_time;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public int getU_group() {
		return u_group;
	}

	public void setU_group(int u_group) {
		this.u_group = u_group;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

}

// a.q_id,a.q_title,a.q_time,b.u_id
class PHPQAWaitModel {
	private long q_id;
	private String q_title;
	private String q_time;
	private int u_id;

	public long getQ_id() {
		return q_id;
	}

	public void setQ_id(long q_id) {
		this.q_id = q_id;
	}

	public String getQ_title() {
		return q_title;
	}

	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}

	public String getQ_time() {
		return q_time;
	}

	public void setQ_time(String q_time) {
		this.q_time = q_time;
	}

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
}
