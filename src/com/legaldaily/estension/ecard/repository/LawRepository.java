package com.legaldaily.estension.ecard.repository;

import java.util.List;
import java.util.Map;

import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;

/**
 * 律师，律所，案件,法律分类
 * @author hwj
 *
 */
public interface LawRepository {
	public Lawyer getLawyer(int lawyerId);
	public LawOffice getLawOffice(int officeId);
	public LawCategory getLawCategory(int catId);
	public LawCase getLawCase(int caseId);
	
	public List<LawCase> getAllLawCases();
	public List<LawOffice> getAllLawOffices();
	public List<Lawyer> getAllLawyers();
	public List<LawCategory> getAllLawCategories();
	
	public long getAllLawCasesCount();
	public long getAllLawOfficesCount();
	public long getAllLawyersCount();
	public long getAllLawCategoriesCount();
	public List<Map<LawOffice, Integer>> getLawOfficeRanking();
	
	public List<Boolean> passLawCase(int caseid[]);
	public List<Boolean> deleteLawCase(int caseid[]);
	public boolean editLawCase(LawCase lawCase);
	/**
	 * 返回处理的caseid
	 * @param lawCase
	 * @return
	 */
	public int addLawCase(LawCase lawCase);
	public boolean updateLawCategory(String name, int parentid, int catid);
	public boolean addLawCategory(String name, int parentid);
	/**
	 * 返回插入或者更新的office
	 * @param office
	 * @return
	 */
	public int saveLawOffice(LawOffice office);
	public int saveLawyer(Lawyer lawyer);
	
}
