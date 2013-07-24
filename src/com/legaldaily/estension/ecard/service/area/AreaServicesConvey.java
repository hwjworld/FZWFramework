package com.legaldaily.estension.ecard.service.area;

import java.util.List;

import com.fzw.service.ServiceConvey;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.condition.AreaCondition;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;
import com.legaldaily.estension.ecard.repository.AreaRepository;

public class AreaServicesConvey extends ServiceConvey<AreaCondition> {

	AreaServices areaServices = null;
	public AreaServicesConvey(AreaRepository areaRepository) {
		areaServices = new AreaServicesImpl(areaRepository);
	}
	public Province getProvince(EcardServiceCondition condition){
//		getCondition(condition).buildAreaCondition();
		return areaServices.getProvince(getCondition(condition).getProvinceid()[0]);
	}
	public City getCity(EcardServiceCondition condition){
//		getCondition(condition).();
		return areaServices.getCity(getCondition(condition).getCityid()[0]);
	}
	public List<City> listCity(EcardServiceCondition condition){
//		getCondition(condition).buildAreaCondition();
		return areaServices.listCity(getCondition(condition));
	}
	public List<Province> listProvince(EcardServiceCondition condition){
//		getCondition(condition).buildAreaCondition();
		return areaServices.listProvince(getCondition(condition));
	}
}
