package com.legaldaily.estension.ecard.service.law;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.fzw.Globals;
import com.fzw.domain.DomainMessage;
import com.fzw.utils.ListUtils;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.condition.LawCondition;
import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.repository.LawRepository;
import com.legaldaily.estension.ecard.repository.SideRepository;
import com.legaldaily.estension.ecard.utils.Comparators;

public class LawServicesImpl implements LawServices {

	LawRepository lawRepository = null;

	public LawServicesImpl(LawRepository lawRepository) {
		this.lawRepository = lawRepository;
	}
	public LawServicesImpl() {
		this.lawRepository = (LawRepository) Globals.getBean("lawRepository");
	}

	/*
	 * select aid,case_title,case_province,case_city,case_value,case_submit_date
	 * from cases where del_flag='0' and case_end_flag='0' order by
	 * case_submit_date desc limit 6
	 * 
	 * @see com.fzw.service.law.LawServices#getAvailableLawCases(int)
	 */
	@Override
	public List<LawCase> getAvailableLawCases(int startpos, int count) {
		List<LawCase> rvList = new ArrayList<LawCase>();
		;
		if (count <= 0) {
			return rvList;
		}
		List<LawCase> lawCases = lawRepository.getAllLawCases();
		for (int i = lawCases.size() - 1; i >= 0; i--) {
			LawCase lawCase = lawCases.get(i);
			if (lawCase.getIsDel() != 0 || lawCase.getIsEnd() != 0) {
				lawCases.remove(i);
			}
		}
		Collections.sort(lawCases, Comparators.LAWCASE_COMPARATOR_ASC_SUBMITDATE);
		rvList = (List<LawCase>) ListUtils.subList(lawCases, startpos, count);
		return rvList;
	}
	
	@Override
	public List<LawCase> getPublishedLawCases(int startpos, int count) {
		List<LawCase> rvList = new ArrayList<LawCase>();
		;
		if (count <= 0) {
			return rvList;
		}
		List<LawCase> lawCases = lawRepository.getAllLawCases();
		for (int i = lawCases.size() - 1; i >= 0; i--) {
			LawCase lawCase = lawCases.get(i);
			if ("publish".equals(lawCase.getPubState()) && lawCase.getIsCheck() == 1) {
				rvList.add(lawCase);
			}
		}
		Collections.sort(rvList, Comparators.LAWCASE_COMPARATOR_DESC_SUBMITDATE);
		rvList = (List<LawCase>) ListUtils.subList(rvList, startpos, count);
		return rvList;
	}

	@Override
	public List<LawCategory> getAllLawCategories() {
		return lawRepository.getAllLawCategories();
	}

	@Override
	public List<LawCategory> getLawCategories(LawCondition condition) {
		//is all
		if(condition.isAll()){
			return getAllLawCategories();
		}
		// list id
		List<LawCategory> rv = new ArrayList<LawCategory>();
		int ids[] = condition.getId();
		if (ArrayUtils.isNotEmpty(ids)) {
			for (int i = 0; i < ids.length; i++) {
				LawCategory lawCategory = lawRepository.getLawCategory(ids[i]);
				if(lawCategory != null){
					rv.add(lawCategory);
				}
			}
		}
		//list parentid
		ids = condition.getParentId();
		String name = condition.getValue("name");
		if(ArrayUtils.isNotEmpty(ids) || StringUtils.isNotBlank(name)){
			List<LawCategory> alLawCategories = lawRepository.getAllLawCategories();
			for (LawCategory lawCategory : alLawCategories) {
				if( ArrayUtils.contains(ids, lawCategory.getParentCategory().getCatId()) ||	lawCategory.getCatName().equals(name)){
					rv.add(lawCategory);
				}
			}
			
//			for (int i = 0; i < ids.length; i++) {
//				for (LawCategory lawCategory : alLawCategories) {
//					if(ArrayUtils.contains(ids, lawCategory.getParentCategory().getCatId())){
//						rv.add(lawCategory);
//					}
//				}
//			}
		}
		//by name
		
		//count
		if(condition.getCount() > 0){
			rv = (List<LawCategory>) ListUtils.subList(rv, 0, condition.getCount());
		}
		return rv;
	}

	@Override
	public List<Map<LawOffice, Integer>> getLawOfficeRanking(int count) {
		List<Map<LawOffice, Integer>> rvList = new ArrayList<Map<LawOffice,Integer>>();
		List<Map<LawOffice, Integer>> list = lawRepository.getLawOfficeRanking();
		for (Map<LawOffice, Integer> map : list) {
			if(count > 0 && rvList.size() >= count)
				break;
			LawOffice office = map.keySet().iterator().next();
			if(office.getUser().getUid()!=5 && office.getUser().getUid()!=160){
				rvList.add(map);
			}
		}
		return rvList;
	}

	@Override
	public List<LawOffice> getLawOfficePartner() {
		List<LawOffice> officeList = lawRepository.getAllLawOffices();
		Collections.sort(officeList, Comparators.LAWOFFICE_COMPARATOR_DESC_PROFINCEID);
		return officeList;
	}

	@Override
	public Lawyer getLawyerByUserId(int userId) {
		if(userId<=0)
			return null;
		Lawyer rvlawyer = null;
		List<Lawyer> lawyers = lawRepository.getAllLawyers();
		for (Lawyer lawyer : lawyers) {
			if(lawyer.getUser().getUid()==userId){
				rvlawyer = lawyer;
				break;
			}
		}
		return rvlawyer;
	}

