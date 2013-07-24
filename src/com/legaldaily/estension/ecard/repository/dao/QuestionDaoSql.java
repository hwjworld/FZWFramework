package com.legaldaily.estension.ecard.repository.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.legaldaily.estension.ecard.model.condition.Cond;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.recommend.RecommendTransient;
import com.legaldaily.estension.ecard.model.recommend.Recommendation;

@Repository("questiondao")
public class QuestionDaoSql extends EcardDao implements QuestionDao {

	@Override
	public Question getQuestion(long qid) {
		Question question = (Question) selectOne("selectQuestionInfo", qid);
		if (question == null)
			return question;
		setQuestionToAnswer(question);
		return question;
	}

	private void setQuestionToAnswer(Question question) {
		if (question.getBestAnswer() != null) {
			// 加此判断是因为mybatis里不管有没有(原因为u_id)，都会加入一个空的answer
			if (question.getBestAnswer().getId() <= 0) {
				question.setBestAnswer(null);
			}
			question.getBestAnswer().setQuestion(question);
		}
		if (question.getAnswers() != null || question.getAnswers().size() > 0) {
			List<Answer> answers = question.getAnswers();
			for (int i = answers.size() - 1; i >= 0; i--) {
				Answer answer = answers.get(i);
				if (answer.getId() <= 0) {
					question.getAnswers().remove(answer);
				} else {
					answer.setQuestion(question);
				}
			}
		}
	}

	@Override
	public List<RecommendTransient> listRecommendTransients(Cond cond) {
		return (List<RecommendTransient>) selectList(
				"selectRecommendTransients", cond);
	}

	// @Override
	// public List<Long> listQARecommendIDs() {
	// List<Long> list = selectList("selectRecommendQuestionIDs");
	// return list;
	// }

	@Override
	public List<Long> listWaitQARecommendIDs(Cond cond) {
		return (List<Long>) selectList("selectWaitRecommendQuestionIDs", cond);
	}


	@Override
	public void insertRecommendation(Recommendation recommendation) {
		insert("insertRecommendation", recommendation);
	}

	@Override
	public void deleteRecommendation(Recommendation recommendation) {
		delete("deleteRecommendation", recommendation);
	}

	@Override
	public void moveRecommendationPosition(Recommendation recommendation1,
			Recommendation recommendation2) {
		HashMap<String, Recommendation> map = new HashMap<String, Recommendation>();
		map.put("r1", recommendation1);
		map.put("r2", recommendation2);
		if (recommendation1.getOrder() < recommendation2.getOrder()) {
			update("updateMovePositionStep0", map);
			update("updateMovePositionStep11", map);
			update("updateMovePositionStep2", map);
			// update q_tuijian set order_by=order_by-1 where order_by>'$order1'
			// and order_by<='$order2' order by order_by
		} else if (recommendation1.getOrder() > recommendation2.getOrder()) {
			update("updateMovePositionStep0", map);
			update("updateMovePositionStep12", map);
			update("updateMovePositionStep2", map);
			// update q_tuijian set order_by=order_by+1 where order_by<'$order1'
			// and order_by>='$order2' order by order_by desc
		}
		// update q_tuijian set order_by='$order2' where order_by=0

		return;
	}

	@Override
	public RecommendTransient selectRecommendTransient(long qid) {

		return (RecommendTransient) selectOne("selectOneRecommendTransient",
				qid);
	}

	@Override
	public List<Long> getVisibleAndSatisfyQuestions(int offset, int count,int provinceid) {
		QuestionCondition condition = new QuestionCondition();
		condition.setStartpos(offset);
		condition.setCount(count);
		condition.setProvinceId(provinceid);
		return (List<Long>) selectList("selectVisibleAndSatisfyQuestions",
				condition);
	}

	@Override
	public List<Long> getVisibleAndSatisfyQuestions(QuestionCondition condition) {
		return (List<Long>) selectList("selectVisibleAndSatisfyQuestions",
				condition);
	}

	@Override
	public List<Long> getVisibleQuestions(int offset, int count, int provinceid) {
		QuestionCondition condition = new QuestionCondition();
		condition.setStartpos(offset);
		condition.setCount(count);
		condition.setProvinceId(provinceid);
		return (List<Long>) selectList("selectVisibleQuestions",
				condition);
	}
	
