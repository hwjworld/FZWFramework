package com.legaldaily.estension.ecard.model.area;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;


public class Province extends Area {
	private List<City> cities;

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public void addCity(City city){
		if(CollectionUtils.isEmpty(cities)){
			cities = new ArrayList<City>();
		}
		cities.add(city);
	}
}
