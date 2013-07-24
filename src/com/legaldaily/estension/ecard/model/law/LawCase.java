package com.legaldaily.estension.ecard.model.law;

import java.util.Date;

import com.google.code.morphia.annotations.Embedded;
import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.user.User;

public class LawCase extends EcardModel {
	private int caseId;
	private User user;
	private String title;
	private LawCategory category;
	private Province province;
	private City city;
	private String location;
	private String price;
	private String offer;
	private String description;
	//备注
	private String memo;
	private String validity;
	
	private Date expireDate;
	private int ispub;
	private int pubchars;
	private String contactName;
	private String contactEmail;
	private String contactTelephone;
	private String contactMobile;
	private String contactFax;
	private String contactAddress;
	private String contactPostcode;
	
	private Date submitDate;
	private String caseUserIp;
	private int isEnd;
	private String pubState;
	private int isDel;
	private int isCheck;
	
	public int getCaseId() {
		return caseId;
	}
	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	public User getUser() {
		if(user == null)
			user = new User();
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LawCategory getCategory() {
		if(category == null)
			category = new LawCategory();
		return category;
	}
	public void setCategory(LawCategory category) {
		this.category = category;
	}
	public Province getProvince() {
		if(province == null)
			province = new Province();
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public City getCity() {
		if(city == null)
			city = new City();
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public int getIspub() {
		return ispub;
	}
	public void setIspub(int ispub) {
		this.ispub = ispub;
	}
	public int getPubchars() {
		return pubchars;
	}
	public void setPubchars(int pubchars) {
		this.pubchars = pubchars;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactTelephone() {
		return contactTelephone;
	}
	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getContactFax() {
		return contactFax;
	}
	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getContactPostcode() {
		return contactPostcode;
	}
	public void setContactPostcode(String contactPostcode) {
		this.contactPostcode = contactPostcode;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getCaseUserIp() {
		return caseUserIp;
	}
	public void setCaseUserIp(String caseUserIp) {
		this.caseUserIp = caseUserIp;
	}
	public int getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}
	public String getPubState() {
		return pubState;
	}
	public void setPubState(String pubState) {
		this.pubState = pubState;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public int getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

}
