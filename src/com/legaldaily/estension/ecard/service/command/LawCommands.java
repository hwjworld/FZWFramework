package com.legaldaily.estension.ecard.service.command;

import com.fzw.Globals;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;
import com.fzw.view.ResultAdapter;
import com.legaldaily.estension.ecard.model.condition.LawCondition;
import com.legaldaily.estension.ecard.service.EcardService;
import com.legaldaily.estension.ecard.view.adapt.php.PHPLawAdapter;

public class LawCommands extends ServiceCommand {

	public static final String GET_AVAILABLE_LAWCASES = "getAvailableLawCases";
	public static final String GET_ALL_LAWCASES = "getAllLawCases";
	public static final String GET_WAITCHECK_LAWCASES = "getWaitCheckLawCases";
	
	public static final String GET_LAW_CATEGORIES = "getLawCategories";
	public static final String GET_LAWOFFICE_RANKING = "getLawOfficeRanking";
	public static final String GET_LAWOFFICE_PARTNER = "getLawOfficePartner";
	public static final String GET_LAWYER_BYUSERID = "getLawyerByUserId";
	public static final String GET_LAWYER = "getLawyer";
	public static final String GET_LAWYER_BY_LAWOFFICE = "getLawyerByLawOffice";
	public static final String GET_LAWCASE = "getLawCase";
	

	public static final String GET_LAWOFFICE_BY_USERID = "getLawOfficeByUserId";
	
	public static final EcardService SERVICES = (EcardService) Globals.getBean("lawService");
//	public static final LawCommands EXECUTOR = new LawCommands();
	public static final ResultAdapter ADAPTER = new PHPLawAdapter();
	
	public LawCommands() {
		registeCommand(GET_AVAILABLE_LAWCASES, this);
		registeCommand(GET_ALL_LAWCASES, this);
		registeCommand("getAvailableLawCasesCount", this);
		registeCommand("getAllLawCasesCount", this);
		
		registeCommand(GET_LAW_CATEGORIES, this);
		registeCommand("updateLawCategory", this);
		registeCommand("addLawCategory", this);
		registeCommand(GET_LAWOFFICE_RANKING, this);
		registeCommand(GET_LAWOFFICE_PARTNER, this);
		registeCommand(GET_LAWYER_BYUSERID, this);
		registeCommand(GET_LAWYER, this);
		registeCommand(GET_LAWYER_BY_LAWOFFICE, this);
		registeCommand(GET_WAITCHECK_LAWCASES, this);
		
		registeCommand("passLawCase", this);
		registeCommand("deleteLawCase", this);
		registeCommand("editLawCase", this);
		registeCommand("addLawCase", this);
		registeCommand("addLawyer", this);
		registeCommand(GET_LAWCASE, this);
		registeCommand(GET_LAWOFFICE_BY_USERID, this);
		registeCommand("saveLawOffice", this);
		
	}
	@Override
	public Object execute(ConnectionMessage message) {
		message.setResultAdapter(ADAPTER);
		return executeCommand(message.getMessageName(), SERVICES, new LawCondition(message));
	}

}
