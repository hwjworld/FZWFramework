package com.legaldaily.estension.ecard.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.legaldaily.estension.ecard.model.side.SVValue;
import com.legaldaily.estension.ecard.repository.dao.SideDao;
import com.legaldaily.estension.ecard.repository.dao.SideDaoSql;

public class SideRepositoryDao implements SideRepository {

	private static final Map<Object, Object> sideMap = new HashMap<Object, Object>();

	private SideDao sideDao;

	public SideRepositoryDao() {
		this.sideDao = new SideDaoSql();
	}
	@Override
	public List<SVValue> getAllDefaultSetting() {
		String key = "svvalue";
		if(!sideMap.containsKey(key)){
			List<SVValue> list = sideDao.getAllDefaultSettings();
			if(CollectionUtils.isNotEmpty(list)){
				sideMap.put(key, list);
			}
		}
		return (List<SVValue>) sideMap.get(key);
	}
	@Override
	public boolean writeLog(int userid, String userip, int objid, String action, String category) {
		return sideDao.writeLog(userid,userip,objid,action,category)>0?true:false;
	}
	@Override
	public void increasePv(String date) {
		int id = getPvIdByDate(date);
		if(id >0 ){
			updatePv(id);
		}else {
			addPv(date);
		}
	}
	private void addPv(String date) {
		sideDao.addPv(date);
	}
	private void updatePv(int id) {
		sideDao.updatePv(id);
	}
	private int getPvIdByDate(String date) {
		return sideDao.getPvIdByDate(date);
	}
	
}
