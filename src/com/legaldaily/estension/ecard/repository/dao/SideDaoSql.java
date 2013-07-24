package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.side.SVValue;

public class SideDaoSql extends EcardDao implements SideDao {

	@Override
	public List<SVValue> getAllDefaultSettings() {
		return (List<SVValue>) selectList("selectAllDefaultSettings");
	}

	@Override
	public int writeLog(int userid, String userip, int objid, String action, String category) {
		return insert("writelog",genMabatisMapValues(userid,userip,objid,action,category));
	}

	@Override
	public void addPv(String date) {
		insert("insertPv",date);
	}

	@Override
	public void updatePv(int id) {
		update("updatePv",id);
	}

	@Override
	public int getPvIdByDate(String date) {
		return (Integer) selectOne("selctPvIdByDate",date);
	}

}
