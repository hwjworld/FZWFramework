/**
 * TODO 增加此类为了减轻各repository的压力
 * 那些归为正常Repositry,继承些类为域事件Repository
 * 不可直接将dbdao暴露
 * 时间压力,暂先不实现
 */
package com.legaldaily.estension.ecard.repository.domainrepository;

import com.fzw.domain.DomainMessage;
import com.fzw.repository.dao.DBDao;
import com.legaldaily.estension.ecard.repository.listener.EcardMessageListener;

public abstract class DomainEventRepository implements  DBDao, EcardMessageListener {

	public abstract void action(DomainMessage domainMessage);

}
