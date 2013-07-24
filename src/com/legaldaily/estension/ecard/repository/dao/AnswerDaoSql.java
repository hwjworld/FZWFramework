package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.AnswerCondition;
import com.legaldaily.estension.ecard.model.question.Answer;

public class AnswerDaoSql extends EcardDao implements AnswerDao {

	@Override
	public List<Long> getAnswers(AnswerCondition condition) {
		return (List<Long>) selectList("selectAnswerByCondition", condition);
	}

	@Override
	public long getAnswersCount(AnswerCondition condition) {
		return (Long) selectOne("selectAnswerByConditionCount",condition);
	}
	@Override
	public Answer getAnswer(long answerid) {
		return (Answer) selectOne("selectAnswerInfo",answerid);
	}

	@Override
	public int updateAnswer(Answer answer) {
		return update("updateAnswer", answer);
	}

	@Override
	public int addAnswer(Answer answer) {
		return insert("insertAnswer", answer);
	}

	@Override
	public int updateUserScore(Answer answer) {
		return update("updateUserScore",answer);
	}

	@Override
	public boolean isExistUserAnswerMeta(int userId) {
		return QUESTION_DAO.isExistUserQuestionMeta(userId);
	}

	@Override
	public int increaseOneUserAnswerMeta(int userId) {
		return update("increastOneUserAnswerMeta", userId);
	}

	@Override
	public int insertUserAnswerMeta(int userId) {
		return insert("insertNewUserAnswerMeta", userId);
	}

	@Override
	public List<Answer> getAnswersByQuestionId(long questionId) {
		return (List<Answer>) selectList("selectAnswers",questionId);
	}
	@Override
	public Answer getBestAnswersByQuestionId(long questionId) {
		return (Answer) selectOne("selectBestAnswer",questionId);
	}

	@Override
	public int increaseOneReplyCount(long questionId) {
		return update("updateReplyCount",questionId);
	}

	@Override
	public int passAnswer(long []answerId) {
		return update("passAnswer",answerId);
	}

	@Override
	public int forbitAnswer(long[] aids) {
		return update("forbitAnswer",aids);
	}

	@Override
	public int decreaseOneReplyCount(long questionId) {
		return update("decreaseReplyCount",questionId);
	}

	@Override
	public int setBestAnswerScore(Answer answer) {
		return update("setBestAnswerScore",answer);
	}

	@Override
	public int setBestAnswerUserScore(Answer answer) {
		return update("setBestAnswerUserScore",answer);
	}

	@Override
	public int setBestAnswerSatisfy(Answer answer) {
		return update("setBestAnswerSatisfy",answer);
	}

	@Override
	public int unsetBestAnswerSatisfy(Answer answer) {
		return update("unsetBestAnswerSatisfy",answer);
	}

	@Override
	public int unsetBestAnswerScore(Answer answer) {
		return update("unsetBestAnswerScore",answer);
	}

	@Override
	public int unsetBestAnswerUserScore(Answer answer) {
		return update("unsetBestAnswerUserScore",answer);
	}

	@Override
	public List<Long> getLawyerAnswers(int startpos, int count, boolean all) {
		return (List<Long>) selectList("selectLawyerAnswers",genMabatisMapValues(startpos,count,all));
	}

	@Override
	public int getLawyerAnswersCount(boolean all) {
		return (Integer) selectOne("selectLawyerAnswersCount",genMabatisMapValues(all));
	}

	@Override
	public List<Long> getNetfriendsAnswers(int startpos, int count, boolean all) {
		return (List<Long>) selectList("selectNetfriendsAnswers",genMabatisMapValues(startpos,count,all));
	}

	@Override
	public long getNetfriendsAnswersCount(boolean all) {
		return (Long) selectOne("selectNetfriendsAnswersCount",genMabatisMapValues(all));
	}

}
