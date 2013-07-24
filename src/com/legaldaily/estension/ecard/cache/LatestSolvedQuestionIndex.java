package com.legaldaily.estension.ecard.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.CollectionUtils;

import com.fzw.Globals;
import com.fzw.utils.ListUtils;
import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.question.QuestionsType;
import com.legaldaily.estension.ecard.repository.LawRepository;
import com.legaldaily.estension.ecard.repository.QuestionRepository;
import com.legaldaily.estension.ecard.service.law.LawServices;
import com.legaldaily.estension.ecard.service.law.LawServicesImpl;
import com.legaldaily.estension.ecard.service.question.QuestionServices;
import com.legaldaily.estension.ecard.service.question.QuestionServicesImpl;

/**
 * 后台准备按每个group分类的最新ＱＵＥＳＴＩＯＮ数据 支持2种方式更新 １.每15分钟更新一次 ２.有新提问一了更新那组的数据
 * Map<groupid<int>, list<question-count10>>
 * 
 * @author hwj
 * 
 */
public class LatestSolvedQuestionIndex {
	// 存放分组后的最新解决列表
	private static final Map<Integer, List<Question>> latestSolvedQuestion = new HashMap<Integer, List<Question>>();

	// 存放已解决和未解决的问题有列表
	private static final Map<QuestionsType, List<Question>> questionsMap = new HashMap<QuestionsType, List<Question>>();

	private static final LawServices lawServices = new LawServicesImpl(
			(LawRepository) Globals.getBean("lawRepository"));
	private static final QuestionServices questionServices = new QuestionServicesImpl(
			(QuestionRepository) Globals.getBean("questionRepository"));
	private static boolean inited = false;

	public static void init() {
		if (!inited) {
			LogUtils.info("开始准备最新解答数据");
			Timer timer = new Timer();
			timer.schedule(new UpdateLatestSolvedQuestionsTask(), 0,
					15 * 60 * 1000);
			timer.schedule(new UpdateQuestionsTask(), 0, 15 * 60 * 1000);
		}
	}

	static class UpdateLatestSolvedQuestionsTask extends TimerTask {

		@Override
		public void run() {
			List<LawCategory> categories = lawServices.getAllLawCategories();
			QuestionCondition condition = new QuestionCondition();
			condition.setCount(20);
			for (LawCategory lawCategory : categories) {
				condition.setGroupId(lawCategory.getCatId());
				updateLatestSolvedQuestionsByGroup(condition);
			}
			LogUtils.info("准备按组最新解答数据完毕");
		}

		private static void updateLatestSolvedQuestionsByGroup(
				QuestionCondition condition) {
			List<Question> list = questionServices
					.getLatestSolvedQuestionByGroup(condition);
			latestSolvedQuestion.put(condition.getGroupId(), list);
		}
	}
	
	public static void updateLatestSolvedQuestion(Question question){
		indexSolvedQuestion(question);
		indexUnSolvedQuestion(question);
		indexNoneAnswerQuestions(question);
		indexHighScoreQuestions(question);
		LogUtils.info("索引［"+question.getQid()+"--"+question.getTitle()+"］完毕");
	}

	public static void deleteLatestSolvedQuestion(Question question){
		for (List<Question> list : questionsMap.values()) {
			if(list == null || question == null)
				continue;
			for (int i = list.size()-1; i >=0 ; i--) {
				if(list.get(i).getQid() == question.getQid()){
					list.remove(i);
					break;
				}
			}
		}
		LogUtils.info("从索引中删除［"+question.getQid()+"--"+question.getTitle()+"］");
	}

	/**
	 * 包括已解决的问题列表和未解决的问题列表
	 * 
	 * @author hwj
	 * 
	 */
	static class UpdateQuestionsTask extends TimerTask {

		@Override
		public void run() {
			indexSolvedQuestion();
			indexUnSolvedQuestion();
			indexNoneAnswerQuestions();
			indexHighScoreQuestions();
			LogUtils.info("[准备最新解答数据完毕]");
		}
	}

	
	/**
	 * 按groupid得到已解决问题列表数据
	 */
	public static List<Question> getLatestSolvedQuestionByGroup(int groupid,
			int count) {
		if (count > 20) {
			QuestionCondition condition = new QuestionCondition();
			condition.setGroupId(groupid);
			condition.setCount(count);
			return questionServices.getLatestSolvedQuestionByGroup(condition);
		}
		List<Question> rvList = new ArrayList<Question>();
		List<Question> questions = latestSolvedQuestion.get(groupid);
		if (CollectionUtils.isNotEmpty(questions)) {
			rvList.addAll(questions);
		}
		rvList = (List<Question>) ListUtils.subList(rvList, 0, count);
		return rvList;
	}

