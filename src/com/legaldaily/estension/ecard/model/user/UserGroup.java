package com.legaldaily.estension.ecard.model.user;

import com.legaldaily.estension.ecard.model.EcardModel;

public class UserGroup extends EcardModel {
	private int groupid;
	private String groupname;
	//组用户权限
	private String u_rulers;
	//组程序权限
	private String s_rulers;
	
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getU_rulers() {
		return u_rulers;
	}
	public void setU_rulers(String u_rulers) {
		this.u_rulers = u_rulers;
	}
	public String getS_rulers() {
		return s_rulers;
	}
	public void setS_rulers(String s_rulers) {
		this.s_rulers = s_rulers;
	}
	
}
