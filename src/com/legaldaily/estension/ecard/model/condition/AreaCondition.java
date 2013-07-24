package com.legaldaily.estension.ecard.model.condition;

import com.fzw.model.ConnectionMessage;
import com.fzw.utils.StringValueUtils;

public class AreaCondition extends EcardServiceCondition {

	public AreaCondition(ConnectionMessage message) {
		super(message);
	}

	private int cityid[];
	private int provinceid[];
	private boolean all;
	private String orderby;

	public int[] getCityid() {
		return cityid;
	}

	public void setCityid(int[] cityid) {
		this.cityid = cityid;
	}

	public int[] getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int[] provinceid) {
		this.provinceid = provinceid;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	@Override
	public void buildCondition() {
		cityid = StringValueUtils.convert2intArrays(getIds(params.get("cityid")));
		provinceid = StringValueUtils.convert2intArrays(getIds(params.get("provinceid")));
		all = StringValueUtils.getBoolean(params.get("all"),false);
		setOrderby(getOrderBySql(StringValueUtils.getWWPString(params, "orderby",null)));
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

}
