package com.legaldaily.estension.ecard.service.qarecommend;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.Cond;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.recommend.RecommendTransient;
import com.legaldaily.estension.ecard.model.recommend.Recommendation;
import com.legaldaily.estension.ecard.repository.QuestionRepository;

//import com.fzw.repository.UserRepository;

public class QARecommendImpl implements IQARecommend {

	protected QuestionRepository questionRepository;

	// protected UserRepository userRepository;

	public QARecommendImpl(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	// public QARecommendImpl(QuestionRepository
	// questionRepository,UserRepository userRepository) {
	// this.questionRepository = questionRepository;
	// this.userRepository = userRepository;
	// }
	@Override
	public List<Recommendation> getQARList(Cond cond) {
		cond.buildRecommendCond();
		return questionRepository.listQARecommend(cond);
	}

	@Override
	public List<Question> getQARWaitList(Cond cond) {
		cond.buildQAWaitRCond();
		return questionRepository.listWaitQARecommend(cond);
	}

	@Override
	public boolean addRecommend(Cond cond) {
		cond.buildAddRecommendCond();
		for (long qid : cond.getQid()) {
			if (qid <= 0) {
				continue;
			}
			RecommendTransient rt = new RecommendTransient();
			rt.setQid(qid);
			rt.setRecommendTime(cond.getRecommendTime());
			rt.setUserid(cond.getOperatorid());

			questionRepository.addRecommend(questionRepository
					.buildRecommendation(rt));
		}
		return true;
	}

	@Override
	public boolean removeRecommend(Cond cond) {
		cond.buildRemoveRecommendCond();
		for (long qid : cond.getQid()) {
			if (qid <= 0) {
				continue;
			}
			RecommendTransient rt = new RecommendTransient();
			rt.setQid(qid);

			questionRepository.removeRecommend(questionRepository
					.buildRecommendation(rt));
		}
		return true;
	}

	@Override
	public boolean movePosition(Cond cond) {
		cond.buildMoveRecommendPositionCond();
		long qid1 = cond.getQid()[0];
		long qid2 = cond.getQid()[1];

		if (qid1 > 0 && qid2 > 0 && qid1 != qid2) {
			Recommendation recommendation1 = questionRepository
					.buildRecommendation(qid1);
			Recommendation recommendation2 = questionRepository
					.buildRecommendation(qid2);
			questionRepository.moveRecommendPosition(recommendation1,
					recommendation2);
		}
		return true;
	}

//	@Override
//	public void execute(ConnectionMessage message) {
//		String messageName = message.getMessageName();
//		Cond cond = new Cond(message.getParamMap());
//		Object result = null;
//		if (StringUtils.equals(messageName, EcardService.LIST_QARECOMMEND)) {
//			result = getQARList();
//		} else if (StringUtils.equals(messageName,
//				EcardService.LIST_WAITQARECOMMEND)) {
//			result = getQARWaitList(Cond.buildQARCond(cond));
//		} else if (StringUtils.equals(messageName, EcardService.RECOMMEND_ADD)) {
//			result = addRecommend(Cond.buildAddRecommendCond(cond));
//		} else if (StringUtils.equals(messageName,
//				EcardService.RECOMMEND_REMOVE)) {
//			result = removeRecommend(Cond.buildRemoveRecommendCond(cond));
//		} else if (StringUtils.equals(messageName,
//				EcardService.RECOMMEND_MOVEPOSITION)) {
//			result = movePosition(Cond.buildMoveRecommendPositionCond(cond));
//		} else {
//			result = "unsupported command";
//		}
//		message.setResultValue(gson.toJson(result));
//	}

}
