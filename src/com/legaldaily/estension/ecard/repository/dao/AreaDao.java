package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;

public interface AreaDao {
	public int getProvinceOrder(int provinceId);
//	public int getCityOrder(int cityId);
	
	public Province getProvince(int provinceId);
	public City getCity(int cityid);
	public List<City> getAllCities();
	public List<Province> getAllProvinces();
}
