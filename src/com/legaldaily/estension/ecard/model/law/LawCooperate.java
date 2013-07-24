package com.legaldaily.estension.ecard.model.law;

import java.util.Date;

import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.user.User;

public abstract class LawCooperate extends EcardModel  {
	protected int cooperateId;
	protected User user;
	protected Province province;
	protected City city;
	//律师执业证号或律所执照号
	protected String license;
	protected String telephone;
	protected String email;
	protected String homepage;
	protected Date regtime;
	protected String introduction;
	
	public int getCooperateId() {
		return cooperateId;
	}
	public void setCooperateId(int cooperateId) {
		this.cooperateId = cooperateId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public Date getRegtime() {
		return regtime;
	}
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
