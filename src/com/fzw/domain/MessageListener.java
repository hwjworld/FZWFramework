package com.fzw.domain;



/**
 * 域事件接口，实现此接口的action将走异步调用，提高效率，分清职责. 可以使用所有的repository,
 * 
 * @author hwj
 *
 */
public interface MessageListener{

	abstract void action(DomainMessage domainMessage);
}
