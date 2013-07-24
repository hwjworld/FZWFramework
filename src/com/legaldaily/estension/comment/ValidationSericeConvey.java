package com.legaldaily.estension.comment;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.fzw.model.condition.ServiceCondition;
import com.fzw.service.ServiceConvey;
import com.legaldaily.estension.comment.yzm.YZMService;
import com.legaldaily.estension.comment.yzm.Yzm;

public class ValidationSericeConvey extends ServiceConvey<CommentCondition> {

	ValidationService validationService = new ValidationServiceImpl();
	YZMService yzmService = new YZMService();
	public HashMap valid(ServiceCondition condition) {
		String type = condition.getValue("type");
		String value = condition.getValue("value");
		String tpgroup = condition.getValue("tpgroup");
		if(StringUtils.isNotBlank(type)&& StringUtils.isNotBlank(value)){
			return validationService.valid(type, value, tpgroup);
		}else {
			HashMap map = new HashMap();
			map.put("count", "0");
			map.put("tptime", "");
			return map;
		}
	}

	public HashMap getIPCount(ServiceCondition condition) {
		return validationService.getIPCount(condition.getValue("ip"),condition.getValue("tpgroup"));
	}

	public HashMap getIDCardCount(ServiceCondition condition) {
		return validationService.getIPCount(condition.getValue("idcard"),condition.getValue("tpgroup"));
	}
	public boolean addIP(ServiceCondition condition) {
		String time = condition.getValue("time");
		return validationService.addIP(condition.getValue("ip"),condition.getValue("tpgroup"),time);
	}

	public boolean addIDCard(ServiceCondition condition) {
		String time = condition.getValue("time");
		return validationService.addIDCard(condition.getValue("idcard"),condition.getValue("tpgroup"),time);
	}
	public Yzm getYzm(ServiceCondition condition){
		return yzmService.getYzm();
	}
}
