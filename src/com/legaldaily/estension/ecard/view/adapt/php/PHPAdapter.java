package com.legaldaily.estension.ecard.view.adapt.php;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fzw.view.ResultAdapter;

/**
 * 修改返回对象组织成适应php页面显示的对象结构
 * 
 * @author hwj
 * 
 */
public abstract class PHPAdapter implements ResultAdapter {

	public static String getDateString(Date date) {
		if(date == null){
			return "0000-00-00 00:00:00";
		}
		return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
	}
}
