package com.legaldaily.estension.ecard.service.area;

import java.util.List;

import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.condition.AreaCondition;
import com.legaldaily.estension.ecard.service.EcardService;

public interface AreaServices extends EcardService{

	public List<City> getAllCities();
	public List<Province> getAllProvinces();
	public Province getProvince(int provinceId) ;
	public City getCity(int cityId);
	public List<City> listCity(AreaCondition condition);
	public List<Province> listProvince(AreaCondition condition);
}
