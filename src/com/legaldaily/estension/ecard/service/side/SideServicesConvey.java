package com.legaldaily.estension.ecard.service.side;

import java.util.List;

import sun.jdbc.odbc.OdbcDef;

import com.fzw.model.condition.ServiceCondition;
import com.fzw.service.ServiceConvey;
import com.legaldaily.estension.ecard.model.condition.SideCondition;
import com.legaldaily.estension.ecard.model.side.SVValue;
import com.legaldaily.estension.ecard.repository.SideRepository;

public class SideServicesConvey extends ServiceConvey<SideCondition> {

	SideService sideService = null;

	public SideServicesConvey(SideRepository sideRepository) {
		sideService = new SideServiceImpl(sideRepository);
	}

	public String getDefaultSetting(ServiceCondition condition) {
		return sideService.getDefaultSetting(condition.getValue("name"));
	}
	public List<SVValue> getAllDefaultSetting(ServiceCondition condition) {
		return sideService.getAllDefaultSetting();
	}
	public boolean writeLog(ServiceCondition condition){
		int userid = condition.getIntValue("userid");
		String userip = condition.getValue("userip");
		int objid = condition.getIntValue("objid");
		String action = condition.getValue("action");
		String category = condition.getValue("category");
		return sideService.writeLog(userid,userip,objid,action,category);
	}
	public boolean pv(ServiceCondition condition){
		return sideService.pv(condition.getValue("date"));
	}
}
