package com.legaldaily.estension.ecard.repository;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.Cond;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.recommend.RecommendTransient;
import com.legaldaily.estension.ecard.model.recommend.Recommendation;

public interface QuestionRepository {
	public Question getQuestion(long qid);
	/**
	 * 返回已推荐QUESTION对象的列表
	 * @return
	 */
	public List<Recommendation> listQARecommend(Cond cond);
	public List<Question> listWaitQARecommend(Cond cond);
	public Recommendation getRecommendation(long qid);
	
	public int saveQuestion(Question question);
	public int removeQuestion(Question question);
	public int removeQuestion(long questionId);

	public void addRecommend(Recommendation recommendation);

	public void removeRecommend(Recommendation recommendation);
	
	/**
	 * 将r1移到r2位置
	 * @param recommendation1
	 * @param recommendation2
	 */
	public void moveRecommendPosition(Recommendation recommendation1, Recommendation recommendation2);
	
	/**
	 * 拷贝rt值，仅计算question和user
	 * @param rt
	 * @return
	 */
	public Recommendation buildRecommendation(RecommendTransient rt);
	
	/**
	 * 重新生成Recommendation
	 * 建立完整Recommendation,包括order,recommendtime等
	 * @param qid
	 * @return
	 */
	public Recommendation buildRecommendation(long qid);
	
	/**
	 * stat=1 and lock=1
	 * @param offset
	 * @param count
	 * @return
	 */
	public List<Question> getVisibleAndSatisfyQuestions(int offset, int count, int provinceid);
	public List<Question> getVisibleAndSatisfyQuestions(QuestionCondition condition);
	public List<Question> getVisibleQuestions(int offset, int count, int provinceid);
	public List<Question> getVisibleQuestions(QuestionCondition condition);

	public List<Question> getQuestions(QuestionCondition condition);
	public void refreshQuestionAnswers(long questionId);
	
	/**
	 * 问题通过审核
	 * @param qid
	 * @return
	 */
	public boolean passQuestion(long qid);
	

	public int setBestAnswer(Answer answer); 
	public int unsetBestAnswer(Question question);
	public List<Question> getLatestSolvedQuestionByGroup(QuestionCondition condition);
	
	public int getMemberQuestionsCount( boolean all);
	public int getNetFriendsQuestionsCount( boolean all);
	
	public List<Question> getAllQuestions();
	public long getQuestionsCount(QuestionCondition condition);
	public long getSolvedQuestionsCount(QuestionCondition condition);
	public boolean setAddContent(long qid, String addContent);
	public boolean questionOperate(long qid, String operate);
	public boolean setQuestionOrder(long qid, int order);
	public List<Question> getMemberQuestions(int startpos, int count, boolean all);
	public List<Question> getNetFriendsQuestions(int startpos, int count, boolean all);
	public boolean deleteQuestion(long qid);
	public List<Question> getAddContentQuestions(int startpos, int count,
			boolean boolean1);
	public int getAddContentQuestionsCount(boolean all);
	public boolean passQuestionsAddcontent(long qid);
	public boolean forbidQuestionAddcontent(long qid);
	public boolean addQuestionPv(long qid);
}
