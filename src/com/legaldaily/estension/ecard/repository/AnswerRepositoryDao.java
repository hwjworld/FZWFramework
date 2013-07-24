package com.legaldaily.estension.ecard.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.legaldaily.estension.ecard.Constants;
import com.legaldaily.estension.ecard.model.condition.AnswerCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.repository.dao.AnswerDao;
import com.legaldaily.estension.ecard.repository.dao.AnswerDaoSql;

public class AnswerRepositoryDao extends RepositoryDao implements AnswerRepository {

	
	private AnswerDao answerDao;
	public AnswerRepositoryDao() {
		answerDao = new AnswerDaoSql();
	}
	
	@Override
	public Answer getAnswer(long answerid) {
		Answer answer = null;
		if(!containKeyInCache(answerCache, answerid)){
			answer = answerDao.getAnswer(answerid);
			if (answer == null) {
				return null;
			}
			setAnswer(answer);
			storeAnswer(answer);
		}
		answer = (Answer) getValueFromCache(answerCache, answerid);
		return answer;
	}


	@Override
	public List<Answer> getAnswers(AnswerCondition condition) {
		List<Long> ids = answerDao.getAnswers(condition);
		return getAnswersFromIds(ids);
	}
	@Override
	public long getAnswersCount(AnswerCondition condition) {
		return answerDao.getAnswersCount(condition);
	}

	private int addAnswer(Answer answer) {
		int rv = 0;
		rv = answerDao.addAnswer(answer);
		if(rv > 0){
			answerDao.updateUserScore(answer);
			if(answerDao.isExistUserAnswerMeta(answer.getUser().getUid())){
				answerDao.increaseOneUserAnswerMeta(answer.getUser().getUid());
			}else {
				answerDao.insertUserAnswerMeta(answer.getUser().getUid());
			}
			answerDao.increaseOneReplyCount(answer.getQuestion().getQid());
			//store to answerCache
			getAnswer(rv);
		}
		return rv;
	}

	private int updateAnswer(Answer answer) {
		return answerDao.updateAnswer(answer);
	}

	@Override
	public int removeAnswer(Answer answer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int saveAnswer(Answer answer){
		int rv = 0;
		if(answer.getId() > 0 ){
			rv = updateAnswer(answer);
			questionRepository.refreshQuestionAnswers(getAnswer(answer.getId()).getQuestion().getQid());
		}else if(answer.getId() <= 0){
			rv = addAnswer(answer);
			questionRepository.refreshQuestionAnswers(answer.getQuestion().getQid());
		}
		return rv;
	}
	@Override
	public int saveAuditAnswer(Answer answer) {
		answer.setCheck_flag(1);
		return saveAnswer(answer);
	}
	
	@Override
	public int saveNoauditAnswer(Answer answer) {
		answer.setCheck_flag(0);
		int rv = saveAnswer(answer);
		return rv;
	}


	@Override
	public List<Answer> getAnswersByQuestion(long questionId) {
		List<Answer> answers = answerDao.getAnswersByQuestionId(questionId);
		for (Answer answer : answers) {
			setAnswer(answer);
		}
		return answers;
	}
	@Override
	public Answer getBestAnswersByQuestion(long questionId) {
		return answerDao.getBestAnswersByQuestionId(questionId);
	}

	@Override
	public int setBestAnswer(Answer answer) {
		int rv = answerDao.setBestAnswerScore(answer);
		rv += answerDao.setBestAnswerUserScore(answer);
		rv += answerDao.setBestAnswerSatisfy(answer);
//		return questionRepository.setBestAnswer(answer);
		return rv;
	}

	@Override
	public List<Answer> getAllAnswers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean passAnswer(long[] answerid) {
		int rv = answerDao.passAnswer(answerid);
		for (long l : answerid) {
			if(getAnswer(l).getUser().getUserGroup().getGroupid()== Constants.DEFAULT_NETFRIEND){
				answerDao.increaseOneReplyCount(l);
			}
		}
		return rv>0?true:false;
	}
	
	@Override
	public boolean forbidAnswer(long[] answerid) {
		int rv = answerDao.forbitAnswer(answerid);
		for (long l : answerid) {
			if(getAnswer(l).getUser().getUserGroup().getGroupid() != Constants.DEFAULT_NETFRIEND){
				answerDao.decreaseOneReplyCount(l);
			}
		}
		return rv>0?true:false;
	
	}

	@Override
	public int unsetBestAnswer(Answer answer) {
		int rv = answerDao.unsetBestAnswerSatisfy(answer);
		rv += answerDao.unsetBestAnswerScore(answer);
		rv += answerDao.unsetBestAnswerUserScore(answer);
		return rv;
	}
	
	public List<Answer> getAnswersFromIds(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids))
			return null;
		List<Answer> answers = new ArrayList<Answer>(ids.size());
		for (Long long1 : ids) {
			answers.add(getAnswer(long1));
		}
		return answers;
	}

	@Override
	public List<Answer> getLawyerAnswers(int startpos, int count, boolean all) {
		List<Long> list = answerDao.getLawyerAnswers(startpos,count,all);
		return getAnswersFromIds(list);
	}

	@Override
	public int getLawyerAnswersCount(boolean all) {
		return answerDao.getLawyerAnswersCount(all);
	}

	@Override
	public List<Answer> getNetfriendsAnswers(int startpos, int count, boolean all) {
		List<Long> list = answerDao.getNetfriendsAnswers(startpos,count,all);
		return getAnswersFromIds(list);
	}

	@Override
	public long getNetfriendsAnswersCount(boolean all) {
		return answerDao.getNetfriendsAnswersCount(all);
	}

}
