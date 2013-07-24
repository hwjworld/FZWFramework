package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.side.SVValue;

public interface SideDao {
	public List<SVValue> getAllDefaultSettings();

	public int writeLog(int userid, String userip, int objid, String action, String category);

	public void addPv(String date);

	public void updatePv(int id);

	public int getPvIdByDate(String date);
}
