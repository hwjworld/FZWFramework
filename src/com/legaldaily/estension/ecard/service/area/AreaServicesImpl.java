package com.legaldaily.estension.ecard.service.area;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fzw.Globals;
import com.legaldaily.estension.ecard.domain.context.AreaContext;
import com.legaldaily.estension.ecard.domain.context.ProvinceAreaContext;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.condition.AreaCondition;
import com.legaldaily.estension.ecard.repository.AreaRepository;
import com.legaldaily.estension.ecard.repository.SideRepository;
import com.legaldaily.estension.ecard.utils.Comparators;

public class AreaServicesImpl implements AreaServices {

	private AreaRepository areaRepository;
	public AreaServicesImpl(AreaRepository areaRepository) {
		this.areaRepository = areaRepository;
	}
	public AreaServicesImpl() {
		this.areaRepository = (AreaRepository) Globals.getBean("areaRepository");
	}
	@Override
	public List<City> getAllCities() {
		return areaRepository.getAllCities();
	}
	@Override
	public List<Province> getAllProvinces() {
		return areaRepository.getAllProvinces();
	}
	@Override
	public Province getProvince(int provinceId) {
		return areaRepository.getProvince(provinceId);
	}
	@Override
	public City getCity(int cityId) {
		return areaRepository.getCity(cityId);
	}
	
	public List<City> getCitiesByProvinceid(int provinceid){
		List<City> allcCities = getAllCities();
		List<City> rvList = new ArrayList<City>();
		for (City city : allcCities) {
			if(city.getProvinceId() == provinceid){
				rvList.add(city);
			}
		}
		return rvList;
	}
	@Override
	public List<City> listCity(AreaCondition condition) {
		if(condition.isAll()){
			return getAllCities();
		}
		List<City> cities = new ArrayList<City>();
		for (int cityId : condition.getCityid()) {
			if(cityId > 0){
				cities.add(getCity(cityId));
			}
		}
		
		int provinceid[] = condition.getProvinceid(); 
		for(int i=0 ; i<provinceid.length ; i++){
			cities.addAll(getCitiesByProvinceid(provinceid[i]));
		}
		return cities;
	}
	@Override
	public List<Province> listProvince(AreaCondition condition) {
		List<Province> provinces = new ArrayList<Province>();
		if(condition.isAll()){
			provinces.addAll(getAllProvinces());
		}else {
			for (int provinceId : condition.getProvinceid()) {
				if( provinceId > 0){
					provinces.add(getProvince(provinceId));
				}
			}
		}
		if(StringUtils.indexOfAny(condition.getOrderby(), new String[]{"desc","asc"})!=StringUtils.INDEX_NOT_FOUND){
			List<AreaContext> contexts = new ArrayList<AreaContext>();
			for (Province province2 : provinces) {
				AreaContext context = new ProvinceAreaContext(areaRepository);
				context.setArea(province2);
				context.mixOrder2Area();
				contexts.add(context);
			}
			if(StringUtils.indexOf(condition.getOrderby(), "desc")!=StringUtils.INDEX_NOT_FOUND){
				Collections.sort(contexts, Comparators.AREACONTEXT_COMPARATOR_DESC_ORDER);
			}else if (StringUtils.indexOf(condition.getOrderby(), "asc")!=StringUtils.INDEX_NOT_FOUND) {
				Collections.sort(contexts, Comparators.AREACONTEXT_COMPARATOR_ASC_ORDER);
			}
			provinces.clear();
			for (AreaContext areaContext : contexts) {
				provinces.add((Province)areaContext.getArea());
			}
		}
		return provinces;
	
	}

}
