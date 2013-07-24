package com.fzw.utils;

public class BitUtils {

	/**
	 * 两个二进制字符串进行与的运算
	 * 同php的字符串的＆操作
	 * @return
	 */
	public static String and(String a, String b) {
		char aa[] = a.toCharArray();
		char bb[] = b.toCharArray();
		char min[] = null;
		char max[] = null;
		StringBuilder builder = new StringBuilder();
		if(aa.length>bb.length){
			min = bb;
			max = aa;
		}else{
			min = aa;
			max = bb;
		}
		for (int i = 0; i < min.length; i++) {
			builder.append(Integer.valueOf(""+min[i], 2)&Integer.valueOf(""+max[i], 2));
		}
		return builder.toString();
	}
}
