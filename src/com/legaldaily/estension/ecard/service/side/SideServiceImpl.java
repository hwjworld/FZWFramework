package com.legaldaily.estension.ecard.service.side;

import java.util.List;

import com.fzw.Globals;
import com.legaldaily.estension.ecard.model.side.SVValue;
import com.legaldaily.estension.ecard.repository.SideRepository;

public class SideServiceImpl implements SideService {


	protected SideRepository sideRepository;

	// protected UserRepository userRepository;

	public SideServiceImpl(SideRepository sideRepository) {
		this.sideRepository = sideRepository;
	}
	public SideServiceImpl() {
		this.sideRepository = (SideRepository) Globals.getBean("sideRepository");
	}
	@Override
	public List<SVValue> getAllDefaultSetting() {
		return sideRepository.getAllDefaultSetting();
	}

	@Override
	public String getDefaultSetting(String title) {
		List<SVValue> allSettings = getAllDefaultSetting();
		String value = null;
		for (SVValue svValue : allSettings) {
			if(svValue.getTitle().equalsIgnoreCase(title)){
				value = svValue.getValue();
				break;
			}
		}
		return value;
	}
	@Override
	public boolean writeLog(int userid, String userip, int objid, String action, String category) {
		DOMAIN_EVENTS.writeLog(userid,userip,objid,action,category);
		return true;
	}
	@Override
	public boolean pv(String date) {
		DOMAIN_EVENTS.pv(date);
		return true;
	}

}
