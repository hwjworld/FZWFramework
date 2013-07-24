package com.legaldaily.estension.ecard.service.question;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.service.EcardService;

public interface QuestionServices extends EcardService {

	public Question getQuestion(long questionId);
	public int addQuestion(Question question);
	public int updateQuestion(Question question);
	public int delQuestion(long[] questionId);
	public int delQuestion(Question question);
	
	// business services below
	// 已解决问题列表
	// select a.q_id,a.u_id,DATE_FORMAT(a.a_time,'%m月%d日') as
	// an_time,q.q_title,u.nick_name,u.u_group from answers a,questions q,users
	// u where a.a_stats='1' and a.u_id=u.u_id and a.q_id=q.q_id and
	// q.q_stats='1' and q.q_lock='1' and a.a_victory='1' group by q_id order by
	// q.in_turn desc, a.a_time desc limit 6
//	public List<Question> getSolvedQuestions(int startpos, int count, int provinceid);
	public List<Question> getSolvedQuestions(QuestionCondition condition);
	public long getSolvedQuestionsCount(QuestionCondition condition);
	
	// 待解答问题列表
	// "q_id,q_title,DATE_FORMAT(q_time,'%m月%d日') as q_time,q_value,g_id"
	// ,5,18,0,true,false,"q_id","desc",0,"q_lock='0'"
//	public List<Question> getUnSolvedQuestions(int startpos, int count, int provinceid);
//	public List<Question> getUnSolvedQuestions(QuestionCondition condition);

	// 0解答问题列表
	// "q_id,q_title,q_time,q_value,g_id",5,18,0,true,false,"q_id","desc",0,"rep_values=0"
//	public List<Question> getNoneAnswerQuestions(int startpos, int count, int provinceid);
//	public List<Question> getNoneAnswerQuestions(QuestionCondition condition);

	// 高悬赏问题列表
	// "q_id,q_title,q_time,q_value,g_id",5,18,0,true,false,"q_value","desc",0,"q_lock='0' and q_value>0"
//	public List<Question> getHighScoreQuestions(int startpos, int count, int provinceid);
//	public List<Question> getHighScoreQuestions(QuestionCondition condition);

	public List<Question> getQuestions(QuestionCondition condition);
	
	/**
	 * 判断是否已锁定，已经有最佳答案
	 * @param question
	 * @return
	 */
	public boolean isLocked(Question question);
	
	public boolean passQuestions(long qid[]);

	/**
	 * 设置最佳答案
	 * @param answer
	 * @return
	 */
	public boolean setBestAnswer(Answer answer);
	/**
	 * 取消最佳答案
	 * @param answer
	 * @return
	 */
	public boolean unsetBestAnswer(Question question);
	
	public List<Question> getNetFriendsQuestions(QuestionCondition condition);
	public List<Question> getMemberQuestions(QuestionCondition condition);
	public List<Question> getAddContentQuestions(QuestionCondition condition);
	public int getMemberQuestionsCount(boolean all);
	public int getNetFriendsQuestionsCount(boolean all);
	public int getAddContentQuestionsCount(boolean all);
	
	public List<Question> getLatestSolvedQuestionByGroup(QuestionCondition condition);
	public List<Question> getLatestSolvedQuestion(int count);
	public long getQuestionsCount(QuestionCondition condition);
	public long getHighScoreQuestionsCount(QuestionCondition condition);
	public boolean setAddContent(long qid, String addContent);
	public boolean questionOperate(long qid, String operate);
	public boolean setQuestionOrder(long qid, int order);
	public boolean forbidQuestions(long[] qid);
	public boolean passQuestionsAddcontent(long[] qid);
	public boolean forbidQuestionAddcontent(long[] qid);
	public boolean addQuestionPv(long qid);
}
