package com.legaldaily.estension.ecard.service.law;

import java.util.List;
import java.util.Map;

import com.legaldaily.estension.ecard.model.condition.LawCondition;
import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;
import com.legaldaily.estension.ecard.service.EcardService;

// include law office
public interface LawServices extends EcardService {
	public List<LawCase> getAvailableLawCases(int startpos, int count);
	public long getAvailableLawCasesCount();
	public List<LawCase> getWaitCheckLawCases(int startpos, int count);
	public long getWaitCheckLawCasesCount();
	public List<LawCategory> getAllLawCategories();
	public List<LawCategory> getLawCategories(LawCondition condition);
	public List<Map<LawOffice, Integer>> getLawOfficeRanking(int count);
	public List<LawOffice> getLawOfficePartner();
	public Lawyer getLawyerByUserId(int userId);
	public Lawyer getLawyer(int lawyerId);
	/**
	 * 
	 * @param caseid
	 * @return 依次审核通过正确与否
	 */
	public List<Boolean> passLawCase(int caseid[]);
	public List<Boolean> deleteLawCase(int caseid[]);
	public boolean editLawCase(LawCase lawCase);
	/**
	 * 返回新加的caseid
	 * @param lawCase
	 * @return
	 */
	public int addLawCase(LawCase lawCase);
	public List<LawCase> getPublishedLawCases(int startpos, int count);
	public LawCase getLawCase(int caseid);
	public LawOffice getLawOfficeByUserId(int userid);
	public LawOffice getLawOffice(int officeid);
	public boolean updateLawCategory(String name, int parentid, int catid);
	public boolean addLawCategory(String name, int parentid);
	public List<LawCase> getAllLawCases(int startpos, int count);
	public long getAllLawCasesCount();
	public int saveLawOffice(LawOffice office);
	public int addLawyer(Lawyer lawyer);
}
