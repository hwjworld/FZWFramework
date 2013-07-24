package com.legaldaily.estension.ecard.model.condition;

import java.util.Date;

import com.fzw.model.ConnectionMessage;
import com.fzw.utils.StringValueUtils;

public class UserCondition extends EcardServiceCondition {

	public UserCondition(ConnectionMessage message) {
		super(message);
	}

	public UserCondition() {
		super();
	}
	
	private Date starttime;
	private Date endtime;
	private int count;

	@Override
	public void buildCondition() {
		starttime = StringValueUtils.getWWPDate(params, "starttime",
				"1970-01-01");
		endtime = StringValueUtils.getWWPDate(params, "endtime");
		count = StringValueUtils.getWWPInt(params, "count", 5);
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
