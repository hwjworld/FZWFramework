package com.legaldaily.estension.ecard.domain.context;

import java.util.HashMap;
import java.util.Map;

import com.legaldaily.estension.ecard.model.area.Area;

/**
 * 由于职责简单，本context既充当context对象也充当contextManager 	
 * @author hwj
 *
 */
public abstract class AreaContext {
	
	/**
	 * 地区context，维护加入本环境中的地区
	 * <areaid, area>
	 */
	private static final Map<Area, AreaContext> contextMap = new HashMap<Area, AreaContext>();
	private Area area;
	private int order;
	
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	//put area to current context
	public static void addAreaToContext(Area area,AreaContext context){
		if(area != null)
			contextMap.put(area, context);
	}
	//remove an area from current context
	public static void removeAreaFromContext(Area area) {
		contextMap.remove(area);
	}
	
	/**
	 * 分配给当前context一个order,即给area一个order
	 * (order表从相应repository中取)
	 */
	public abstract void mixOrder2Area();
	
	public int getAreaOrder(){
		mixOrder2Area();
		return order;
	}
	public static int getAreaOrder(Area area){
		return contextMap.get(area).getOrder();
	}
}
