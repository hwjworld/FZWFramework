package com.legaldaily.estension.ecard.service.question;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fzw.model.ConnectionMessage;
import com.fzw.service.ServiceConvey;
import com.fzw.utils.DateUtil;
import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.cache.LatestSolvedQuestionIndex;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.repository.QuestionRepository;

public class QuestionServicesConvey extends ServiceConvey<QuestionCondition> {

	QuestionServices questionServices = null;

	public QuestionServicesConvey(QuestionRepository areaRepository) {
		questionServices = new QuestionServicesImpl(areaRepository);
	}

	public Question getQuestion(EcardServiceCondition condition) {
		return questionServices
				.getQuestion(getCondition(condition).getQid()[0]);
	}

	public boolean updateQuestion(EcardServiceCondition condition) {
		Question question = new Question();
		LawCategory lawCategory = new LawCategory();
		lawCategory.setCatId(condition.getIntValue("groupid"));
		question.setLawCategory(lawCategory);
		question.setTitle(condition.getValue("title"));
		question.setContent(condition.getValue("content"));
		Province province = new Province();
		province.setAreaId(condition.getIntValue("provinceid"));
		question.setProvince(province);
		question.setQid(condition.getLongValue("qid"));
		if(questionServices.updateQuestion(question)>0)
			return true;
		else
			return false;
	}

	// insert into
	// questions(u_id,u_ip,g_id,q_title,q_content,q_time,q_value,province_id,q_stats,rep_values)
	// values('$current_userid','$u_ip',$q_cat,'$question_title','$q_content',now(),$q_value,$q_province,'$q_stats',0)
	public boolean addQuestion(EcardServiceCondition condition) {
//		String questionInfo = CodeUtil.decodeBase64(getCondition(condition)
//				.getContent());
		
		String values[] = ConnectionMessage.getValue(getCondition(condition).getMessage(), "u_id",
				"u_ip", "g_id", "q_title", "q_content", "q_time", "q_value",
				"province_id", "q_stats");
		Question question = new Question();
		question.getUser().setUid(StringValueUtils.getInt(values[0]));
		question.setQuestion_ip(values[1]);
		question.getLawCategory().setCatId(StringValueUtils.getInt(values[2]));
		question.setTitle(values[3]);
		question.setContent(StringUtils.replace(values[4], "\\\\", "\\"));
		try {
			question.setCreatetime(DateUtil.parseDate(values[5]));
		} catch (ParseException e) {
			question.setCreatetime(new Date());
		}
		question.setScore(StringValueUtils.getInt(values[6]));
		question.getProvince().setAreaId(StringValueUtils.getInt(values[7]));
		question.setVisible(values[8]);
		if(questionServices.addQuestion(question)>0)return true;
		else 
			return false;
	}

	public void delQuestion(EcardServiceCondition condition) {
		questionServices.delQuestion(getCondition(condition).getQid());
	}

	// 已解决问题列表
//	public List<Question> getSolvedQuestions(EcardServiceCondition condition) {
////		return questionServices.getSolvedQuestions(getCondition(condition)
////				.getStartpos(), getCondition(condition).getCount(),getCondition(condition).getProvinceId());
//
//		return LatestSolvedQuestionIndex.getQsuestions(getCondition(condition));
//	}

//	// 未解决问题列表
//	public List<Question> getUnSolvedQuestions(EcardServiceCondition condition) {
////		return questionServices.getUnSolvedQuestions(getCondition(condition)
////				.getStartpos(), getCondition(condition).getCount(),getCondition(condition).getProvinceId());
////		return questionServices.getUnSolvedQuestions(getCondition(condition));
//		QuestionCondition condition2 = getCondition(condition);
//		return LatestSolvedQuestionIndex.getUnSolvedQuestions(condition2);
//	}

//	public List<Question> getNoneAnswerQuestions(EcardServiceCondition condition) {
////		return questionServices.getNoneAnswerQuestions(getCondition(condition)
////				.getStartpos(), getCondition(condition).getCount(),getCondition(condition).getProvinceId());
//		return questionServices.getNoneAnswerQuestions(getCondition(condition));
//	}

//	public List<Question> getHighScoreQuestions(EcardServiceCondition condition) {
////		return questionServices.getHighScoreQuestions(getCondition(condition)
////				.getStartpos(), getCondition(condition).getCount(),getCondition(condition).getProvinceId());
//		return questionServices.getHighScoreQuestions(getCondition(condition));
//	}
//	public long getHighScoreQuestionsCount(EcardServiceCondition condition) {
////		return questionServices.getHighScoreQuestions(getCondition(condition)
////				.getStartpos(), getCondition(condition).getCount(),getCondition(condition).getProvinceId());
//		return questionServices.getHighScoreQuestionsCount(getCondition(condition));
//	}

