package com.legaldaily.estension.ecard.view.adapt.php;

import java.util.List;
import java.util.Map;

import com.fzw.Globals;
import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.domain.context.AreaContext;
import com.legaldaily.estension.ecard.domain.context.ProvinceAreaContext;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.repository.AreaRepository;
import com.legaldaily.estension.ecard.service.command.AreaCommands;

public class PHPAreaAdapter extends PHPAdapter {

	@Override
	public Object adapt(String methodName,Map<String, String> methodParam, Object returnValue) {
		if(returnValue == null){
			LogUtils.warn("No result in method\" [ "+methodName+" ]\"");
			return returnValue;
		}
		if(methodName.equals(AreaCommands.PROVINCE_LIST)){
			returnValue = adaptProvinceListWithoutOrder(returnValue);
		}else if (methodName.equals(AreaCommands.PROVINCE_GET)) {
			returnValue = adaptProvince(returnValue);
		}else if (methodName.equals(AreaCommands.CITY_LIST)) {
			returnValue = adaptCityList(returnValue);
		}
		return returnValue;
	}

	private Object adaptCityList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			list.set(i, adaptCityInfo(list.get(i)));
		}
		return list;
	}
	private Object adaptCityInfo(Object returnValue) {
		City city = (City) returnValue;
		PHPCityModel model = new PHPCityModel();
		model.setC_id(city.getAreaId());
		model.setC_name(city.getAreaName());
		return model;
	}

	private Object adaptProvince(Object returnValue) {
		Province province = (Province) returnValue;
		PHPProvinceModel model = new PHPProvinceModel();
		model.setP_name(province.getAreaName());
		model.setP_id(province.getAreaId());
		return model;
	}

	private Object adaptProvinceListWithoutOrder(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			Province province = (Province) list.get(i);
			PHPProvinceModel model = new PHPProvinceModel();
			model.setP_id(province.getAreaId());
			model.setP_name(province.getAreaName());
			list.set(i, model);
		}
		return returnValue;
	}
	private Object adaptProvinceListWithOrder(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			Province province = (Province) list.get(i);
			PHPProvinceModel model = new PHPProvinceModel();
			model.setP_id(province.getAreaId());
			model.setP_name(province.getAreaName());
			AreaContext context = new ProvinceAreaContext((AreaRepository) Globals.getBean("areaRepository"));
			context.setArea(province);
			context.mixOrder2Area();
			AreaContext.addAreaToContext(province, context);
			model.setOrderby(AreaContext.getAreaOrder(province));
			list.set(i, model);
		}
		return returnValue;
	}

}
class PHPProvinceModel{
	private int p_id,orderby;
	private String p_name;
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public int getOrderby() {
		return orderby;
	}
	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
}

class PHPCityModel{
	private int c_id;
	private String c_name;
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
}