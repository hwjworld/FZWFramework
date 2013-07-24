package com.legaldaily.estension.ecard.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;

import org.apache.commons.collections.CollectionUtils;

import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;
import com.legaldaily.estension.ecard.repository.dao.LawDao;
import com.legaldaily.estension.ecard.repository.dao.LawDaoSql;

public class LawRepositoryDao extends RepositoryDao implements LawRepository {

	private LawDao lawDao = null;
	public LawRepositoryDao() {
		lawDao = new LawDaoSql();
	}
	@Override
	public Lawyer getLawyer(int lawyerId) {
		if(!containKeyInCache(lawyerCache, lawyerId)){
			Lawyer lawyer = lawDao.getLawyer(lawyerId);
			setLawyer(lawyer);
			storeLawyer(lawyer);
		}	
		return (Lawyer) getValueFromCache(lawyerCache, lawyerId);
	}

	@Override
	public LawOffice getLawOffice(int officeId) {
		if(officeId <= 0 ){
			return null;
		}
		if(!containKeyInCache(lawofficeCache, officeId)){
			LawOffice lawOffice = lawDao.getLawOffice(officeId);
			setLawoffice(lawOffice);
			storeLawoffice(lawOffice);
		}	
		return (LawOffice) getValueFromCache(lawofficeCache, officeId);
	}

	@Override
	public LawCategory getLawCategory(int catId) {
		if(catId <= 0)
			return null;
		if(!containKeyInCache(lawCategories, catId)){
			LawCategory lawCategory = lawDao.getLawCategory(catId);
			setLawCatetory(lawCategory);
			storeLawCatetory(lawCategory);
		}
		return (LawCategory) getValueFromCache(lawCategories, catId);
	}


	private LawCase getLawCaseFromCache(int caseid) {
		Element elem = lawcaseCache.get(caseid);
		LawCase lawCase = null;
		if (elem == null) {
			return null;
		} else {
			lawCase = (LawCase) elem.getValue();
		}
		return lawCase;
	
		
	}
	@Override
	public LawCase getLawCase(int caseId) {
		LawCase lawCase = null;
		if((lawCase = getLawCaseFromCache(caseId)) == null){
			lawCase = lawDao.getLawCase(caseId);
			if (lawCase == null) {
				return null;
			}
			setLawCase(lawCase);
			storeLawCase(lawCase);
		}
		return lawCase;
	}

	@Override
	public List<LawCase> getAllLawCases() {
		List<LawCase> list = (List<LawCase>) getAllCacheValues(lawcaseCache);
		if(CollectionUtils.isNotEmpty(list) ){//&& list.size()==getAllLawCasesCount()){
			return list;
		}
		list = lawDao.getAllLawCases();
		for (LawCase lawCase : list) {
			setLawCase(lawCase);
			storeLawCase(lawCase);
		}
		return list;
	}
	@Override
	public List<LawOffice> getAllLawOffices() {
		List<LawOffice> list = (List<LawOffice>) getAllCacheValues(lawofficeCache);
		if(CollectionUtils.isNotEmpty(list) ){//&& list.size()==getAllLawOfficesCount()){
			return list;
		}
		list = lawDao.getAllLawOffices();
		for (LawOffice lawOffice : list) {
			setLawoffice(lawOffice);
			storeLawoffice(lawOffice);
		}
		return list;
	}
	@Override
	public List<Lawyer> getAllLawyers() {
		List<Lawyer> list = (List<Lawyer>) getAllCacheValues(lawyerCache);
		if(CollectionUtils.isNotEmpty(list) ){// && list.size()==getAllLawyersCount()){
			return list;
		}
		list = lawDao.getAllLawyers();
		for (Lawyer lawyer : list) {
			setLawyer(lawyer);
			storeLawyer(lawyer);
		}
		return list;
	}
	@Override
	public long getAllLawCasesCount() {
		return lawDao.getAllLawCasesCount();
	}
	@Override
	public long getAllLawOfficesCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getAllLawyersCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<LawCategory> getAllLawCategories() {
		List<LawCategory> list = (List<LawCategory>) getAllCacheValues(lawCategories);
		if(CollectionUtils.isNotEmpty(list) ){//&& list.size()==getAllLawyersCount()){
			return list;
		}
		list = lawDao.getAllLawCategories();
		for (LawCategory lawCategory : list) {
			setLawCatetory(lawCategory);
			storeLawCatetory(lawCategory);
		}
		return list;
	}
	@Override
	public long getAllLawCategoriesCount() {
		return 0;
	}
	
