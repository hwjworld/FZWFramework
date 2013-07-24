package com.legaldaily.estension.ecard.service.law;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import sun.jdbc.odbc.OdbcDef;

import com.fzw.model.ConnectionMessage;
import com.fzw.service.ServiceConvey;
import com.fzw.utils.DateUtil;
import com.fzw.utils.LogUtils;
import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;
import com.legaldaily.estension.ecard.model.condition.LawCondition;
import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.repository.LawRepository;

public class LawServicesConvey extends ServiceConvey<LawCondition> {

	LawServices lawServices = null;

	public LawServicesConvey(LawRepository lawRepository) {
		lawServices = new LawServicesImpl(lawRepository);
	}

	public List<LawCase> getAvailableLawCases(EcardServiceCondition condition) {
		return lawServices.getAvailableLawCases(getCondition(condition).getStartpos(), getCondition(condition).getCount());
	}

	public long getAvailableLawCasesCount(EcardServiceCondition condition) {
		return lawServices.getAvailableLawCasesCount();
	}

	public List<LawCase> getAllLawCases(EcardServiceCondition condition) {
		return lawServices.getAllLawCases(getCondition(condition).getStartpos(), getCondition(condition).getCount());
	}

	public long getAllLawCasesCount(EcardServiceCondition condition) {
		return lawServices.getAllLawCasesCount();
	}

	public List<LawCategory> getLawCategories(EcardServiceCondition condition) {
		return lawServices.getLawCategories(getCondition(condition));
	}

	public boolean updateLawCategory(EcardServiceCondition condition) {
		String name = condition.getValue("name");
		int parentid = condition.getIntValue("parentcatid");
		int catid = condition.getIntValue("catid");
		return lawServices.updateLawCategory(name, parentid, catid);
	}

	public boolean addLawCategory(EcardServiceCondition condition) {
		String name = condition.getValue("name");
		int parentid = condition.getIntValue("parentcatid");
		return lawServices.addLawCategory(name, parentid);
	}

	public List<Map<LawOffice, Integer>> getLawOfficeRanking(EcardServiceCondition condition) {
		return lawServices.getLawOfficeRanking(getCondition(condition).getCount());
	}

	public List<LawOffice> getLawOfficePartner(EcardServiceCondition condition) {
		return lawServices.getLawOfficePartner();
	}

	public Lawyer getLawyerByUserId(EcardServiceCondition condition) {
		return lawServices.getLawyerByUserId(getCondition(condition).getId()[0]);
	}

	public Lawyer getLawyer(EcardServiceCondition condition) {
		return lawServices.getLawyer(getCondition(condition).getId()[0]);
	}

	public List<LawCase> getWaitCheckLawCases(EcardServiceCondition condition) {
		return lawServices.getWaitCheckLawCases(getCondition(condition).getStartpos(), getCondition(condition).getCount());
	}

	public long getWaitCheckLawCasesCount(EcardServiceCondition condition) {
		return lawServices.getWaitCheckLawCasesCount();
	}

	public List<Boolean> passLawCase(EcardServiceCondition condition) {
		return lawServices.passLawCase(getCondition(condition).getId());
	}

	public List<Boolean> deleteLawCase(EcardServiceCondition condition) {
		return lawServices.deleteLawCase(getCondition(condition).getId());
	}

	public boolean editLawCase(EcardServiceCondition condition) {
		String[] fields = ConnectionMessage.getValue(condition.getMessage(), new String[] { "case_title", "case_cate", "case_province", "case_city",
				"case_locate", "case_value", "case_offer", "case_desc", "case_mem", "case_limit", "case_todate", "case_ispub", "case_pubchars",
				"case_contact_name", "case_contact_email", "case_contact_tele", "case_contact_mobile", "case_contact_fax", "case_contact_address",
				"case_contact_postcode", "aid" });

		LawCase lawCase = new LawCase();
		lawCase.setTitle(fields[0]);
		lawCase.getCategory().setCatId(StringValueUtils.getInt(fields[1]));
		lawCase.getProvince().setAreaId(StringValueUtils.getInt(fields[2]));
		lawCase.getCity().setAreaId(StringValueUtils.getInt(fields[3]));
		lawCase.setLocation(fields[4]);
		lawCase.setPrice(fields[5]);
		lawCase.setOffer(fields[6]);
		lawCase.setDescription(fields[7]);
		lawCase.setMemo(fields[8]);
		lawCase.setValidity(fields[9]);
		try {
			// TODO CUO EL
			lawCase.setExpireDate(DateUtil.parseDate(fields[10]));
		} catch (ParseException e) {
			LogUtils.warn("No expireDate when add new LawCase, use default value: 1 year.");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
			lawCase.setExpireDate(calendar.getTime());
		}
		lawCase.setIspub(StringValueUtils.getInt(fields[11]));
		lawCase.setPubchars(StringValueUtils.getInt(fields[12]));
		lawCase.setContactName(fields[13]);
		lawCase.setContactEmail(fields[14]);
		lawCase.setContactTelephone(fields[15]);
		lawCase.setContactMobile(fields[16]);
		lawCase.setContactFax(fields[17]);
		lawCase.setContactAddress(fields[18]);
		lawCase.setContactPostcode(fields[19]);
		lawCase.setCaseId(StringValueUtils.getInt(fields[20]));

		return lawServices.editLawCase(lawCase);
	}

