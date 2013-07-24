package com.fzw.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.terracotta.license.util.Base64;

import com.fzw.Constants;
import com.fzw.connection.FZWService;
import com.fzw.extension.Extension;
import com.fzw.extension.ExtensionManager;
import com.fzw.view.ResultAdapter;


public class ConnectionMessage {
	
	private static final String MESSAGE_TYPE_GET = "01";
	private static final String MESSAGE_TYPE_JSON = "02";
	
	private String messageStr = null;
	private String messageType = null;
	private String messageName;
	private Extension extension = null;

	private Map<String, String> paramMap = new HashMap<String, String>();
	/**
	 * 最终返回的json格式消息
	 * 
	 */
	private String resultValue;
	/**
	 * 供代理类处理
	 */
	private ResultAdapter resultAdapter;

	public static ConnectionMessage buildMessage(String string) {
		ConnectionMessage message = null;
		if(!StringUtils.isBlank(string)){
			message = new ConnectionMessage();
			message.setMessageStr(string);
			message.setMessage();
		}
		return message;
	}
	
	
	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void addParamMap(String paramName, String paramValue) {
		paramMap.put(paramName, paramValue);
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getMessageStr() {
		StringBuilder builder = new StringBuilder();
		builder.append(messageName);
		if(MapUtils.isNotEmpty(paramMap)){
			builder.append("?");
		}
		Iterator it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			Object object = (Object) it.next();
			if("messageName".equals(object.toString()))
				continue;
			builder.append("&").append(object.toString()).append("=").append(paramMap.get(object));
		}
//		return messageStr;
		return builder.toString();
	}

	public void setMessageStr(String messageStr) {
		this.messageType = StringUtils.substring(messageStr, 0, 2);
//		this.messageStr = messageStr;
		this.messageStr = StringUtils.substring(messageStr, 2);
	}

	public ResultAdapter getResultAdapter() {
		return resultAdapter;
	}

	public void setResultAdapter(ResultAdapter resultAdapter) {
		this.resultAdapter = resultAdapter;
	}
	
	/**
	 * 从消息格式的串中取值
	 * abc=123&test=true&ttf=ffff
	 * get('abc') => [123]
	 * get('test') => [true]
	 * get('abc','test') => [123],[true]
	 * @param messageString
	 * @param field
	 * @return
	 */
	public static String[] getValue(String messageString,String ...field) {
		String tmpmsg = "&"+messageString+"&";
		String rv[] = new String[field.length];
		for (int i = 0; i < rv.length; i++) {
			String tmp[] = StringUtils.substringsBetween(tmpmsg, "&"+field[i]+"=", "&");
			if(ArrayUtils.isEmpty(tmp)){
				rv[i] = "";
			}else{
				rv[i] = tmp[0];
			}
		}
		return rv;
	}/**
	 * 直接从paramMap的串中取值
	 * @param field
	 * @return
	 */
	public static String[] getValue(ConnectionMessage message, String ...field) {
		String rv[] = new String[field.length];
		for (int i = 0; i < rv.length; i++) {
			String value = message.getParamMap().get(field[i]);
			if(StringUtils.isBlank(value)){
				rv[i] = "";
			}else{
				rv[i] = value;
			}
		}
		return rv;
	}
	
	public static void main(String[] args) {
//		System.out.println(getValue("abc=123&test=true&ttf=ffff", "abc")[0]);
//		System.out.println(getValue("abc=123&test=true&ttf=ffff", "test")[0]);
//		System.out.println(getValue("abc=123&test=true&ttf=ffff", "ttf")[0]);
//		System.out.println(getValue("abc=123&test=true&ttf=ffff", "tt4f")[0]);[0]
		System.out.println(getValue("abc=123&test=true&ttf=ffff", "abc","test","tt4f","tt4f")[0]);
		for (String string : getValue("abc=123&test=true&ttf=ffff", "abc","test","tt4f","ttf")) {
			System.out.println(string);
		}
	}
	
	/**
	 * 
	 * @return false: unsupported message type.
	 */
	public boolean setMessage() {
		if(StringUtils.isBlank(messageStr)){
			return false;
		}
		if(MESSAGE_TYPE_GET.equals(messageType)){
			setTypeGetMessage();
		}
		if(MESSAGE_TYPE_JSON.equals(messageType)){
			setTypeJsonMessage();
		}
		if(StringUtils.isNotBlank(getValue(Constants.PROCESSEXTENSION))){
			setExtension(ExtensionManager.getExtension(getValue(Constants.PROCESSEXTENSION)));
		}else {
			setExtension(ExtensionManager.getExtensionByCommand(getMessageName()));
		}
		return true;
	}
	
	private void setTypeJsonMessage(){
		Map map = FZWService.gson.fromJson(messageStr, HashMap.class);
		setMessageName(map.get("messageName").toString());

        for (Iterator<?> i = map.keySet().iterator(); i.hasNext(); ) {
            Object key = i.next();
            Object value = map.get(key);
            addParamMap(String.valueOf(key), String.valueOf(value));
        }
	}
	private void setTypeGetMessage() {
		setMessageName(StringUtils.substringBefore(messageStr, "?"));

		String p[] = StringUtils.substringAfter(messageStr, "?").split("&");
		for(String pp: p){
			if(!StringUtils.isBlank(pp)){
				String key = StringUtils.substringBefore(pp, "=");
				String value = new String(Base64.decode(StringUtils.substringAfter(pp, "=")));
				addParamMap(key, value);
			}
		}
	}
	public String getValue(String name){
		return getValue(this, name)[0];
	}


	public Extension getExtension() {
		return extension;
	}


	public void setExtension(Extension extension) {
		this.extension = extension;
	}
}

