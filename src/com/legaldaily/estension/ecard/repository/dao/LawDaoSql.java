package com.legaldaily.estension.ecard.repository.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.model.condition.LawCondition;
import com.legaldaily.estension.ecard.model.law.LawCase;
import com.legaldaily.estension.ecard.model.law.LawCategory;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;

public class LawDaoSql extends EcardDao implements LawDao {

	@Override
	public List<LawCase> getAllLawCases() {
		return (List<LawCase>) selectList("selectAllCases");
	}

	@Override
	public List<LawCase> getCases(LawCondition condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LawOffice> getAllLawOffices() {
		return (List<LawOffice>) selectList("selectAllOffices");
	}

	@Override
	public List<Lawyer> getAllLawyers() {
		return (List<Lawyer>) selectList("selectAllLawyers");
	}

	@Override
	public List<LawCategory> getAllLawCategories() {
		return (List<LawCategory>) selectList("selectAllLawCategoris");
	}
	
	@Override
	public long getAllLawCasesCount() {
		return (Long) selectOne("selectAllCasesCount");
	}

	@Override
	public long getAllLawOfficesCount() {
		return (Long) selectOne("selectAllLawofficeCount");
	}

	@Override
	public long getAllLawyersCount() {
		return (Long) selectOne("selectAllLawyersCount");
	}

	@Override
	public LawOffice getLawOffice(int officeId) {
		return (LawOffice) selectOne("selectLawoffice", officeId);
	}

	@Override
	public Lawyer getLawyer(int lawyerId) {
		return (Lawyer) selectOne("selectLawyer", lawyerId);
	}

	@Override
	public LawCase getLawCase(int caseId) {
		return (LawCase) selectOne("selectCase", caseId);
	}

	@Override
	public LawCategory getLawCategory(int catId) {
		return (LawCategory) selectOne("selectLawCategory", catId);
	}

	@Override
	public List<Map<Integer, Integer>> getLawOfficeRanking() {
		List<Map<Object, Object>> list = (List<Map<Object, Object>>) selectList("getLawOfficeRanking");
		List<Map<Integer, Integer>> rv = new ArrayList<Map<Integer,Integer>>();
		for (Map<Object, Object> map : list) {
			Map<Integer, Integer> tmpMap = new HashMap<Integer, Integer>();
			tmpMap.put(StringValueUtils.getInt(map.get("ID").toString()), StringValueUtils.getInt(map.get("SCORE").toString()));
			rv.add(tmpMap);
		}
		return rv ;
	}

	@Override
	public int passLawCase(int []caseid) {
//		String ids = StringUtils.join(ArrayUtils.toObject(caseid));
		return update("passLawCase",caseid);
	}

	@Override
	public int deleteLawCase(int []caseid) {
//		String ids = StringUtils.join(ArrayUtils.toObject(caseid));
		return update("deleteLawCase",caseid);
	}

	@Override
	public int editLawCase(LawCase lawCase) {
		return update("editLawCase",lawCase);
	}

	@Override
	public int addLawCase(LawCase lawCase) {
		int rv = insert("insertLawCase",lawCase);
		return rv;
//		return rv>0?getMysqlLastId():0;
	}

	@Override
	public int updateLawCategory(String name, int parentid, int catid) {
		return update("updateLawCategory",genMabatisMapValues(name,parentid,catid));
	}

	@Override
	public int addLawCategory(String name, int parentid) {
		return insert("insertLawCategory",genMabatisMapValues(name,parentid));
	}

	@Override
	public int addLawOffice(LawOffice office) {
		return insert("insertLawOffice",office);
	}

	@Override
	public boolean updateLawOffice(LawOffice office) {
		return update("updateLawOffice",office)>0?true:false;
	}

	@Override
	public int addLawyer(Lawyer lawyer) {
		return insert("insertLawyer",lawyer);
	}

	@Override
	public int updateLawyer(Lawyer lawyer) {
		return update("updateLawyer",lawyer);
	}
}
