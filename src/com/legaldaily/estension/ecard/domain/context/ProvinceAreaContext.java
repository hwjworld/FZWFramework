package com.legaldaily.estension.ecard.domain.context;

import com.legaldaily.estension.ecard.repository.AreaRepository;


public class ProvinceAreaContext extends AreaContext{
	
	AreaRepository areaRepository = null;
	
	public ProvinceAreaContext(AreaRepository areaRepository) {
		this.areaRepository = areaRepository;
	}
	
	@Override
	public void mixOrder2Area() {
		setOrder(areaRepository.getProvinceOrder(this.getArea().getAreaId()));
	}	
}
