package com.legaldaily.estension.ecard.view.adapt.php;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;
import com.legaldaily.estension.ecard.service.command.LawCommands;

public class PHPLawAdapter extends PHPAdapter {

	@Override
	public Object adapt(String methodName,Map<String, String> methodParam, Object returnValue) {
		if(returnValue == null){
			LogUtils.warn("No result in method\" [ "+methodName+" ]\"");
			return returnValue;
		}
		if (methodName.equals(LawCommands.GET_AVAILABLE_LAWCASES)||
				methodName.equals(LawCommands.GET_WAITCHECK_LAWCASES)||
				methodName.equals(LawCommands.GET_ALL_LAWCASES)) {
			returnValue = adaptLawCase(returnValue);
		}else if (methodName.equals(LawCommands.GET_LAW_CATEGORIES)) {
			returnValue = adaptLawCategory(returnValue);
		}else if (methodName.equals(LawCommands.GET_LAWOFFICE_RANKING)) {
			returnValue = adaptLawOfficeRanking(returnValue);
		}else if (methodName.equals(LawCommands.GET_LAWOFFICE_PARTNER)) {
			returnValue = adaptLawOfficeList(returnValue);
		}else if (methodName.equals(LawCommands.GET_LAWOFFICE_BY_USERID)) {
			returnValue = adaptLawOfficeInfo(returnValue);
		}else if (methodName.equals(LawCommands.GET_LAWYER)||methodName.equals(LawCommands.GET_LAWYER_BYUSERID)) {
			returnValue = adaptLawyer(returnValue);		
		}else if (methodName.equals(LawCommands.GET_LAWYER_BYUSERID)) {
			returnValue = adaptLawyer(returnValue);		
		}else if (methodName.equals(LawCommands.GET_LAWCASE)) {
			returnValue = adaptLawCaseInfo(returnValue);
		}
		return returnValue;
	}

