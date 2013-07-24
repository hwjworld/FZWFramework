package com.legaldaily.estension.ecard.service.question;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.fzw.model.ConnectionMessage;
import com.fzw.service.ServiceConvey;
import com.fzw.utils.CodeUtil;
import com.fzw.utils.DateUtil;
import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.model.condition.AnswerCondition;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.repository.AnswerRepository;

public class AnswerServicesConvey extends ServiceConvey<AnswerCondition> {

	AnswerServices answerServices = null;

	public AnswerServicesConvey(AnswerRepository answerRepository) {
		answerServices = new AnswerServicesImpl(answerRepository);
	}

	public List<Answer> getAnswer(EcardServiceCondition condition) {
		return answerServices.getAnswers(getCondition(condition));
	}
	
	public List<Answer> getAnswers(EcardServiceCondition condition) {
		return answerServices.getAnswers(getCondition(condition));
	}
	public long getAnswersCount(EcardServiceCondition condition){
		return answerServices.getAnswersCount(getCondition(condition));
	}
	// insert into answers(q_id,u_id,au_ip,a_content,a_time,get_score,a_stats)
	// values('$qid',$current_userid,'$u_ip','$a_content',now(),$defalt_score,'$a_stats')
	public boolean addAnswer(EcardServiceCondition condition) {

//		String answerInfo = CodeUtil.decodeBase64(getCondition(condition)
//				.getContent());
		String values[] = ConnectionMessage.getValue(condition.getMessage(),"q_id",
				"u_id", "au_ip", "a_content");
		Answer answer = new Answer();
		answer.getQuestion().setQid(StringValueUtils.getLong(values[0]));
		answer.getUser().setUid(StringValueUtils.getInt(values[1]));
		answer.setIp(values[2]);
		answer.setContent(values[3]);
		answer.setAnswer_time(new Date());

		return answerServices.addAnswer(answer) > 0 ? true : false;

	}

	// update answers set a_content='$a_content',check_flag='$check_flag' where
	// a_id=$aid"
	public boolean updateAnswer(EcardServiceCondition condition) {

		String answerInfo = CodeUtil.decodeBase64(getCondition(condition)
				.getContent());
		String values[] = ConnectionMessage.getValue(answerInfo, "a_content",
				"check_flag", "a_id");
		Answer answer = new Answer();
		answer.setContent(values[0]);
		answer.setCheck_flag(StringValueUtils.getInt(values[1]));
		answer.setId(StringValueUtils.getLong(values[2]));

		return answerServices.updateAnswer(answer) > 0 ? true : false;

	}

	public boolean deleteAnswer(EcardServiceCondition condition) {
		return false;
	}

	public boolean passAnswer(EcardServiceCondition condition) {
		return answerServices.passAnswer(getCondition(condition).getAnswerId());
	}

	public boolean forbidAnswer(EcardServiceCondition condition) {
		return answerServices.forbidAnswer(getCondition(condition)
				.getAnswerId());
	}

	public boolean setBestAnswer(EcardServiceCondition condition) {
		Answer answer = answerServices.getAnswer(getCondition(condition)
				.getAnswerId()[0]);
		return answerServices.setBestAnswer(answer);
	}

	public boolean unsetBestAnswer(EcardServiceCondition condition) {
		Answer answer = answerServices.getAnswer(getCondition(condition)
				.getAnswerId()[0]);
		return answerServices.unsetBestAnswer(answer);
	}
	
	public List<Answer> getLatestLawyerAnswers(EcardServiceCondition condition) {
		return answerServices.getLatestLawyerAnswers(getCondition(condition));
	}

	public int getLatestLawyerAnswersCount(EcardServiceCondition condition) {
		return answerServices
				.getLatestLawyerAnswersCount(getCondition(condition));
	}

	public List<Answer> getLatestNetfriendsAnswers(
			EcardServiceCondition condition) {
		return answerServices.getLatestNetfriendsAnswers(getCondition(condition));
	}

	public long getLatestNetfriendsAnswersCount(EcardServiceCondition condition) {
		return answerServices
				.getLatestNetfriendsAnswersCount(getCondition(condition));
	}

	public boolean editAnswer(EcardServiceCondition condition) {
		Answer answer = new Answer();
		answer.setContent(condition.getValue("content"));
		answer.setCheck_flag(condition.getIntValue("check"));
		answer.setId(condition.getLongValue("answerid"));
		return answerServices.editAnswer(answer);
	}
	public List<Answer> getAnswersByQuestionid(EcardServiceCondition condition){
		long qid = condition.getLongValue("questionid");
		int startpos = condition.getIntValue("startpos");
		int count = condition.getIntValue("count");
		return answerServices.getAnswersByQuestionid(qid,startpos,count);
	}
	public int getAnswersByQuestionidCount(EcardServiceCondition condition){
		long qid = condition.getLongValue("questionid");
		return answerServices.getAnswersByQuestionidCount(qid);
	}

}