	public List<Question> getQuestions(EcardServiceCondition condition) {
//		return questionServices.getQuestions(getCondition(condition));
		return LatestSolvedQuestionIndex.getQuestions(getCondition(condition));
	}
	public long getQuestionsCount(EcardServiceCondition condition) {
		return questionServices.getQuestionsCount(getCondition(condition));
	}

	public boolean isLocked(EcardServiceCondition condition) {
		return questionServices.isLocked(questionServices
				.getQuestion(getCondition(condition).getQid()[0]));
	}

	public boolean passQuestions(EcardServiceCondition condition) {
		return questionServices.passQuestions(getCondition(condition).getQid());
	}
	public boolean forbidQuestions(EcardServiceCondition condition) {
		return questionServices.forbidQuestions(getCondition(condition).getQid());
	}

	public boolean unsetBestAnswer(EcardServiceCondition condition) {
		return questionServices.unsetBestAnswer(questionServices
				.getQuestion(getCondition(condition).getQid()[0]));
	}

	public List<Question> getNetFriendsQuestions(EcardServiceCondition condition) {
		return questionServices.getNetFriendsQuestions(getCondition(condition));
	}
	public int getNetFriendsQuestionsCount(EcardServiceCondition condition) {
		return questionServices.getNetFriendsQuestionsCount(StringValueUtils.getBoolean(condition.getValue("all")));
	}

	public List<Question> getAddContentQuestions(EcardServiceCondition condition) {
		return questionServices.getAddContentQuestions(getCondition(condition));
	}
	public int getAddContentQuestionsCount(EcardServiceCondition condition) {
		return questionServices.getAddContentQuestionsCount(StringValueUtils.getBoolean(condition.getValue("all")));
	}
	
	
	public List<Question> getMemberQuestions(EcardServiceCondition condition) {
		return questionServices.getMemberQuestions(getCondition(condition));
	}
	public int getMemberQuestionsCount(EcardServiceCondition condition) {
		return questionServices.getMemberQuestionsCount(StringValueUtils.getBoolean(condition.getValue("all")));
	}

	
	public List<Question> getLatestSolvedQuestionByGroup(EcardServiceCondition condition) {
		QuestionCondition condition2 = getCondition(condition);
		return LatestSolvedQuestionIndex.getLatestSolvedQuestionByGroup(condition2.getGroupId(), condition2.getCount());
//		return questionServices.getLatestSolvedQuestionByGroup(getCondition(condition));
	}
	
	public long getSolvedQuestionsCount(EcardServiceCondition condition){
		return questionServices.getSolvedQuestionsCount(getCondition(condition));
	}
	
	public boolean setAddContent(EcardServiceCondition condition){
		QuestionCondition condition2 = getCondition(condition);
		long qid = condition2.getQid()[0];
		String addContent = condition.getValue("addcontent");
		return questionServices.setAddContent(qid, addContent);
	}
	public boolean setQuestionOrder(EcardServiceCondition condition){
		QuestionCondition condition2 = getCondition(condition);
		long qid = condition2.getQid()[0];
		int order = StringValueUtils.getInt(condition.getValue("order"));
		return questionServices.setQuestionOrder(qid, order);
	}
	public boolean passQuestionsAddcontent(EcardServiceCondition condition){
		return questionServices.passQuestionsAddcontent(getCondition(condition).getQid());		
	}
	public boolean forbidQuestionAddcontent(EcardServiceCondition condition){
		return questionServices.forbidQuestionAddcontent(getCondition(condition).getQid());
	}
	public boolean addQuestionPv(EcardServiceCondition condition){
		return questionServices.addQuestionPv(condition.getLongValue("qid"));
	}
}