	@Override
	public Lawyer getLawyer(int lawyerId) {
		if(lawyerId <= 0)
			return null;
		return lawRepository.getLawyer(lawyerId);
	}

	@Override
	public long getAvailableLawCasesCount() {
		return lawRepository.getAllLawCasesCount();
	}

	@Override
	public List<LawCase> getWaitCheckLawCases(int startpos, int count) {
		List<LawCase> rvList = new ArrayList<LawCase>();
		if (count <= 0) {
			return rvList;
		}
		List<LawCase> lawCases = lawRepository.getAllLawCases();
		for (int i = lawCases.size() - 1; i >= 0; i--) {
			LawCase lawCase = lawCases.get(i);
			if (lawCase.getIsCheck() == 0) {
				rvList.add(lawCase);
			}
		}
		Collections.sort(lawCases, Comparators.LAWCASE_COMPARATOR_ASC_SUBMITDATE);
		rvList = (List<LawCase>) ListUtils.subList(rvList, startpos, count);
		return rvList;
	}

	@Override
	public long getWaitCheckLawCasesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Boolean> passLawCase(int[] caseid) {
		for (int i : caseid) {
			LawCase lawCase = lawRepository.getLawCase(i);
			lawCase.setIsDel(0);
			lawCase.setIsCheck(1);
		}
		DomainMessage message = DOMAIN_EVENTS.passLawCase(caseid);
		if(message.isDone()){
			return (List<Boolean>) message.getEventResult();
		}else {
			List<Boolean> list = new ArrayList<Boolean>();
			for (int i : caseid) {
				list.add(true);
			}
			return list;
		}
	}

	@Override
	public List<Boolean> deleteLawCase(int[] caseid) {
		for (int i : caseid) {
			LawCase lawCase = lawRepository.getLawCase(i);
			lawCase.setIsDel(1);
			lawCase.setIsCheck(1);
		}
		DomainMessage message = DOMAIN_EVENTS.deleteLawCase(caseid);
		if(message.isDone()){
			return (List<Boolean>) message.getEventResult();
		}else {
			List<Boolean> list = new ArrayList<Boolean>();
			for (int i : caseid) {
				list.add(true);
			}
			return list;
		}
	}

	@Override
	public boolean editLawCase(LawCase lawCase) {
		return lawRepository.editLawCase(lawCase);
	}

	@Override
	public int addLawCase(LawCase lawCase) {
		if(lawCase == null)
			return 0;
		return lawRepository.addLawCase(lawCase);
	}

	@Override
	public LawCase getLawCase(int caseid) {
		return lawRepository.getLawCase(caseid);
	}

	@Override
	public LawOffice getLawOfficeByUserId(int userid) {
		List<LawOffice> list = lawRepository.getAllLawOffices();
		LawOffice rv = null;
		for (LawOffice lawOffice : list) {
			if(lawOffice.getUser().getUid() == userid){
				rv = lawOffice;
				break;
			}
		}
		return rv;
	}

	@Override
	public boolean updateLawCategory(String name, int parentid, int catid) {
		boolean rv  = false;
		LawCategory category = lawRepository.getLawCategory(catid);
		LawCategory parentCategory = lawRepository.getLawCategory(parentid);
		if(StringUtils.isNotBlank(name) && category != null){
			category.setCatName(name);
			if(parentCategory == null)
				parentCategory = LawCategory.NULL_CATEGORY;
			category.setParentCategory(parentCategory);
			DOMAIN_EVENTS.updateLawCategory(name,parentid,catid);
			rv = true;
		}
		return rv;
	}

	@Override
	public boolean addLawCategory(String name, int parentid) {
		return lawRepository.addLawCategory(name,parentid);
	}

	@Override
	public List<LawCase> getAllLawCases(int startpos, int count) {
		List<LawCase> alLawCases = lawRepository.getAllLawCases();
		Collections.sort(alLawCases, Comparators.LAWCASE_COMPARATOR_ASC_SUBMITDATE);
		List<LawCase> rvList = (List<LawCase>) ListUtils.subList(alLawCases, startpos, startpos+count);
		return rvList;
	}

	@Override
	public long getAllLawCasesCount() {
		return lawRepository.getAllLawCases().size();
	}

	@Override
	public int saveLawOffice(LawOffice office) {
		if(office.getUser() != null && office.getUser().getUid()>0){
			LawOffice office2 = getLawOfficeByUserId(office.getUser().getUid());
			if(office2 != null){
				office.setCooperateId(office2.getCooperateId());
			}
		}
		return lawRepository.saveLawOffice(office);
	}
	@Override
	public LawOffice getLawOffice(int officeid) {
		return lawRepository.getLawOffice(officeid);
	}

	@Override
	public int addLawyer(Lawyer lawyer) {
		LawOffice lawOffice = getLawOffice(lawyer.getLawOffice().getCooperateId());
		User user = userService.getUser(lawyer.getUser().getUid());
		Province province = areaService.getProvince(lawyer.getProvince().getAreaId());
		City city = areaService.getCity(lawOffice.getCity().getAreaId());
		if(lawOffice == null || user == null || province == null || city == null){
			return 0;
		}
		return lawRepository.saveLawyer(lawyer);
	}
}
