/**
 * 
 */
package com.legaldaily.estension.ecard.service.qarecommend;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.Cond;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.recommend.Recommendation;
import com.legaldaily.estension.ecard.service.EcardService;

/**
 * 
 * QAR = QuestionAnswerRecommend
 * 
 * @author hwj
 * 
 */
public interface IQARecommend extends EcardService {

	/**
	 * 返回 已推荐问答列表
	 * 
	 * @return
	 */
	public List<Recommendation> getQARList(Cond cond);

	/**
	 * 返回 未推荐问答列表
	 * 
	 * @param cond
	 *            查询条件
	 * @return
	 */
	public List<Question> getQARWaitList(Cond cond);

	/**
	 * 根据条件增加一个问题推荐
	 * 
	 * @return
	 */
	public boolean addRecommend(Cond cond);

	/**
	 * 移除一个问题推荐
	 * 
	 * @return
	 */
	public boolean removeRecommend(Cond cond);

	/**
	 * 移动位置
	 * 
	 * @return
	 */
	public boolean movePosition(Cond cond);

//	@Override
//	public void execute(ConnectionMessage message);

}
