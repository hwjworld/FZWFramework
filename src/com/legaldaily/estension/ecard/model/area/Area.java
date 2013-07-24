package com.legaldaily.estension.ecard.model.area;

import com.legaldaily.estension.ecard.model.EcardModel;


public abstract class Area extends EcardModel{
	protected int areaId;
	protected String areaName;
	
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
