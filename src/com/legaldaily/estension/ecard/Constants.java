package com.legaldaily.estension.ecard;

import java.util.HashMap;
import java.util.Map;

import com.fzw.extension.Extension;
import com.fzw.extension.ExtensionManager;

public class Constants {

	public static final String EXTENSIONNAME = "ecard";
	// sv_value
	public static final int DEFAULT_NETFRIEND = 9;
	public static final int DEFAULT_LAWYERS = 6;
	public static final int GOOD_ANSWER_SCORE = 20;
	
	public static final Extension EXTENSION = ExtensionManager.getExtension(EXTENSIONNAME);
	
	//sv _value default settings
	private static final Map<String, String> DEFAULT_SETTINGS = new HashMap<String, String>();
	public static String getDefaultSetting(String settingname){
		return DEFAULT_SETTINGS.get(settingname);
	}
	public static void setDefaultSetting(String settingname,String value){
		DEFAULT_SETTINGS.put(settingname, value);
	}
	
}
