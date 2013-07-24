package com.legaldaily.estension.ecard.service.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import com.fzw.Globals;
import com.fzw.utils.FutureTaskUtil;
import com.fzw.utils.ListUtils;
import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.Constants;
import com.legaldaily.estension.ecard.cache.LatestSolvedQuestionIndex;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.repository.QuestionRepository;

public class QuestionServicesImpl implements QuestionServices {

	/**
	 * 存放某些变量的第二结果值 例如先查了某个列表,第二次要查count,
	 * 那么就可以按第一个消息的名称做为key,count做为value,可以省去第二次查询带来的开销
	 */
	private static final Map<String, Object> tmpResultMap = new HashMap<String, Object>();

	private QuestionRepository questionRepository;

	public QuestionServicesImpl(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}
	public QuestionServicesImpl() {
		this.questionRepository = (QuestionRepository) Globals.getBean("questionRepository");
	}

	@Override
	public Question getQuestion(long questionId) {
		return questionRepository.getQuestion(questionId);
	}

	@Override
	public int addQuestion(Question question) {
		return questionRepository.saveQuestion(question);
	}

	@Override
	public int delQuestion(long[] questionId) {
		int rv = 0;
		for (long l : questionId) {
			rv += questionRepository.removeQuestion(l);
		}
		return rv;
	}

	@Override
	public int delQuestion(Question question) {
		return questionRepository.removeQuestion(question);
	}

	@Override
	/*
	 * 1.取startpos+count个结果存入list 2.结果按answer的回答时间排序
	 * 3.从list中取出startpos往后的count个值,subarray
	 * 
	 * @see com.fzw.service.question.QuestionServices#getSolvedQuestions(int,
	 * int)
	 */
	public List<Question> getSolvedQuestions(QuestionCondition condition) {
		int offset = 0, limit = 200;
		int startpos = condition.getStartpos();
		int count = condition.getCount();
		condition.setStartpos(offset);
		condition.setCount(limit);
		List<Question> resultValues = new ArrayList<Question>();
		// 1
		while (resultValues.size() < startpos + count) {
			List<Question> questions = questionRepository
					.getVisibleAndSatisfyQuestions(condition);
			if (CollectionUtils.isEmpty(questions)) {
				break;
			}
			for (Question question : questions) {
				if (question.getVisible().equals("1")
						&& question.getBestAnswer() != null
						&& question.getBestAnswer().getVisible().equals("1")) {
					if (resultValues.size() >= startpos + count)
						break;
					resultValues.add(question);
				}
			}
			condition.setStartpos(condition.getStartpos() + limit);
			// offset += limit;
		}
		// 2
		return (List<Question>) ListUtils.subList(resultValues, startpos, count
				+ startpos);
		// return resultValues.subList(startpos, count + startpos);
	}

	public long getSolvedQuestionsCount(QuestionCondition condition) {
		return questionRepository.getSolvedQuestionsCount(condition);
	}

	// select q_id,q_title,DATE_FORMAT(q_time,'%m\u6708%d\u65e5') as
	// q_time,q_value,g_id from questions where q_stats='1' order by q_id desc
	// limit 0,5
	// @Override
	public List<Question> getUnSolvedQuestions(QuestionCondition condition) {
		int offset = 0, limit = 50;
		int startpos = condition.getStartpos();
		int count = condition.getCount();
		condition.setStartpos(offset);
		condition.setCount(limit);
		List<Question> resultValues = new ArrayList<Question>();
		// 1
		while (resultValues.size() < startpos + count) {
			// List<Question> questions =
			// questionRepository.getVisibleQuestions(
			// offset, limit,provinceid);
			List<Question> questions = questionRepository
					.getVisibleQuestions(condition);
			if (CollectionUtils.isEmpty(questions)) {
				break;
			}
			for (Question question : questions) {
				if (resultValues.size() >= startpos + count)
					break;
				resultValues.add(question);
			}
			offset += limit;
		}
		// 2
		return (List<Question>) ListUtils.subList(resultValues,
				condition.getStartpos(),
				condition.getStartpos() + condition.getCount());
		// return resultValues.subList(startpos, count + startpos);
	}

