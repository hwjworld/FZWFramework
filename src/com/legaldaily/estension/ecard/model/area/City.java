package com.legaldaily.estension.ecard.model.area;

public class City extends Area{
	private String cityMap;
	private int provinceId;
		
	public String getCityMap() {
		return cityMap;
	}
	public void setCityMap(String cityMap) {
		this.cityMap = cityMap;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	

}
