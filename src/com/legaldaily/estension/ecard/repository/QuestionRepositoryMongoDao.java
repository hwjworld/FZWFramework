package com.legaldaily.estension.ecard.repository;

import java.util.List;

import com.legaldaily.estension.ecard.cache.MongoDBCacheManager;
import com.legaldaily.estension.ecard.model.condition.Cond;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.recommend.RecommendTransient;
import com.legaldaily.estension.ecard.model.recommend.Recommendation;
import com.legaldaily.estension.ecard.repository.dao.QuestionDao;
import com.legaldaily.estension.ecard.repository.dao.QuestionDaoSql;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;

public class QuestionRepositoryMongoDao extends RepositoryDao implements QuestionRepository{

	private QuestionDao questionDao;
	DB db = MongoDBCacheManager.getMongo().getDB("ecard");

	public QuestionRepositoryMongoDao() {
		this.questionDao = new QuestionDaoSql();
	}

	@Override
	public Question getQuestion(long qid) {
		BasicDBObject object = new BasicDBObject();
		Question question = null;
		object.put("qid", qid);
		if(db.getCollection("question").findOne(object) == null){
			question = questionDao.getQuestion(qid);
			if (question == null) {
				return null;
			}
			setQuestion(question);
			setQuestionUser(question);
			setQuestionArea(question);
			
			
		}
		return (Question) db.getCollection("question").findOne(object);
	}

	@Override
	public List<Recommendation> listQARecommend(Cond cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> listWaitQARecommend(Cond cond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Recommendation getRecommendation(long qid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveQuestion(Question question) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeQuestion(Question question) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeQuestion(long questionId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addRecommend(Recommendation recommendation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeRecommend(Recommendation recommendation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRecommendPosition(Recommendation recommendation1, Recommendation recommendation2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Recommendation buildRecommendation(RecommendTransient rt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Recommendation buildRecommendation(long qid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getVisibleAndSatisfyQuestions(int offset, int count, int provinceid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getVisibleAndSatisfyQuestions(QuestionCondition condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getVisibleQuestions(int offset, int count, int provinceid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getVisibleQuestions(QuestionCondition condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getQuestions(QuestionCondition condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshQuestionAnswers(long questionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean passQuestion(long qid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int setBestAnswer(Answer answer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int unsetBestAnswer(Question question) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Question> getLatestSolvedQuestionByGroup(QuestionCondition condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMemberQuestionsCount(boolean all) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNetFriendsQuestionsCount(boolean all) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Question> getAllQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getQuestionsCount(QuestionCondition condition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getSolvedQuestionsCount(QuestionCondition condition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setAddContent(long qid, String addContent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean questionOperate(long qid, String operate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setQuestionOrder(long qid, int order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Question> getMemberQuestions(int startpos, int count, boolean all) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> getNetFriendsQuestions(int startpos, int count, boolean all) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteQuestion(long qid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Question> getAddContentQuestions(int startpos, int count, boolean boolean1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAddContentQuestionsCount(boolean all) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean passQuestionsAddcontent(long qid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean forbidQuestionAddcontent(long qid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addQuestionPv(long qid) {
		// TODO Auto-generated method stub
		return false;
	}

}
