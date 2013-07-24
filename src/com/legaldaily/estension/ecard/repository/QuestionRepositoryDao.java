package com.legaldaily.estension.ecard.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.ehcache.Element;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.cache.EcardCacheManager;
import com.legaldaily.estension.ecard.cache.LatestSolvedQuestionIndex;
import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.condition.Cond;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.recommend.RecommendTransient;
import com.legaldaily.estension.ecard.model.recommend.Recommendation;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.repository.dao.QuestionDao;
import com.legaldaily.estension.ecard.repository.dao.QuestionDaoSql;
import com.legaldaily.estension.ecard.utils.Comparators;

public class QuestionRepositoryDao extends RepositoryDao implements
		QuestionRepository {

	private QuestionDao questionDao;

	public QuestionRepositoryDao() {
		this.questionDao = new QuestionDaoSql();
	}

	@Override
	public Question getQuestion(long qid) {
		Question question = null;
		if (!containKeyInCache(questionCache, qid)) {
			question = questionDao.getQuestion(qid);
			if (question == null) {
				return null;
			}
			setQuestion(question);
			setQuestionUser(question);
			setQuestionArea(question);
			storeQuestion(question);
		}
		question = (Question) getValueFromCache(questionCache, qid);
		return question;
	}

	@Override
	public List<Recommendation> listQARecommend(Cond cond) {
		// List<RecommendTransient> list = recommendCache.getKeys();
		List<RecommendTransient> list = null;
		List<Recommendation> recommendList = new ArrayList<Recommendation>();
		if (list == null || list.size() == 0) {
			list = questionDao.listRecommendTransients(cond);
		}
		for (RecommendTransient rt : list) {
			Recommendation recommend = null;

			if (!recommendCache.isKeyInCache(rt.getQid())) {
				recommend = new Recommendation();
				Question question = getQuestion(rt.getQid());
				recommend.setQuestion(question);
				User user = userRepository.getUser(rt.getUserid());
				recommend.setReferees(user);
				recommend.setOrder(rt.getOrder());
				recommend.setRecommendTime(rt.getRecommendTime());
				recommendCache.put(new Element(rt.getQid(), recommend));
			} else {
				recommend = (Recommendation) recommendCache.get(rt.getQid())
						.getValue();
			}
			recommendList.add(recommend);
		}
		return recommendList;
	}

	@Override
	public List<Question> listWaitQARecommend(Cond cond) {
		// List<Long> list = waitRecommendCache.getKeys();
		List<Long> list = null;
		List<Question> waitRecommendList = new ArrayList<Question>();
		// if (list == null || list.size() == 0) {
		list = questionDao.listWaitQARecommendIDs(cond);
		// }else{
		//
		// }
		for (Long qidLong : list) {
			long qid = qidLong.longValue();
			if (recommendCache.isKeyInCache(qid)) {
				continue;
			}
			Question question = getQuestion(qid);
			if ("1".equals(question.getSatisfied())
					&& question.getBestAnswer() != null
					&& "1".equals(question.getVisible())) {

				// waitRecommendCache.put(new Element(qid, question));
				waitRecommendList.add(question);
			}

			if (waitRecommendList.size() == cond.getCount()) {
				break;
			}
		}
		return waitRecommendList;
	}

	@Override
	public int removeQuestion(Question question) {
		removeFromCache(questionCache, question.getQid());
		return questionDao.deleteQuestion(question.getQid());
	}

	@Override
	public int removeQuestion(long questionId) {
		if (getQuestion(questionId) == null)
			return 0;
		return removeQuestion(getQuestion(questionId));
	}

	@Override
	public int saveQuestion(Question question) {
		int rv = 0;
		if (question.getQid() <= 0 && !StringUtils.isBlank(question.getTitle())) {
			rv = addQuestion(question);
			LatestSolvedQuestionIndex.updateLatestSolvedQuestion(getQuestion(rv));
		} else if (question.getQid() > 0
				&& !StringUtils.isBlank(question.getTitle())) {
			rv = updateQuestion(question);
		}
		if (rv > 0 && question.getQid() > 0) {
			removeFromCache(questionCache, question.getQid());
			getQuestion(question.getQid());
		}
		return rv;
	}

	private int updateQuestion(Question question) {
		return questionDao.updateQuestion(question);
	}

	private int addQuestion(Question question) {
		int rv = questionDao.insertQuestion(question);
		if (rv > 0) {
			if (questionDao
					.isExistUserQuestionMeta(question.getUser().getUid())) {
				updateUserQuestionMeta(question);
			} else {
				insertNewUserQuestionMeta(question);
			}
		}
		return rv;
	}

	private void insertNewUserQuestionMeta(Question question) {
		questionDao.insertUserQuestionMeta(question.getUser().getUid());
	}

	private void updateUserQuestionMeta(Question question) {
		questionDao.updateUserQuestionMeta(question.getUser().getUid());
	}

	@Override
	public void addRecommend(Recommendation recommendation) {
		if (recommendation == null)
			return;
		// 必须保证缓存数据的完整性
		if (!recommendCache.isKeyInCache(recommendation.getQuestion().getQid())) {
			questionDao.insertRecommendation(recommendation);
			buildRecommendation(recommendation.getQuestion().getQid());
		}
	}

	@Override
	public void removeRecommend(Recommendation recommendation) {
		if (recommendation == null)
			return;
		EcardCacheManager.removeFromCache(
				recommendation.getQuestion().getQid(), recommendCache);
		questionDao.deleteRecommendation(recommendation);
	}

	// 此方法返回的Recommendation不记入缓存
	@Override
	public Recommendation buildRecommendation(RecommendTransient rt) {
		if (rt == null) {
			return null;
		}
		Recommendation recommendation = new Recommendation();

		Question question = getQuestion(rt.getQid());
		recommendation.setQuestion(question);
		recommendation.setRecommendTime(rt.getRecommendTime());
		recommendation.setOrder(rt.getOrder());
		recommendation.setReferees(userRepository.getUser(rt.getUserid()));
		return recommendation;
	}

	@Override
	public void moveRecommendPosition(Recommendation recommendation1,
			Recommendation recommendation2) {
		questionDao
				.moveRecommendationPosition(recommendation1, recommendation2);
		List<Long> keyList = recommendCache.getKeys();
		int max = Math.max(recommendation1.getOrder(),
				recommendation2.getOrder());
		int min = Math.min(recommendation1.getOrder(),
				recommendation2.getOrder());
		for (Long qid : keyList) {
			Element element = recommendCache.get(qid);
			Recommendation recommendation = null;
			if (element != null
					&& (recommendation = (Recommendation) element.getValue()) != null
					&& recommendation.getOrder() >= min
					&& recommendation.getOrder() <= max) {
				buildRecommendation(qid);
			}
		}
	}

	// 此方法返回在的Recommendation将记入缓存
	@Override
	public Recommendation buildRecommendation(long qid) {
		EcardCacheManager.removeFromCache(qid, recommendCache);
		return getRecommendation(qid);
	}

	// 此方法返回在的Recommendation将记入缓存
	@Override
	public Recommendation getRecommendation(long qid) {
		Element elem = recommendCache.get(qid);
		Recommendation recommend = null;
		if (elem == null) {
			RecommendTransient rt = questionDao.selectRecommendTransient(qid);
			if (rt == null) {
				return null;
			}
			recommend = buildRecommendation(rt);
			EcardCacheManager.putToCache(qid, recommend, recommendCache);
		} else {
			recommend = (Recommendation) elem.getValue();
		}
		return recommend;
	}

	@Override
	public List<Question> getVisibleAndSatisfyQuestions(int offset, int count,
			int provinceid) {
		List<Long> q_ids = questionDao.getVisibleAndSatisfyQuestions(offset,
				count, provinceid);
		return getQuestionsFromIds(q_ids);
	}

	@Override
	public List<Question> getVisibleAndSatisfyQuestions(
			QuestionCondition condition) {
		List<Long> q_ids = questionDao.getVisibleAndSatisfyQuestions(condition);
		return getQuestionsFromIds(q_ids);
	}

	@Override
	public List<Question> getVisibleQuestions(int offset, int count,
			int provinceid) {
		List<Long> q_ids = questionDao.getVisibleQuestions(offset, count,
				provinceid);
		return getQuestionsFromIds(q_ids);
	}

	@Override
	public List<Question> getVisibleQuestions(QuestionCondition condition) {
		List<Long> q_ids = questionDao.getVisibleQuestions(condition);
		return getQuestionsFromIds(q_ids);
	}

	@Override
	public List<Question> getQuestions(QuestionCondition condition) {

		List<Long> q_ids = questionDao.getQuestions(condition);
		return getQuestionsFromIds(q_ids);
	}

	@Override
	public long getQuestionsCount(QuestionCondition condition) {
		// return getCount(condition);
		return questionDao.getQuestionsCount(condition);
	}

	@Override
	public void refreshQuestionAnswers(long questionId) {
		Question question = getQuestion(questionId);
		if(question == null)
			return;
		refreshAnswers(question);
		refreshBestAnswer(question);
		storeAnswerFromQuestion(question);
	}

	private void refreshBestAnswer(Question question) {
		if(question.getBestAnswer() != null){
			removeFromCache(answerCache, question.getBestAnswer().getId());
			question.setBestAnswer(null);
		}
		Answer answer = answerRepository.getBestAnswersByQuestion(question.getQid());
		question.setBestAnswer(answer);
	}

	private void refreshAnswers(Question question) {
		List<Answer> answers = question.getAnswers();
		if(CollectionUtils.isNotEmpty(answers)){
			for (int i = answers.size()-1; i >=0; i--) {
				Answer answer = answers.get(i);
				removeFromCache(answerCache, answer.getId());
				answers.remove(answer);
			}
		}
		answers = answerRepository.getAnswersByQuestion(question.getQid());
		for (Answer answer : answers) {
			question.addAnswer(answer);
		}
	}

	@Override
	public boolean passQuestion(long qid) {
		return questionDao.passQuestion(qid) > 0 ? true : false;
	}

	@Override
	public int setBestAnswer(Answer answer) {
		answer = answerRepository.getAnswer(answer.getId());
		answer.getQuestion().setBestAnswer(answer);
		questionDao.setBestUpdateUserScore(answer);
		return questionDao.setBestAnswer(answer);
	}

	@Override
	public int unsetBestAnswer(Question question) {
		question = getQuestion(question.getQid());
		question.setBestAnswer(null);
		questionDao.unsetBestUpdateUserScore(question);
		return questionDao.unsetBestAnswer(question);
	}

	@Override
	public List<Question> getLatestSolvedQuestionByGroup(
			QuestionCondition condition) {
		List<Long> q_ids = questionDao
				.getLatestSolvedQuestionByGroup(condition);
		return getQuestionsFromIds(q_ids);
	}

	private List<Question> getQuestionsFromIds(List<Long> ids) {
		if (CollectionUtils.isEmpty(ids))
			return null;
		List<Question> questions = new ArrayList<Question>(ids.size());
		for (Long long1 : ids) {
			questions.add(getQuestion(long1.longValue()));
		}
		return questions;
	}

	@Override
	public int getMemberQuestionsCount( boolean all) {
		return questionDao.getMemberQuestionsCount(all);
	}

	@Override
	public int getNetFriendsQuestionsCount( boolean all) {
		return questionDao.getNetFriendsQuestionsCount(all);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getAllQuestions() {
		List<? extends EcardModel> list = getAllCacheValues(questionCache);
		if (CollectionUtils.isEmpty(list)) {
			List<Question> questions = questionDao.selectAllQuestions();
			for (Question question : questions) {
				setQuestion(question);
				storeQuestion(question);
			}
		} else if (list.size() != questionDao.selectAllQuestionsCount()) {
			List<Serializable> keys = questionCache.getKeys();
			Object maxid = Collections.max(keys,
					Comparators.STRING_COMPARATOR_DESC_INTEGER);
			List<Long> ids = questionDao
					.selectQuestionLargerIds(StringValueUtils.getLong(maxid
							.toString()));
			for (Long id : ids) {
				if (!containKeyInCache(questionCache, id)) {
					getQuestion(id);
				}
			}
		}
		list = getAllCacheValues(questionCache);
		return (List<Question>) list;
	}

	@Override
	public long getSolvedQuestionsCount(QuestionCondition condition) {
		return questionDao.getSolvedQuestionsCount(condition);
	}

	@Override
	public boolean setAddContent(long qid, String addContent) {

		Question question = questionRepository.getQuestion(qid);
		if (question == null) {
			return false;
		}
		question.setAddContent(addContent);
		return questionDao.setAddContent(qid, addContent) > 0 ? true : false;
	}

	@Override
	public boolean questionOperate(long qid, String operate) {
		boolean rv = false;
		Question question = getQuestion(qid);
		if (question != null) {
			// TODO ..留着扩展
		}
		return rv;
	}

	@Override
	public boolean setQuestionOrder(long qid, int order) {
		Question question = getQuestion(qid);
		if (question == null)
			return false;
		question.setOrder(order);
		return questionDao.setQuestionOrder(qid, order) > 0 ? true : false;
	}

	@Override
	public List<Question> getMemberQuestions(int startpos, int count, boolean all) {
		List<Long> list = questionDao.getMemberQuestions(startpos, count, all);
		return getQuestionsFromIds(list);
	}

	@Override
	public List<Question> getNetFriendsQuestions(int startpos, int count, boolean all) {
		List<Long> list = questionDao.getNetFriendsQuestions(startpos, count,all);
		return getQuestionsFromIds(list);
	}

	@Override
	public boolean deleteQuestion(long qid) {
		return questionDao.deleteQuestion(qid) > 0 ? true : false;
	}

	@Override
	public List<Question> getAddContentQuestions(int startpos, int count,
			boolean all) {
		List<Long> list = questionDao.getAddContentQuestions(startpos, count, all);
		return getQuestionsFromIds(list);
	}

	@Override
	public int getAddContentQuestionsCount(boolean all) {
		return questionDao.getAddContentQuestionsCount(all);
	}

	@Override
	public boolean passQuestionsAddcontent(long qid) {
		return questionDao.passQuestionsAddcontent(qid) > 0 ? true : false;
	}

	@Override
	public boolean forbidQuestionAddcontent(long qid) {
		return questionDao.forbidQuestionAddcontent(qid) > 0 ? true : false;
	}

	@Override
	public boolean addQuestionPv(long qid) {
		return questionDao.addQuestionPv(qid)>0?true:false;
	}
}