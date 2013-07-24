package com.legaldaily.estension.ecard.service.side;

import java.util.List;

import com.legaldaily.estension.ecard.model.side.SVValue;
import com.legaldaily.estension.ecard.service.EcardService;

public interface SideService extends EcardService {
	public List<SVValue> getAllDefaultSetting();
	
	public String getDefaultSetting(String title);

	public boolean writeLog(int userid, String userip, int objid, String action, String category);

	public boolean pv(String value);
}