	private static void indexHighScoreQuestions() {
		QuestionCondition condition = new QuestionCondition();
		condition.setCount(10);
		condition.setStartpos(0);
		condition.setVisible("1");
		condition.setSatisfy("0");
		condition.setScoreValueMark('>');
		condition.setScoreValue(0);
		condition.setOrderby("q_value desc");
		questionsMap.put(QuestionsType.getHighScoreQuestions,
				questionServices.getQuestions(condition));
		LogUtils.info("准备高悬赏问题完毕");		
	}
	private static void indexHighScoreQuestions(Question question) {
		List<Question> list = questionsMap.get(QuestionsType.getHighScoreQuestions);
		if(question.getIsCheck()==1 && question.getScore()>0){
			if(CollectionUtils.isEmpty(list)){
				list.add(question);
			}else {
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getScore()<=question.getScore()){
						list.add(i,question);
						break;
					}
				}
			}
		}
	}

	private static void indexNoneAnswerQuestions() {
		QuestionCondition condition = new QuestionCondition();
		condition.setCount(10);
		condition.setStartpos(0);
		condition.setSatisfy("0");
		condition.setVisible("1");
		condition.setOrderby("in_turn desc,q_id desc");
		condition.setReplyCount(0);
		condition.setReplyCountMark('=');
		questionsMap.put(QuestionsType.getNoneAnswerQuestions,
				questionServices.getQuestions(condition));
		LogUtils.info("准备０解答问题完毕");
	}
	private static void indexNoneAnswerQuestions(Question question) {
		List<Question> list = questionsMap.get(QuestionsType.getNoneAnswerQuestions);
		if(question.getIsCheck()==1 && question.getBestAnswer() == null && CollectionUtils.isEmpty(question.getAnswers())){
			list.add(0,question);
		}
	}

	private static void indexUnSolvedQuestion() {
		QuestionCondition condition = new QuestionCondition();
		condition.setCount(10);
		condition.setStartpos(0);
		condition.setSatisfy("0");
		condition.setVisible("1");
		condition.setOrderby("in_turn desc,q_id desc");
		questionsMap.put(QuestionsType.getUnSolvedQuestions,
				questionServices.getQuestions(condition));
		LogUtils.info("准备待解决问题完毕");		
	}
	private static void indexUnSolvedQuestion(Question question) {
		List<Question> list = questionsMap.get(QuestionsType.getUnSolvedQuestions);
		if(question.getIsCheck()==1 && question.getBestAnswer() == null){
			list.add(0,question);
		}
	}

	private static void indexSolvedQuestion() {
		QuestionCondition condition = new QuestionCondition();
		condition.setCount(10);
		condition.setProvinceId(0);
		condition.setStartpos(0);
		condition.setOrderby("");
		questionsMap.put(QuestionsType.getSolvedQuestions,
				questionServices.getSolvedQuestions(condition));
		LogUtils.info("准备已解决问题完毕");
	}
	private static void indexSolvedQuestion(Question question) {
		List<Question> list = questionsMap.get(QuestionsType.getSolvedQuestions);
		if(question.getIsCheck()==1 && question.getBestAnswer()!= null){
			list.add(0,question);
		}
//		LogUtils.info("准备已解决问题完毕");
	}

	/**
	 * 已解决问题列表数据
	 */
	public static List<Question> getLatestSolvedQuestion(int count) {
		return null;
	}
	public static List<Question> getQuestions(QuestionCondition condition) {
		List<Question> rvList = new ArrayList<Question>();
		boolean inFlag = false;
		String type = condition.getValue("type");
		QuestionsType[] names = QuestionsType.values();
		for (QuestionsType QuestionsType : names) {
			if (QuestionsType.name().equals(type) && condition.getCount() <= 10
					&& condition.getProvinceId() == 0
					&& condition.getStartpos() == 0) {
				if(questionsMap.get(QuestionsType) != null){
					inFlag = true;
					rvList.addAll(questionsMap.get(QuestionsType));
				}
				break;
			}
		}
		if(!inFlag){
			if(QuestionsType.getSolvedQuestions.name().equals(type)){
				rvList.addAll(questionServices.getSolvedQuestions(condition));
			}else {
				rvList.addAll(questionServices.getQuestions(condition));
			}
		}else {
			rvList = (List<Question>) ListUtils.subList(rvList, 0,
					condition.getCount());
		}
		return rvList;
		
	}

}
