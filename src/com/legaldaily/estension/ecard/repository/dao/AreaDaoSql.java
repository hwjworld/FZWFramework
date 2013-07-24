package com.legaldaily.estension.ecard.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;

public class AreaDaoSql extends EcardDao implements AreaDao {

	private static Map<Integer, Integer> provinceOrderMap = new HashMap<Integer, Integer>();
	@Override
	public int getProvinceOrder(int provinceId) {
		if(!provinceOrderMap.containsKey(provinceId)){
			provinceOrderMap.put(provinceId, (Integer)selectOne("selectProvinceOrder", provinceId));
		}
		return provinceOrderMap.get(provinceId);	
	}

	@Override
	public Province getProvince(int provinceId) {
		return (Province)selectOne("selectProvinceInfo", provinceId);	
	}

	@Override
	public City getCity(int cityid) {
		return (City)selectOne("selectCityInfo", cityid);
	}

	@Override
	public List<City> getAllCities() {
		 return (List<City>) selectList("selectAllCities");
	}

	@Override
	public List<Province> getAllProvinces() {
		 return (List<Province>) selectList("selectAllProvinces");
	}

}
