package com.legaldaily.estension.ecard.utils;

import com.fzw.utils.BitUtils;
import com.legaldaily.estension.ecard.model.user.User;

public class PermissionUtil {
	
	public static boolean isLawyer(User user){
		if(user == null)
			return false;
		//25是抄的php的程序，不知道什么意思,应该是律师的回答， 不需要审核的
		return ua_test(user, 25);
	}
	/**
	 *  判断用户行功能使用权限
	 * @param user 用户
	 * @param u_code 用户行为功能代码；
	 * @return
	 */
	public static boolean ua_test(User user, int u_code) {
		if (user == null || u_code == 0 || user.getUserGroup()== null)
			return false;
		if (user.getUserGroup().getGroupid() == 1) {
			return true;
		}
		// 得到用户的行为权限
		String ua_ruler = user.getUserGroup().getU_rulers();
		// 得到用户的行为权限
		String a_code = ruler_get(u_code);
		if (BitUtils.and(ua_ruler, a_code).equals( a_code) ){
			return true;
		} else {
			return false;
		}
	}

	// 得到权限字数串函数
	private static String ruler_get(int no) {
		String temp = "";
		String ruler_str = "";
		for (int l = 0; l < no - 1; l++) {
			temp = temp + "0";
		}
		ruler_str = temp + "1";
		temp = "";
		for (int l = no; l < 255; l++) {
			ruler_str = ruler_str + "0";
		}
		return ruler_str;
	}

}