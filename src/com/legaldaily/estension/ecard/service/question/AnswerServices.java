package com.legaldaily.estension.ecard.service.question;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.AnswerCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.service.EcardService;

public interface AnswerServices extends EcardService {
	public Answer getAnswer(long answerId);
	public List<Answer> getAnswers(AnswerCondition condition);
	/**
	 * 新增一个答案，回答者得到相应积分，更新问题的回答数rep_value,更新用户的回答数answer_cnt
	 * @return
	 */
	public int addAnswer(Answer answer);
	/**
	 * 修改答案
	 * @param answer
	 * @return
	 */
	public int updateAnswer(Answer answer);
	/**
	 * 删除答案
	 * @param answerId
	 * @return
	 */
	public int deleteAnswer(int answerId);
	/**
	 * 审核通过答案
	 * @param answer
	 * @return
	 */
	public boolean passAnswer(long[] aids);
	/**
	 * 审核禁止答案
	 * @param answer
	 * @return
	 */
	public boolean forbidAnswer(long[] aids);

	public boolean setBestAnswer(Answer answer);
	public boolean unsetBestAnswer(Answer answer);
	
	public List<Answer> getLatestLawyerAnswers(AnswerCondition condition);
	public int getLatestLawyerAnswersCount(AnswerCondition condition);

	public List<Answer> getLatestNetfriendsAnswers(AnswerCondition condition);
	public long getLatestNetfriendsAnswersCount(AnswerCondition condition);

	public boolean editAnswer(Answer answer);
	public long getAnswersCount(AnswerCondition condition);
	public List<Answer> getAnswersByQuestionid(long qid, int startpos, int count);
	public int getAnswersByQuestionidCount(long qid);
}
