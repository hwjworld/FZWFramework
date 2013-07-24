package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;
import java.util.Map;

import com.legaldaily.estension.ecard.model.condition.LawCondition;
import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;

public interface LawDao {
//	public List<Integer> getAllCaseIds();
	public LawCase getLawCase(int caseId);
	public LawOffice getLawOffice(int officeId);
	public Lawyer getLawyer(int lawyerId);
	public LawCategory getLawCategory(int catId);
	
	public List<LawCase> getAllLawCases();
	public List<LawOffice> getAllLawOffices();
	public List<Lawyer> getAllLawyers();
	public List<LawCategory> getAllLawCategories();
	
	public long getAllLawCasesCount();
	public long getAllLawOfficesCount();
	public long getAllLawyersCount();
	
	public List<LawCase> getCases(LawCondition condition);
	/**
	 * 返回律所排序
	 * @return Map<officeID, score>
	 */
	public List<Map<Integer, Integer>> getLawOfficeRanking();
	
	public int passLawCase(int caseid[]);
	public int deleteLawCase(int caseid[]);
	public int editLawCase(LawCase lawCase);
	public int addLawCase(LawCase lawCase);
	public int updateLawCategory(String name, int parentid, int catid);
	public int addLawCategory(String name, int parentid);
	public int addLawOffice(LawOffice office);
	public boolean updateLawOffice(LawOffice office);
	public int addLawyer(Lawyer lawyer);
	public int updateLawyer(Lawyer lawyer);
}