	// @Override
	public List<Question> getNoneAnswerQuestions(QuestionCondition condition) {
		int startpos = condition.getStartpos();
		int count = condition.getCount();
		// condition.setStartpos(offset);
		// condition.setCount(limit);

		List<Question> resultValues = new ArrayList<Question>();
		// 1
		// QuestionCondition condition = new QuestionCondition();
		condition.setVisible("1");
		condition.setReplyCount(0);
		condition.setSatisfy("0");
		condition.setReplyCountMark('=');
		condition.setOrderby(EcardServiceCondition
				.getOrderBySql("in_turn,desc,q_id,desc"));
		readQuestionByCondition(startpos, count, condition.getProvinceId(),
				resultValues, condition);
		// 2
		return (List<Question>) ListUtils.subList(resultValues, startpos, count
				+ startpos);
		// return resultValues.subList(startpos, count + startpos);
	}

	// @Override
	public List<Question> getHighScoreQuestions(QuestionCondition condition) {
		int startpos = condition.getStartpos();
		int count = condition.getCount();
		List<Question> resultValues = new ArrayList<Question>();
		// 1
		// QuestionCondition condition = new QuestionCondition();
		condition.setVisible("1");
		condition.setSatisfy("0");
		condition.setScoreValue(0);
		condition.setScoreValueMark('>');
		condition.setOrderby(EcardServiceCondition
				.getOrderBySql("q_value,desc"));
		readQuestionByCondition(startpos, count, condition.getProvinceId(),
				resultValues, condition);
		return (List<Question>) ListUtils.subList(resultValues, startpos, count
				+ startpos);
		// 2
		// return resultValues.subList(startpos, count + startpos);
	}

	@Override
	public List<Question> getQuestions(QuestionCondition condition) {
		List<Question> list = questionRepository.getQuestions(condition); 
		return list==null?ListUtils.EMPTY_LIST:list; 
	}

	@Override
	public long getQuestionsCount(QuestionCondition condition) {
		return questionRepository.getQuestionsCount(condition);
	}

	private void readQuestionByCondition(int startpos, int count,
			int provinceid, List<Question> resultValues,
			QuestionCondition condition) {

		int offset = 0, limit = 50;
		while (resultValues.size() < startpos + count) {
			condition.setStartpos(offset);
			condition.setCount(limit);
			condition.setProvinceId(provinceid);
			List<Question> questions = questionRepository
					.getQuestions(condition);
			if (CollectionUtils.isEmpty(questions)) {
				break;
			}
			for (Question question : questions) {
				if (resultValues.size() >= startpos + count)
					break;
				resultValues.add(question);
			}
			offset += limit;
		}
	}

	@Override
	public int updateQuestion(Question question) {
		return questionRepository.saveQuestion(question);
	}

	@Override
	public boolean isLocked(Question question) {
		return question.getSatisfied().equals("1") ? true : false;
	}

	@Override
	public boolean passQuestions(long[] qid) {
		boolean rv = true;
		for (long l : qid) {
			if (l <= 0)
				continue;
			Question question = questionRepository.getQuestion(l);
			question.setVisible("1");
			question.setIsCheck(1);
			DOMAIN_EVENTS.passQuestion(l);
		}
		return rv;
	}

	@Override
	public boolean setBestAnswer(Answer answer) {
		return questionRepository.setBestAnswer(answer) > 0 ? true : false;
	}

	@Override
	public boolean unsetBestAnswer(Question question) {
		return questionRepository.unsetBestAnswer(question) > 0 ? true : false;
	}

	@Override
	public List<Question> getNetFriendsQuestions(QuestionCondition condition) {
		return questionRepository.getNetFriendsQuestions(condition.getStartpos(),condition.getCount(),StringValueUtils.getBoolean(condition.getValue("all")));
//		int count = condition.getCount();
//		condition.setCount(200);
//		condition.setOrderby("q_id desc");
//		condition.setCheck("0");
//		// condition.setUserId(2);
//		List<Question> rv = new ArrayList<Question>();
//		while (rv.size() < count) {
//			List<Question> questions = getQuestions(condition);
//			if (questions.size() <= 0)
//				break;
//			for (Question question : questions) {
//
//				if (rv.size() >= count)
//					break;
//				if (question.getUser() == null
//						|| question.getUser().getUserGroup() == null)
//					continue;
//
//				if (question.getUser().getUserGroup().getGroupid() == Constants.DEFAULT_NETFRIEND) {
//					rv.add(question);
//				}
//			}
//			condition.setStartpos(condition.getStartpos()
//					+ condition.getCount());
//		}
//		return rv;
	}

