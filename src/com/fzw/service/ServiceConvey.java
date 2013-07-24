package com.fzw.service;

import com.fzw.model.condition.ServiceCondition;
import com.legaldaily.estension.ecard.service.EcardService;

/**
 * 职责：服务的转达,Convey类与Command的方法一一对应
 * ServiceImpl是真正的Service
 * 1.将ConnectionMessage的消息转到真实的ServiceImpl上
 * @author hwj
 *
 */
public abstract class ServiceConvey<T extends ServiceCondition> implements EcardService{
	

	protected T getCondition(ServiceCondition condition) {
		return (T)condition;
	} 
}
