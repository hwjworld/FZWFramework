package com.fzw.model.condition;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fzw.model.ConnectionMessage;
import com.fzw.utils.StringValueUtils;
import com.google.code.morphia.annotations.Entity;


public abstract class ServiceCondition {

	protected ConnectionMessage message = null;
	protected Map<String, String> params;

	public ServiceCondition(ConnectionMessage message) {
		this.message = message;
		this.params = message.getParamMap();
		buildCondition();
	}

	public ServiceCondition() {
		this.message = null;
		this.params = new HashMap<String, String>();
	}

	public abstract void buildCondition();

	/**
	 * 手动向参数map中添加参数,若已存在将不覆盖
	 * 
	 * @param key
	 * @param value
	 */
	public void addToParam(String key, String value) {
		if (!params.containsKey(key)) {
			params.put(key, value);
		}
	}

	public String getValue(String key){
		return getValue(key, "");
	}
	public String getValue(String key,String defaultValue){
		return params.containsKey(key)?params.get(key):defaultValue;		
	}
	public int getIntValue(String key){
		return StringValueUtils.getInt(StringUtils.trim(getValue(key)));
	}
	public long getLongValue(String key){
		return StringValueUtils.getLong(StringUtils.trim(params.get(key)));
	}

	public ConnectionMessage getMessage() {
		return message;
	}
}
