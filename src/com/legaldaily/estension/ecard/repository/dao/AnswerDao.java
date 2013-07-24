package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.AnswerCondition;
import com.legaldaily.estension.ecard.model.question.Answer;

public interface AnswerDao {
	public List<Long> getAnswers(AnswerCondition condition);
	public Answer getAnswer(long answerid);
	public List<Answer> getAnswersByQuestionId(long questionId);
	
	public int updateAnswer(Answer answer);
	/**
	 * 返回新增answerid
	 * @param answer
	 * @return
	 */
	public int addAnswer(Answer answer);
	/**
	 * 给回答用户增加积分
	 * @param answer
	 * @return
	 */
	public int updateUserScore(Answer answer);
	public boolean isExistUserAnswerMeta(int userId);
	public int increaseOneUserAnswerMeta(int userId);
	public int insertUserAnswerMeta(int userId);
	public int increaseOneReplyCount(long questionId);
	public int decreaseOneReplyCount(long l);
	public int passAnswer(long[] answerId);
	public int forbitAnswer(long[] aids);
	public int setBestAnswerScore(Answer answer);
	public int setBestAnswerUserScore(Answer answer);
	public int setBestAnswerSatisfy(Answer answer);
	public int unsetBestAnswerSatisfy(Answer answer);
	public int unsetBestAnswerScore(Answer answer);
	public int unsetBestAnswerUserScore(Answer answer);
	public List<Long> getLawyerAnswers(int startpos, int count, boolean all);
	public int getLawyerAnswersCount(boolean all);
	public List<Long> getNetfriendsAnswers(int startpos, int count, boolean all);
	public long getNetfriendsAnswersCount(boolean all);
	public long getAnswersCount(AnswerCondition condition);
	public Answer getBestAnswersByQuestionId(long questionId);
}