	private Object adaptLawyerList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			list.set(i, adaptLawyer(list.get(i)));
		}
		return list;
	}
	private Object adaptLawyer(Object returnValue) {
		Lawyer lawyer = (Lawyer) returnValue;
		PHPLawyerModel model = new PHPLawyerModel();
		model.setC_name(lawyer.getCity().getAreaName());
		model.setHeadpic(lawyer.getUser().getHeadpic());
		model.setLawyer_city(lawyer.getCity().getAreaId());
		model.setLawyer_province(lawyer.getProvince().getAreaId());
		model.setNick_name(lawyer.getUser().getNickname());
		if(lawyer.getLawOffice()!=null){
			model.setOffice_name(lawyer.getLawOffice().getUser().getNickname());
			model.setOffice_uid(lawyer.getLawOffice().getUser().getUid());
			model.setLawoffice_id(lawyer.getLawOffice().getCooperateId());
		}
		model.setP_name(lawyer.getProvince().getAreaName());
		model.setLawyer_code(lawyer.getLicense());
		model.setLawyer_tel(lawyer.getTelephone());
		model.setLawyer_specialty(lawyer.getSpecialty());
		model.setLawyer_email(lawyer.getEmail());
		model.setLawyer_summary(lawyer.getIntroduction());
		return model;
	}

	private Object adaptLawOfficeInfo(Object returnValue) {
		LawOffice lawOffice = (LawOffice) returnValue;
		PHPLawOfficeModel model = new PHPLawOfficeModel();
		model.setBusiness_code(lawOffice.getLicense());
		model.setID(lawOffice.getCooperateId());
		model.setLaw_addr(lawOffice.getAddress());
		model.setLaw_boss(lawOffice.getLegalPerson());
		model.setLaw_city(lawOffice.getCity().getAreaId());
		model.setLaw_email(lawOffice.getEmail());
		model.setLaw_province(lawOffice.getProvince().getAreaId());
		model.setLaw_summary(lawOffice.getIntroduction());
		model.setLaw_tel(lawOffice.getTelephone());
		model.setLaw_time(getDateString(lawOffice.getRegtime()));
		model.setLaw_url(lawOffice.getHomepage());
		model.setNick_name(lawOffice.getUser().getNickname());
		model.setPost_no(lawOffice.getPostCode());
		model.setUser_id(lawOffice.getUser().getUid());
		return model;
	}
	private Object adaptLawOfficeList(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			list.set(i, adaptLawOfficeInfo(list.get(i)));
		}
		return returnValue;
	}

	private Object adaptLawOfficeRanking(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			Map<LawOffice, Integer> map = (Map<LawOffice, Integer>) list.get(i);
			PHPLawOfficeRankingModel model = new PHPLawOfficeRankingModel();
			LawOffice office = map.keySet().iterator().next();
			model.setID(office.getCooperateId());
			model.setNick_name(office.getUser().getNickname());
			model.setScore(map.get(office));
			model.setU_id(office.getUser().getUid()); 
			list.set(i, model);
		}
		return returnValue;
	}

	private Object adaptLawCategory(Object returnValue) {
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			LawCategory lawCategory = (LawCategory) list.get(i);
			PHPLawCategoryModel model = new PHPLawCategoryModel();
			model.setG_id(lawCategory.getCatId());
			model.setG_title(lawCategory.getCatName());
			model.setP_id(lawCategory.getParentCategory().getCatId());
			list.set(i, model);
		}
		return returnValue;
	}

	private Object adaptLawCase(Object returnValue) {
		List list = (List) returnValue;
//		List<PHPLawcaseModel> adaptrv = new ArrayList<PHPLawcaseModel>(list.size());
		for (int i = 0; i < list.size(); i++) {
//			LawCase lawCase = (LawCase) list.get(i);
//			PHPLawcaseModel model = new PHPLawcaseModel();
//			model.setAid(lawCase.getCaseId());
//			model.setCase_city(lawCase.getCity().getAreaName());
//			model.setCase_province(lawCase.getProvince().getAreaName());
//			model.setCase_submit_date(DateFormatUtils.format(
//					lawCase.getSubmitDate(), "yyyy-MM-dd HH:mm:ss"));
//			model.setCase_title(lawCase.getTitle());
//			model.setCase_value(lawCase.getPrice());
//			list.set(i, model);
			try{
				list.set(i, adaptLawCaseInfo(list.get(i)));
			}catch (Exception e) {
				LogUtils.error("an error in apateLawCase");
			}
		}
		// list.clear();
		// list.addAll(adaptrv);
		return returnValue;
	}

	private Object adaptLawCaseInfo(Object returlValue) {

		LawCase lawCase = (LawCase) returlValue;
		PHPLawcaseModel model = new PHPLawcaseModel();
		model.setAid(lawCase.getCaseId());
		try{
			model.setCase_city(lawCase.getCity().getAreaName());	
		}catch (Exception e) {
		}
		try{
			model.setCase_province(lawCase.getProvince().getAreaName());	
		}catch (Exception e) {
		}
		model.setCase_submit_date(getDateString(lawCase.getSubmitDate()));
		model.setCase_title(lawCase.getTitle());
		model.setCase_value(lawCase.getPrice());

		model.setCase_offer(lawCase.getOffer());
		model.setCase_locate(lawCase.getLocation());
		model.setCase_contact_name(lawCase.getContactName());
		model.setCase_contact_address(lawCase.getContactAddress());
		model.setCase_mem(lawCase.getMemo());
		model.setCase_desc(lawCase.getDescription());
		model.setCase_cate(lawCase.getCategory().getCatId());
		model.setCase_provinceid(lawCase.getProvince().getAreaId());
		model.setCase_cityid(lawCase.getCity().getAreaId());
		model.setCase_pubchars(lawCase.getPubchars());
		model.setCase_limit(lawCase.getValidity());
		model.setCase_contact_tele(lawCase.getContactTelephone());
		model.setCase_contact_postcode(lawCase.getContactPostcode());
		model.setCase_contact_fax(lawCase.getContactFax());
		model.setCase_contact_email(lawCase.getContactEmail());
		model.setCase_contact_mobile(lawCase.getContactMobile());
		model.setDel_flag(String.valueOf(lawCase.getIsDel()));
		model.setPub_flag(String.valueOf(lawCase.getIspub()));
		model.setCheck_flag(String.valueOf(lawCase.getIsCheck()));
		
		return model;
	}
}
class PHPLawcaseModel{
	private int aid,case_cate,case_provinceid,case_cityid,case_pubchars;
	private String case_title,case_province,case_city,case_value,case_submit_date,
		case_offer,case_locate,case_contact_name,case_contact_address,case_mem,case_desc,case_serial_num
		,case_limit,case_contact_tele,case_contact_postcode,case_contact_fax,case_contact_email
		,case_contact_mobile,del_flag,pub_flag,check_flag;
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getCase_title() {
		return case_title;
	}
	public void setCase_title(String case_title) {
		this.case_title = case_title;
	}
	public String getCase_province() {
		return case_province;
	}
	public void setCase_province(String case_province) {
		this.case_province = case_province;
	}
	public String getCase_city() {
		return case_city;
	}
	public void setCase_city(String case_city) {
		this.case_city = case_city;
	}
	public String getCase_value() {
		return case_value;
	}
	public void setCase_value(String case_value) {
		this.case_value = case_value;
	}
	public String getCase_submit_date() {
		return case_submit_date;
	}
	public void setCase_submit_date(String case_submit_date) {
		this.case_submit_date = case_submit_date;
	}
	public String getCase_offer() {
		return case_offer;
	}
	public void setCase_offer(String case_offer) {
		this.case_offer = case_offer;
	}
	public String getCase_locate() {
		return case_locate;
	}
	public void setCase_locate(String case_locate) {
		this.case_locate = case_locate;
	}
	public String getCase_contact_name() {
		return case_contact_name;
	}
	public void setCase_contact_name(String case_contact_name) {
		this.case_contact_name = case_contact_name;
	}
	public String getCase_contact_address() {
		return case_contact_address;
	}
	public void setCase_contact_address(String case_contact_address) {
		this.case_contact_address = case_contact_address;
	}
	public String getCase_mem() {
		return case_mem;
	}
	public void setCase_mem(String case_mem) {
		this.case_mem = case_mem;
	}
	public String getCase_desc() {
		return case_desc;
	}
	public void setCase_desc(String case_desc) {
		this.case_desc = case_desc;
	}
	public String getCase_serial_num() {
		return case_serial_num;
	}
	public void setCase_serial_num(String case_serial_num) {
		this.case_serial_num = case_serial_num;
	}
	public int getCase_cate() {
		return case_cate;
	}
	public void setCase_cate(int case_cate) {
		this.case_cate = case_cate;
	}
	public int getCase_provinceid() {
		return case_provinceid;
	}
	public void setCase_provinceid(int case_provinceid) {
		this.case_provinceid = case_provinceid;
	}
	public int getCase_cityid() {
		return case_cityid;
	}
	public void setCase_cityid(int case_cityid) {
		this.case_cityid = case_cityid;
	}
	public String getCase_limit() {
		return case_limit;
	}
	public void setCase_limit(String case_limit) {
		this.case_limit = case_limit;
	}
	public String getCase_contact_tele() {
		return case_contact_tele;
	}
	public void setCase_contact_tele(String case_contact_tele) {
		this.case_contact_tele = case_contact_tele;
	}
	public String getCase_contact_postcode() {
		return case_contact_postcode;
	}
	public void setCase_contact_postcode(String case_contact_postcode) {
		this.case_contact_postcode = case_contact_postcode;
	}
	public String getCase_contact_fax() {
		return case_contact_fax;
	}
	public void setCase_contact_fax(String case_contact_fax) {
		this.case_contact_fax = case_contact_fax;
	}
	public String getCase_contact_email() {
		return case_contact_email;
	}
	public void setCase_contact_email(String case_contact_email) {
		this.case_contact_email = case_contact_email;
	}
	public String getCase_contact_mobile() {
		return case_contact_mobile;
	}
	public void setCase_contact_mobile(String case_contact_mobile) {
		this.case_contact_mobile = case_contact_mobile;
	}
	public int getCase_pubchars() {
		return case_pubchars;
	}
	public void setCase_pubchars(int case_pubchars) {
		this.case_pubchars = case_pubchars;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getPub_flag() {
		return pub_flag;
	}
	public void setPub_flag(String pub_flag) {
		this.pub_flag = pub_flag;
	}
	public String getCheck_flag() {
		return check_flag;
	}
	public void setCheck_flag(String check_flag) {
		this.check_flag = check_flag;
	}
}

class PHPLawCategoryModel{
	private int g_id,p_id;
	private String g_title;
	public int getG_id() {
		return g_id;
	}
	public void setG_id(int g_id) {
		this.g_id = g_id;
	}
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public String getG_title() {
		return g_title;
	}
	public void setG_title(String g_title) {
		this.g_title = g_title;
	}
}
class PHPLawOfficeRankingModel{
	private int ID, score,u_id;
	private String nick_name;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	
}
class PHPLawyerModel{
	private String nick_name,headpic,p_name,c_name,office_name,lawyer_code,lawyer_tel,lawyer_specialty,lawyer_email,lawyer_summary;
	private int lawyer_province,lawyer_city,office_uid,u_id,lawoffice_id;
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getOffice_name() {
		return office_name;
	}
	public void setOffice_name(String office_name) {
		this.office_name = office_name;
	}
	public int getLawyer_province() {
		return lawyer_province;
	}
	public void setLawyer_province(int lawyer_province) {
		this.lawyer_province = lawyer_province;
	}
	public int getLawyer_city() {
		return lawyer_city;
	}
	public void setLawyer_city(int lawyer_city) {
		this.lawyer_city = lawyer_city;
	}
	public int getOffice_uid() {
		return office_uid;
	}
	public void setOffice_uid(int office_uid) {
		this.office_uid = office_uid;
	}
	public String getLawyer_code() {
		return lawyer_code;
	}
	public void setLawyer_code(String lawyer_code) {
		this.lawyer_code = lawyer_code;
	}
	public String getLawyer_tel() {
		return lawyer_tel;
	}
	public void setLawyer_tel(String lawyer_tel) {
		this.lawyer_tel = lawyer_tel;
	}
	public String getLawyer_specialty() {
		return lawyer_specialty;
	}
	public void setLawyer_specialty(String lawyer_specialty) {
		this.lawyer_specialty = lawyer_specialty;
	}
	public String getLawyer_email() {
		return lawyer_email;
	}
	public void setLawyer_email(String lawyer_email) {
		this.lawyer_email = lawyer_email;
	}
	public String getLawyer_summary() {
		return lawyer_summary;
	}
	public void setLawyer_summary(String lawyer_summary) {
		this.lawyer_summary = lawyer_summary;
	}
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public int getLawoffice_id() {
		return lawoffice_id;
	}
	public void setLawoffice_id(int lawoffice_id) {
		this.lawoffice_id = lawoffice_id;
	}
}
class PHPLawOfficeModel{
	private int ID, user_id,law_province,law_city;
	private String law_boss, business_code,	law_addr, post_no,law_tel,law_email,law_url,law_time,law_summary,nick_name;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getLaw_province() {
		return law_province;
	}
	public void setLaw_province(int law_province) {
		this.law_province = law_province;
	}
	public int getLaw_city() {
		return law_city;
	}
	public void setLaw_city(int law_city) {
		this.law_city = law_city;
	}
	public String getLaw_boss() {
		return law_boss;
	}
	public void setLaw_boss(String law_boss) {
		this.law_boss = law_boss;
	}
	public String getBusiness_code() {
		return business_code;
	}
	public void setBusiness_code(String business_code) {
		this.business_code = business_code;
	}
	public String getLaw_addr() {
		return law_addr;
	}
	public void setLaw_addr(String law_addr) {
		this.law_addr = law_addr;
	}
	public String getPost_no() {
		return post_no;
	}
	public void setPost_no(String post_no) {
		this.post_no = post_no;
	}
	public String getLaw_tel() {
		return law_tel;
	}
	public void setLaw_tel(String law_tel) {
		this.law_tel = law_tel;
	}
	public String getLaw_email() {
		return law_email;
	}
	public void setLaw_email(String law_email) {
		this.law_email = law_email;
	}
	public String getLaw_url() {
		return law_url;
	}
	public void setLaw_url(String law_url) {
		this.law_url = law_url;
	}
	public String getLaw_time() {
		return law_time;
	}
	public void setLaw_time(String law_time) {
		this.law_time = law_time;
	}
	public String getLaw_summary() {
		return law_summary;
	}
	public void setLaw_summary(String law_summary) {
		this.law_summary = law_summary;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

}