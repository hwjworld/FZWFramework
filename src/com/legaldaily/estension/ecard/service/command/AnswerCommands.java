package com.legaldaily.estension.ecard.service.command;

import com.fzw.Globals;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;
import com.fzw.view.ResultAdapter;
import com.legaldaily.estension.ecard.model.condition.AnswerCondition;
import com.legaldaily.estension.ecard.service.EcardService;
import com.legaldaily.estension.ecard.view.adapt.php.PHPAnswerAdapter;

public class AnswerCommands extends ServiceCommand {

	public static final String GET_LATESTLAWYER_ANSWERS = "getLatestLawyerAnswers";
	public static final String GET_LATESTLAWYER_ANSWERS_COUNT = "getLatestLawyerAnswersCount";

	public static final String GET_LATESTNETFRIENDS_ANSWERS = "getLatestNetfriendsAnswers";
	public static final String GET_LATESTNETFRIENDS_ANSWERS_COUNT = "getLatestNetfriendsAnswersCount";
	public static final String GET_ANSWERS_BY_QUESTIONID = "getAnswersByQuestionid";
	
	public static final String ANSWER_PASS= "passAnswer";
	public static final String ANSWER_FORBIDDEN = "forbidAnswer";
	public static final String ANSWER_GET = "getAnswer";
	public static final String GET_ANSWERS = "getAnswers";
	
	public static final EcardService SERVICES = (EcardService) Globals.getBean("answerService");
//	public static final AnswerCommands EXECUTOR = new AnswerCommands();
	public static final ResultAdapter ADAPTER = new PHPAnswerAdapter();
	
	public AnswerCommands() {
		registeCommand(GET_LATESTLAWYER_ANSWERS, this);
		registeCommand(GET_LATESTLAWYER_ANSWERS_COUNT, this);
		registeCommand(GET_LATESTNETFRIENDS_ANSWERS, this);
		registeCommand(GET_LATESTNETFRIENDS_ANSWERS_COUNT, this);
		registeCommand(GET_ANSWERS, this);
		registeCommand("getAnswersCount", this);
		
		registeCommand(ANSWER_PASS, this);
		registeCommand(ANSWER_FORBIDDEN, this);
		registeCommand("editAnswer", this);
		registeCommand(ANSWER_GET, this);
		registeCommand("addAnswer", this);
		registeCommand("updateAnswer", this);
		registeCommand("setBestAnswer", this);
		registeCommand("unsetBestAnswer", this);
		registeCommand(GET_ANSWERS_BY_QUESTIONID, this);
		registeCommand(GET_ANSWERS_BY_QUESTIONID+"Count", this);
	}
	
	
	@Override
	public Object execute(ConnectionMessage message) {
		message.setResultAdapter(ADAPTER);
		return executeCommand(message.getMessageName(), SERVICES, new AnswerCondition(message));
	}

}
