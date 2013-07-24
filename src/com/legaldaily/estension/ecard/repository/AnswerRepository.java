package com.legaldaily.estension.ecard.repository;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.AnswerCondition;
import com.legaldaily.estension.ecard.model.question.Answer;

public interface AnswerRepository {

	public List<Answer> getAllAnswers();
	public Answer getAnswer(long answerid);
	/**
	 * 保存不用审核的答案
	 * @param answer
	 * @return
	 */
	public int saveNoauditAnswer(Answer answer);
	/**
	 * 保存需要审核的答案
	 * @param answer
	 * @return
	 */
	public int saveAuditAnswer(Answer answer);
	public int removeAnswer(Answer answer);
	
	public List<Answer> getAnswers(AnswerCondition condition);
	public List<Answer> getAnswersByQuestion(long questionId);
	
	public boolean passAnswer(long[] answerid);
	
	public int setBestAnswer(Answer answer);
	public int saveAnswer(Answer answer);
	public boolean forbidAnswer(long[] aids);
	public int unsetBestAnswer(Answer answer);
	public List<Answer> getLawyerAnswers(int startpos, int count, boolean all);
	public int getLawyerAnswersCount(boolean all);
	public List<Answer> getNetfriendsAnswers(int startpos, int count, boolean all);
	public long getNetfriendsAnswersCount(boolean all);
	public long getAnswersCount(AnswerCondition condition);
	public Answer getBestAnswersByQuestion(long questionId); 
}
