package com.legaldaily.estension.ecard.repository;

import java.util.List;

import com.legaldaily.estension.ecard.model.side.SVValue;

public interface SideRepository extends EcardRepository{
	public List<SVValue> getAllDefaultSetting();

	public boolean writeLog(int userid, String userip, int objid, String action, String category);

	public void increasePv(String date);
}
