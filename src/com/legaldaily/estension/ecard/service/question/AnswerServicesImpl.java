package com.legaldaily.estension.ecard.service.question;

import java.util.Collections;
import java.util.List;

import com.fzw.domain.DomainMessage;
import com.fzw.utils.ListUtils;
import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.Constants;
import com.legaldaily.estension.ecard.model.condition.AnswerCondition;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.repository.AnswerRepository;
import com.legaldaily.estension.ecard.utils.Comparators;
import com.legaldaily.estension.ecard.utils.PermissionUtil;

public class AnswerServicesImpl implements AnswerServices {

	/**
	 * 存放某些变量的第二结果值
	 * 例如先查了某个列表,第二次要查count,
	 * 	那么就可以按第一个消息的名称做为key,count做为value,可以省去第二次查询带来的开销
	 */
//	private static final Map<String, Object> tmpResultMap = new HashMap<String, Object>();
	AnswerRepository answerRepository;
	public AnswerServicesImpl(AnswerRepository repository) {
		answerRepository = repository;
	}
	@Override
	public List<Answer> getAnswers(AnswerCondition condition) {
		List<Answer> list = answerRepository.getAnswers(condition);
		return list==null?ListUtils.EMPTY_LIST:list; 
	}
	@Override
	public long getAnswersCount(AnswerCondition condition) {
		return answerRepository.getAnswersCount(condition);
	}
	@Override
	//由服务来处理判断是要审核还是不审核，不是仓储的职责，所以不使用一个saveAnswer方法
	public int addAnswer(Answer answer) {
		int rv = 0;
		if(PermissionUtil.isLawyer(userService.getUser(answer.getUser().getUid()))){
			answer.setScore(StringValueUtils.getInt(sideService.getDefaultSetting("answer_score")));
			answer.setCheck_flag(1);
			answer.getUser().setScore(answer.getUser().getScore()+answer.getScore());
			answer.setVisible("1");
			rv = answerRepository.saveAnswer(answer);
		}else {
			answer.setScore(StringValueUtils.getInt(sideService.getDefaultSetting("answer_score")));
			answer.setCheck_flag(0);
			answer.setVisible("0");
			rv = answerRepository.saveAnswer(answer);
		}
		return rv;
	}
	@Override
	public int updateAnswer(Answer answer) {
		return answerRepository.saveAnswer(answer);
	}
	@Override
	public int deleteAnswer(int answerId) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean passAnswer(long[] aids) {
		for (long l : aids) {
			if(l <= 0)
				continue;
			Answer answer = answerRepository.getAnswer(l);
			answer.setVisible("1");
			answer.setCheck_flag(1);
		}
		DomainMessage message = DOMAIN_EVENTS.passAnswer(aids);
		if(message.isDone()){
			return (Boolean) message.getEventResult();
		}
		return true;
	}
	@Override
	public boolean forbidAnswer(long[] aids) {
		for (long l : aids) {
			if(l <= 0)
				continue;
			Answer answer = answerRepository.getAnswer(l);
			answer.setVisible("0");
			answer.setCheck_flag(1);
		}
		DomainMessage message = DOMAIN_EVENTS.forbitAnswer(aids);
		if(message.isDone()){
			return (Boolean) message.getEventResult();
		}
		return true;
	}
	@Override
	public boolean setBestAnswer(Answer answer) {
		if(answer == null){
			return false;
		}
		answer.setScore(answer.getScore()+Constants.GOOD_ANSWER_SCORE);
		answer.getQuestion().setBestAnswer(answer);
		answer.getQuestion().setSatisfied("1");
		DOMAIN_EVENTS.setBestAnswer(answer);
//		return answerRepository.setBestAnswer(answer)>0?true:false;
		return true;
	}
	@Override
	public Answer getAnswer(long answerId) {
		return answerRepository.getAnswer(answerId);
	}
	@Override
	public List<Answer> getLatestLawyerAnswers(AnswerCondition condition) {
		return answerRepository.getLawyerAnswers(condition.getStartpos(),condition.getCount(),StringValueUtils.getBoolean(condition.getValue("all")));
//		int count = condition.getCount();
//		condition.setCount(Integer.MAX_VALUE);
//		List<Answer> rvList = new ArrayList<Answer>();
//		while(rvList.size() < count){
//			List<Answer> list = getAnswers(condition);
//			if(CollectionUtils.isEmpty(list))
//				break;
//			for (Answer answer : list) {
//				if(answer.getUser().getUserGroup().getGroupid() == Constants.DEFAULT_LAWYERS){
//					rvList.add(answer);
//				}
//				//采用map-count方式,所以修改此,使用下面新增代码
////				if(rvList.size() >= count){
////					break;
////				}
//			}
//			condition.setStartpos(condition.getStartpos()+condition.getCount());
//		}
//		tmpResultMap.put("getLatestLawyerAnswers", rvList.size());
//		rvList = (List<Answer>) ListUtils.subList(rvList, 0, count);
//		return rvList;
	}
	@Override
	public int getLatestLawyerAnswersCount(AnswerCondition condition) {
		return answerRepository.getLawyerAnswersCount(StringValueUtils.getBoolean(condition.getValue("all")));
//		String mapkey = "getLatestLawyerAnswers";
////		return getLatestLawyerAnswers(condition).size();
//		int rv = 0;
//		if(!tmpResultMap.containsKey(mapkey)){
//			rv = getLatestLawyerAnswers(condition).size();
//		}else {
//			rv = StringValueUtils.getInt(tmpResultMap.get(mapkey).toString());
//			tmpResultMap.remove(mapkey);
//		}
//		return rv;
	}
	@Override
	public List<Answer> getLatestNetfriendsAnswers(AnswerCondition condition) {
		return answerRepository.getNetfriendsAnswers(condition.getStartpos(),condition.getCount(),StringValueUtils.getBoolean(condition.getValue("all")));
//		int count = condition.getCount();
//		condition.setCount(Integer.MAX_VALUE);
//		List<Answer> rvList = new ArrayList<Answer>();
//		while(rvList.size() < count){
//			List<Answer> list = getAnswers(condition);
//			if(CollectionUtils.isEmpty(list))
//				break;
//			for (Answer answer : list) {
//				if(answer.getUser().getUserGroup().getGroupid() != Constants.DEFAULT_LAWYERS){
//					rvList.add(answer);
//				}
////				if(rvList.size() >= count){
////					break;
////				}
//			}
//			condition.setStartpos(condition.getStartpos()+condition.getCount());
//		}
//		tmpResultMap.put("getLatestNetfriendsAnswers", rvList.size());
//		rvList = (List<Answer>) ListUtils.subList(rvList, 0, count);
//		return rvList;
	}
	@Override
	public long getLatestNetfriendsAnswersCount(AnswerCondition condition) {
		return answerRepository.getNetfriendsAnswersCount(StringValueUtils.getBoolean(condition.getValue("all")));
//		String mapkey = "getLatestNetfriendsAnswers";
////		return getLatestLawyerAnswers(condition).size();
//		int rv = 0;
//		if(!tmpResultMap.containsKey(mapkey)){
//			rv = getLatestNetfriendsAnswers(condition).size();
//		}else {
//			rv = StringValueUtils.getInt(tmpResultMap.get(mapkey).toString());
//			tmpResultMap.remove(mapkey);
//		}
//		return rv;
	}
	
	@Override
	public boolean editAnswer(Answer answer) {
		return answerRepository.saveAnswer(answer)>0?true:false;
	}
	@Override
	public boolean unsetBestAnswer(Answer answer) {
		answer.setScore(answer.getScore()-Constants.GOOD_ANSWER_SCORE);
		answer.getQuestion().setSatisfied("0");
		DOMAIN_EVENTS.unsetBestAnswer(answer);
		return true;
	}
	@Override
	public List<Answer> getAnswersByQuestionid(long qid, int startpos, int count) {
		Question question = questionServices.getQuestion(qid);
		List<Answer> answers = question.getAnswers();
		Collections.sort(answers,Comparators.ANSWER_COMPARATOR_DESC_ANSWERID);
		List<Answer> rv = (List<Answer>) ListUtils.subList(answers, startpos, startpos+count);
		return rv;
	}
	@Override
	public int getAnswersByQuestionidCount(long qid) {
		Question question = questionServices.getQuestion(qid);
		return question.getAnswers().size();
	}

}