	@Override
	public List<Question> getMemberQuestions(QuestionCondition condition) {
		
		return questionRepository.getMemberQuestions(condition.getStartpos(), condition.getCount(), StringValueUtils.getBoolean(condition.getValue("all")));
		
//		int count = condition.getCount();
//		condition.setCount(200);
//
//		condition.setOrderby("q_time desc");
//		condition.setCheck("0");
//		// condition.setUserId(2);
//		List<Question> rv = new ArrayList<Question>();
//		while (rv.size() < count) {
//			List<Question> questions = getQuestions(condition);
//			if (CollectionUtils.isEmpty(questions))
//				break;
//			for (Question question : questions) {
//
//				if (rv.size() >= count)
//					break;
//				if (question.getUser() == null
//						|| question.getUser().getUserGroup() == null)
//					continue;
//
//				if (question.getUser().getUserGroup().getGroupid() != Constants.DEFAULT_NETFRIEND
//						&& question.getIsCheck() == 0) {
//					rv.add(question);
//				}
//			}
//			condition.setStartpos(condition.getStartpos()
//					+ condition.getCount());
//			condition.setCount(condition.getCount() + 200);
//		}
//		return rv;
	}

	@Override
	public List<Question> getLatestSolvedQuestionByGroup(
			QuestionCondition condition) {
//		return LatestSolvedQuestionIndex.getLatestSolvedQuestionByGroup(condition.getGroupId(), condition.getCount());
		if (condition.getCount() <= 0)
			return ListUtils.EMPTY_LIST;
		return questionRepository.getLatestSolvedQuestionByGroup(condition);
	}

	@Override
	public int getMemberQuestionsCount(boolean all) {
		return questionRepository.getMemberQuestionsCount(all);
	}

	@Override
	public int getNetFriendsQuestionsCount(boolean all) {
		return questionRepository.getNetFriendsQuestionsCount(all);
	}

	@Override
	public long getHighScoreQuestionsCount(QuestionCondition condition) {
		return questionRepository.getQuestionsCount(condition);
	}

	@Override
	public boolean setAddContent(long qid, String addContent) {
		if(qid <= 0){
			return false;
		}
		return questionRepository.setAddContent(qid, addContent);
	}

	@Override
	public boolean questionOperate(long qid, String operate) {
		if(qid <= 0 || !ArrayUtils.contains(new String[]{"pub","del"}, operate)){
			return false;
		}
		if("pub".equals(operate)){
			return passQuestions(new long[]{qid});
		}else if("del".equals(operate)){
			return delQuestion(new long[]{qid})>0?true:false;
		}
		return questionRepository.questionOperate(qid, operate);
	}

	@Override
	public boolean setQuestionOrder(long qid, int order) {
		return questionRepository.setQuestionOrder(qid, order);
	}

	@Override
	public boolean forbidQuestions(long[] qid) {
		boolean rv = true;
		for (long l : qid) {
			if (l <= 0)
				continue;
			Question question = questionRepository.getQuestion(l);
			question.setVisible("0");
			question.setIsCheck(1);
			DOMAIN_EVENTS.forbidQuestions(l);
		}
		return rv;
	}

	@Override
	public List<Question> getAddContentQuestions(QuestionCondition condition) {
		return questionRepository.getAddContentQuestions(condition.getStartpos(), condition.getCount(), StringValueUtils.getBoolean(condition.getValue("all")));
	}

	@Override
	public int getAddContentQuestionsCount(boolean all) {
		return questionRepository.getAddContentQuestionsCount(all);
	}

	@Override
	public List<Question> getLatestSolvedQuestion(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean passQuestionsAddcontent(long[] qid) {
		boolean rv = true;
		for (long l : qid) {
			Question question = questionRepository.getQuestion(l);
			if (question == null)
				continue;
			question.setAddStats("1");
			question.setAddCheck("1");
			DOMAIN_EVENTS.passQuestionsAddcontent(l);
		}
		return rv;
	}

	@Override
	public boolean forbidQuestionAddcontent(long[] qid) {
		boolean rv = true;
		for (long l : qid) {
			Question question = questionRepository.getQuestion(l);
			if (question == null)
				continue;
			question.setAddStats("0");
			question.setAddCheck("1");
			DOMAIN_EVENTS.forbidQuestionAddcontent(l);
		}
		return rv;
	}

	@Override
	public boolean addQuestionPv(long qid) {
		Question question = getQuestion(qid);
		if(question != null){
			question.addPv();
			DOMAIN_EVENTS.addQuestionPv(qid);
			return true;
		}else {
			return false;
		}
	}

}
