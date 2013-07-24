package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.Cond;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.recommend.RecommendTransient;
import com.legaldaily.estension.ecard.model.recommend.Recommendation;

public interface QuestionDao {
	public Question getQuestion(long qid);
//	public List<Long> listQARecommendIDs();
	public List<RecommendTransient> listRecommendTransients(Cond cond);
	public RecommendTransient selectRecommendTransient(long qid);
	
	/**
	 * 返回前100条数据
	 * questions a,answers b 
	 * 1.不在已推荐列表里 (a.q_id not in(select q_id from q_tuijian)) 
	 * 2.满意  a.q_lock='1' 
	 * 3.可视  a.q_stats='1' 
	 * 4.有最佳答案  b.a_victory='1' 
	 * 5.时间间隔 q_time between '$start_time' and '$end_time'
	 * 6.查询文本条件  like  $search_tj 
	 * 7.按时间排序 by a.q_time desc limit 100"
	 * 
	 * @param cond
	 * @return
	 */
	public List<Long> listWaitQARecommendIDs(Cond cond);
	
	public int updateQuestion(Question question);
	public int deleteQuestion(long qid);

	public int insertQuestion(Question question);
	public boolean isExistUserQuestionMeta(int userId);
	public int updateUserQuestionMeta(int userId);
	public int insertUserQuestionMeta(int userId);
	
	public void insertRecommendation(Recommendation recommendation);
	public void deleteRecommendation(Recommendation recommendation);
	public void moveRecommendationPosition(Recommendation recommendation1, Recommendation recommendation2);

	/**
	 * 返回可视，有满意的问题列表, stat=1 and lock=1 ,按最佳答案的时间倒序
	 * @param offset
	 * @param count
	 * @return
	 */
	public List<Long> getVisibleAndSatisfyQuestions(int offset, int count,int provinceid) ;
	public List<Long> getVisibleAndSatisfyQuestions(QuestionCondition condition);
	/**
	 * 返回可视的问题列表，按问题id倒序
	 * @param offset
	 * @param count
	 * @param provinceid 
	 * @return
	 */
	public List<Long> getVisibleQuestions(int offset, int count, int provinceid) ;
	public List<Long> getVisibleQuestions(QuestionCondition condition) ;

	public List<Long> getQuestions(QuestionCondition condition) ;
	
	public int passQuestion(long qid);
	
	public int setBestAnswer(Answer answer);
	public int setBestUpdateUserScore(Answer answer);
	public int unsetBestAnswer(Question question);
	public int unsetBestUpdateUserScore(Question question);
	public List<Long> getLatestSolvedQuestionByGroup(QuestionCondition condition);
	public int getMemberQuestionsCount(boolean all);
	public int getNetFriendsQuestionsCount(boolean all);
	/**
	 * 所有的question
	 * @return
	 */
	public List<Question> selectAllQuestions();
	/**
	 *　所有的ＱＵＥＳＴＩＯＮ数量
	 * @return
	 */
	public int selectAllQuestionsCount();
	/**
	 * 比给定的Questionid大的questionid
	 * @param maxid
	 * @return
	 */
	public List<Long> selectQuestionLargerIds(long maxid);
	public long getQuestionsCount(QuestionCondition condition);
	public long getSolvedQuestionsCount(QuestionCondition condition);
	public int setAddContent(long qid, String addContent);
	public int setQuestionOrder(long qid, int order);
	public List<Long> getMemberQuestions(int startpos, int count, boolean all);
	public List<Long> getNetFriendsQuestions(int startpos, int count,boolean all);
	public List<Long> getAddContentQuestions(int startpos, int count,
			boolean all);
	public int getAddContentQuestionsCount(boolean all);
	public int passQuestionsAddcontent(long qid);
	public int forbidQuestionAddcontent(long qid);
	public int addQuestionPv(long qid);
}