	@Override
	public List<Long> getVisibleQuestions(QuestionCondition condition) {
		return (List<Long>) selectList("selectVisibleQuestions",
				condition);
	}


	@Override
	public List<Long> getQuestions(QuestionCondition condition) {
//		Object [] questions = selectListWithCount("selectQuestions", condition);
//		return (List<Long>) questions[0];
		return (List<Long>) selectList("selectQuestions",
				condition);
	}

	
	@Override
	public int updateQuestion(Question question) {
		return update("updateQuestion",question);
	}

	@Override
	public int deleteQuestion(long qid) {
		return delete("deleteQuestion", qid);
	}

	@Override
	public int insertQuestion(Question question) {
		return insert("insertQuestion", question);
	}

	@Override
	public int updateUserQuestionMeta(int userId) {
		return update("increastOneUserQuestionMeta", userId);
	}

	@Override
	public boolean isExistUserQuestionMeta(int userId) {
		return ((Integer)selectOne("isExistUserMeta",userId)) > 0?true:false;
	}

	@Override
	public int insertUserQuestionMeta(int userId) {
		return insert("insertNewUserQuestionMeta", userId);
	}

	@Override
	public int passQuestion(long qid) {
		return update("passQuestion",qid);
	}

	@Override
	public int setBestAnswer(Answer answer) {
		return update("setBestAnswer",answer);
	}

	@Override
	public int unsetBestAnswer(Question question) {
		return update("unsetBestAnswer",question);
	}

	@Override
	public int setBestUpdateUserScore(Answer answer) {
		return update("setBestUpdateUserScore",answer);
	}

	@Override
	public int unsetBestUpdateUserScore(Question question) {
		return update("unsetBestUpdateUserScore",question);
	}

	@Override
	public List<Long> getLatestSolvedQuestionByGroup(QuestionCondition condition) {
		return (List<Long>) selectList("getLatestSolvedQuestionByGroup",condition);
	}


	@Override
	public int getNetFriendsQuestionsCount(boolean all) {
		return (Integer) selectOne("selectNetFriendsQuestionsCount",genMabatisMapValues(all));
	}

	@Override
	public List<Question> selectAllQuestions() {
		return (List<Question>) selectList("selectAllQuestions");
	}

	@Override
	public int selectAllQuestionsCount() {
		return (Integer) selectOne("selectAllQuestionsCount");
	}

	@Override
	public List<Long> selectQuestionLargerIds(long maxid) {
		return (List<Long>) selectList("selectQuestionLargerIds",maxid);
	}

	@Override
	public long getQuestionsCount(QuestionCondition condition) {
		return (Long) selectOne("selectQuestionsCount",condition);
	}

	@Override
	public long getSolvedQuestionsCount(QuestionCondition condition) {
		return (Long) selectOne("selectSolvedQuestionsCount");
	}

	@Override
	public int setAddContent(long qid, String addContent) {
		return update("setAddContent",genMabatisMapParameter(qid, addContent));
	}

	@Override
	public int setQuestionOrder(long qid, int order) {
		return update("setQuestionOrder",genMabatisMapParameter(qid, order));
	}

	@Override
	public List<Long> getMemberQuestions(int startpos, int count, boolean all) {
		return (List<Long>) selectList("selectMemberQuestions",genMabatisMapValues(startpos,count,all));
	}

	@Override
	public int getMemberQuestionsCount(boolean all) {
		return (Integer) selectOne("selectMemberQuestionsCount", genMabatisMapValues(all));
	}

	@Override
	public List<Long> getNetFriendsQuestions(int startpos, int count,boolean all) {
		return (List<Long>) selectList("selectNetFriendsQuestions",genMabatisMapValues(startpos,count,all));
	}

	@Override
	public List<Long> getAddContentQuestions(int startpos, int count,
			boolean all) {
		return (List<Long>) selectList("selectAddContentQuestions",genMabatisMapValues(startpos,count,all));
	}

	@Override
	public int getAddContentQuestionsCount(boolean all) {
		return (Integer) selectOne("selectAddContentQuestionsCount", genMabatisMapValues(all));
	}

	@Override
	public int passQuestionsAddcontent(long qid) {
		return update("passQuestionsAddcontent",qid);
	}

	@Override
	public int forbidQuestionAddcontent(long qid) {
		return update("forbidQuestionAddcontent",qid);
	}

	@Override
	public int addQuestionPv(long qid) {
		return update("addQuestionPv",qid);
	}
}