	@Override
	public List<Map<LawOffice, Integer>> getLawOfficeRanking() {
		List<Map<Integer, Integer>> list = lawDao.getLawOfficeRanking();
		List<Map<LawOffice, Integer>> rv = new ArrayList<Map<LawOffice,Integer>>();
		for (Map<Integer, Integer> map : list) {
			HashMap<LawOffice, Integer> tmpmap = new HashMap<LawOffice, Integer>();
			LawOffice office = getLawOffice(map.keySet().iterator().next());
			tmpmap.put(office, map.values().iterator().next());
			rv.add(tmpmap);
		}
		return rv;
	}
	@Override
	public List<Boolean> passLawCase(int[] caseid) {
		int rv = lawDao.passLawCase(caseid);
		List<Boolean> list = new ArrayList<Boolean>();
		for (int i: caseid) {
			if(rv>0)list.add(true);
			else
				list.add(false);
		}
		return list;
	}
	@Override
	public List<Boolean> deleteLawCase(int[] caseid) {
		int rv = lawDao.deleteLawCase(caseid);
		List<Boolean> list = new ArrayList<Boolean>();
		for (int i: caseid) {
			if(rv>0)list.add(true);
			else
				list.add(false);
		}
		return list;
	}
	
	@Override
	public boolean editLawCase(LawCase lawCase) {
		if(lawCase.getCaseId()<=0){
			return false;
		}
		int rv = lawDao.editLawCase(lawCase);
		if(rv >0 ){
			removeFromCache(lawcaseCache, lawCase.getCaseId());
			getLawCase(lawCase.getCaseId());
		}
		return rv>0?true:false;
	}
	
	@Override
	public int addLawCase(LawCase lawCase) {
		if(lawCase.getCaseId()>0){
			return editLawCase(lawCase)?lawCase.getCaseId():0;
		}
		int rv = lawDao.addLawCase(lawCase);
		if(rv>0){
			getLawCase(rv);
		}
		return rv;
	}
	@Override
	public boolean updateLawCategory(String name, int parentid, int catid) {
		return lawDao.updateLawCategory(name,parentid,catid)>0?true:false;
	}
	@Override
	public boolean addLawCategory(String name, int parentid) {
		int catId = lawDao.addLawCategory(name,parentid);
		if(catId > 0){
			getLawCategory(catId);
			return true;
		}else {
			return false;
		}
	}
	@Override
	public int saveLawOffice(LawOffice office) {
		int b = 0;
		if(office.getCooperateId()>0){
			b = updateLawOffice(office);
		}else {
			b = addLawOffice(office);
		}
		return b;
	}
	private int addLawOffice(LawOffice office) {
		int officeid = lawDao.addLawOffice(office);
		getLawOffice(officeid);
		return officeid ;
	}
	private int updateLawOffice(LawOffice office) {
		lawDao.updateLawOffice(office);
		removeFromCache(lawofficeCache, office.getCooperateId());
		getLawOffice(office.getCooperateId());
		return office.getCooperateId();
	}
	@Override
	public int saveLawyer(Lawyer lawyer) {
		int b = 0;
		if(lawyer.getCooperateId()>0){
			updateLawyer(lawyer);
			b = lawyer.getCooperateId();
		}else {
			b = addLawyer(lawyer);
		}
		return b;
	}
	private int addLawyer(Lawyer lawyer) {
		int lawyerid = lawDao.addLawyer(lawyer);
		if(lawyerid > 0)
			getLawyer(lawyerid);
		return lawyerid;
	}
	private int updateLawyer(Lawyer lawyer) {
		if(lawDao.updateLawyer(lawyer)>0){
			removeFromCache(lawyerCache, lawyer.getCooperateId());
			getLawyer(lawyer.getCooperateId());
			return lawyer.getCooperateId();
		}else {
			return 0;
		}
	}

}