	public int addLawCase(EcardServiceCondition condition) {
		String[] fields = ConnectionMessage.getValue(condition.getMessage(), new String[] { "u_id", "case_title", "case_cate", "case_province",
				"case_city", "case_locate", "case_value", "case_offer", "case_desc", "case_mem", "case_limit", "case_todate", "case_ispub",
				"case_pubchars", "case_contact_name", "case_contact_email", "case_contact_tele", "case_contact_mobile", "case_contact_fax",
				"case_contact_address", "case_contact_postcode", "case_user_ip" });
		LawCase lawCase = new LawCase();
		lawCase.getUser().setUid(StringValueUtils.getInt(fields[0]));
		lawCase.setTitle(fields[1]);
		lawCase.getCategory().setCatId(StringValueUtils.getInt(fields[2]));
		lawCase.getProvince().setAreaId(StringValueUtils.getInt(fields[3]));
		lawCase.getCity().setAreaId(StringValueUtils.getInt(fields[4]));
		lawCase.setLocation(fields[5]);
		lawCase.setPrice(fields[6]);
		lawCase.setOffer(fields[7]);
		lawCase.setDescription(fields[8]);
		lawCase.setMemo(fields[9]);
		lawCase.setValidity(fields[10]);
		try {
			lawCase.setExpireDate(DateUtil.parseDate(fields[11]));
		} catch (ParseException e) {
			LogUtils.warn("No expireDate when add new LawCase, use default value: 1 year.");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
			lawCase.setExpireDate(calendar.getTime());
		}
		lawCase.setIspub(StringValueUtils.getInt(fields[12]));
		lawCase.setPubchars(StringValueUtils.getInt(fields[13]));
		lawCase.setContactName(fields[14]);
		lawCase.setContactEmail(fields[15]);
		lawCase.setContactTelephone(fields[16]);
		lawCase.setContactMobile(fields[17]);
		lawCase.setContactFax(fields[18]);
		lawCase.setContactAddress(fields[19]);
		lawCase.setContactPostcode(fields[20]);
		lawCase.setCaseUserIp(fields[21]);
		return lawServices.addLawCase(lawCase);
	}
	
	public int addLawyer(EcardServiceCondition condition){
		Lawyer lawyer = new Lawyer();
		User user = new User();
		user.setUid(condition.getIntValue("userid"));
		lawyer.setUser(user);
		LawOffice office = new LawOffice();
		office.setCooperateId(condition.getIntValue("lawofficeid"));
		lawyer.setLawOffice(office);
		Province province = new Province();
		province.setAreaId(condition.getIntValue("provinceid"));
		City city = new City();
		city.setAreaId(condition.getIntValue("cityid"));
		lawyer.setProvince(province);
		lawyer.setCity(city);
		lawyer.setEmail(condition.getValue("email"));
		lawyer.setHomepage(condition.getValue("homepage"));
		lawyer.setLicense(condition.getValue("license"));
		lawyer.setTelephone(condition.getValue("tel"));
		lawyer.setSpecialty(condition.getValue("specialty"));
		lawyer.setIntroduction(condition.getValue("introduction"));
		return lawService.addLawyer(lawyer);
	}

	public List<LawCase> getPublishedLawCases(EcardServiceCondition condition) {
		LawCondition lawCondition = getCondition(condition);
		return lawServices.getPublishedLawCases(lawCondition.getStartpos(), lawCondition.getCount());
	}

	public LawCase getLawCase(EcardServiceCondition condition) {
		return lawServices.getLawCase(getCondition(condition).getId()[0]);
	}

	public LawOffice getLawOfficeByUserId(EcardServiceCondition condition) {
		return lawServices.getLawOfficeByUserId(getCondition(condition).getId()[0]);
	}
	
	public int saveLawOffice(EcardServiceCondition condition){
		LawOffice office = new LawOffice();
		User user = new User();
		user.setUid(condition.getIntValue("userid"));
		office.setUser(user);
		office.setLegalPerson(condition.getValue("legalperson"));
		office.setLicense(condition.getValue("license"));
		office.setAddress( condition.getValue("address"));
		office.setPostCode(condition.getValue("postcode"));
		Province province = new Province();
		province.setAreaId( condition.getIntValue("provinceid"));
		office.setProvince(province);
		City city = new City();
		city.setAreaId(condition.getIntValue("cityid"));
		office.setCity(city);
		office.setTelephone(condition.getValue("tel"));
		office.setEmail(condition.getValue("email"));
		office.setHomepage(condition.getValue("homepage"));
		office.setIntroduction(condition.getValue("introduction"));
		office.setCooperateId(condition.getIntValue("officeid"));
		return lawServices.saveLawOffice(office);
	}
}
