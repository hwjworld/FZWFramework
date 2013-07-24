package com.legaldaily.estension.ecard.service;

import com.fzw.connection.FZWService;
import com.legaldaily.estension.ecard.service.area.AreaServices;
import com.legaldaily.estension.ecard.service.area.AreaServicesImpl;
import com.legaldaily.estension.ecard.service.law.LawServices;
import com.legaldaily.estension.ecard.service.law.LawServicesImpl;
import com.legaldaily.estension.ecard.service.question.QuestionServices;
import com.legaldaily.estension.ecard.service.question.QuestionServicesImpl;
import com.legaldaily.estension.ecard.service.side.SideService;
import com.legaldaily.estension.ecard.service.side.SideServiceImpl;
import com.legaldaily.estension.ecard.service.users.UserServices;
import com.legaldaily.estension.ecard.service.users.UserServicesImpl;

public interface EcardService extends FZWService {
	static SideService sideService = new SideServiceImpl();
	static AreaServices areaService = new AreaServicesImpl();
	static LawServices lawService = new LawServicesImpl();
	static UserServices userService = new UserServicesImpl();
	static QuestionServices questionServices = new QuestionServicesImpl();
}
