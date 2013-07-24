package com.legaldaily.estension.ecard.repository;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.repository.dao.AreaDao;
import com.legaldaily.estension.ecard.repository.dao.AreaDaoSql;

public class AreaRepositoryDao extends RepositoryDao implements AreaRepository {

	AreaDao areaDao;
	public AreaRepositoryDao() {
		areaDao = new AreaDaoSql();
	}
	@Override
	public int getProvinceOrder(int provinceId) {
		return areaDao.getProvinceOrder(provinceId);
	}

	@Override
	public List<City> getAllCities() {
		List<? extends EcardModel> allCities = getAllCacheValues(cityCache);
		if (CollectionUtils.isNotEmpty(allCities) ) {
			return (List<City>)allCities;
		}
		List<City> cities = areaDao.getAllCities();
		if(CollectionUtils.isEmpty(cities))
			return cities;
		for (City city : cities) {
			getProvince(city.getProvinceId()).addCity(city);
			storeCityToCache(city);
		}
		return cities;
	}

	@Override
	public List<Province> getAllProvinces() {
		List<? extends EcardModel> allProvinces = getAllCacheValues(provinceCache);
		if (CollectionUtils.isNotEmpty(allProvinces) ) {
			return (List<Province>)allProvinces;
		}
		List<Province> provinces = areaDao.getAllProvinces();
		if(CollectionUtils.isEmpty(provinces))
			return provinces;
		for (Province province : provinces) {
			storeProvinceToCache(province);
		}
		return provinces;
	}
	@Override
	public Province getProvince(int provinceId) {
		if(provinceId <= 0)
			return null;
		List<Province> provinces = getAllProvinces();
		Province province = null;
		for (Province p : provinces) {
			if(p.getAreaId() == provinceId){
				province = p;
				break;
			}
		}
		return province;
	}
	@Override
	public City getCity(int cityId) {
		if(cityId <= 0)
			return null;
		List<City> cities = getAllCities();
		City city = null;
		for (City p : cities) {
			if(p.getAreaId() == cityId){
				city = p;
				break;
			}
		}
		return city;
	}

}
