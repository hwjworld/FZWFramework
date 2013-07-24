package com.legaldaily.estension.ecard.repository;

import java.util.List;

import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;

public interface AreaRepository {
//	public int getCityOrder();
	public List<City> getAllCities();
	public List<Province> getAllProvinces();
	int getProvinceOrder(int provinceId);
	public Province getProvince(int provinceId);
	public City getCity(int cityId);
}
