package com.fzw.domain;

import java.util.concurrent.FutureTask;

import com.fzw.utils.FutureTaskUtil;

public class DomainMessage {

	protected Object eventSource;

	protected Object eventResult;

	protected boolean over = false;
	
	public DomainMessage(Object eventSource) {
		super();
		this.eventSource = eventSource;
	}

	public Object getEventSource() {
		return eventSource;
	}

	public void setEventSource(Object eventSource) {
		this.eventSource = eventSource;
	}

	public Object getEventResult() {
		Object result = null;
		if (over)
			return eventResult;
		try {
			eventResult = FutureTaskUtil.getTaskValue(this);
			result = eventResult;
//			boolean runOk = (Boolean) futureTask.get();
//			if (runOk) {
//				result = this.eventResult;
//			}
			over = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setEventResult(Object eventResult) {
		this.eventResult = eventResult;
	}

	public void addFutureTask(FutureTask<Object> futureTask) {
		FutureTaskUtil.addTask(this, futureTask);
	}
	
	public boolean isDone(){
		return FutureTaskUtil.isDone(this);
	}
}
