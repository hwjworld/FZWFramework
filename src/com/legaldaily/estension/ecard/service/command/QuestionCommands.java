package com.legaldaily.estension.ecard.service.command;

import com.fzw.Globals;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;
import com.fzw.view.ResultAdapter;
import com.legaldaily.estension.ecard.model.condition.QuestionCondition;
import com.legaldaily.estension.ecard.service.EcardService;
import com.legaldaily.estension.ecard.view.adapt.php.PHPQuestionListAdapter;

public class QuestionCommands extends ServiceCommand {

	public static final String QUESTION_GET = "getQuestion";
	public static final String QUESTION_ADD = "addQuestion";

	public static final String GET_SOLVED_QUESTIONS = "getSolvedQuestions";
//	public static final String GET_UNSOLVED_QUESTIONS = "getUnSolvedQuestions";
//	public static final String GET_NONEANSWER_QUESTIONS = "getNoneAnswerQuestions";
//	public static final String GET_HIGHSCORE_QUESTIONS = "getHighScoreQuestions";
	
	public static final String GET_LATEST_SOLVED_QUESTIONS = "getLatestSolvedQuestionByGroup";
	public static final String GET_QUESTIONS = "getQuestions";
	public static final String GET_NETFRIENDS_QUESTIONS = "getNetFriendsQuestions";
	
	public static final String GET_MEMBER_QUESTIONS = "getMemberQuestions";
	public static final String GET_LAWYERANSWER_QUESTIONS = "getLawyeranswerQuestions";
	public static final String GET_ADDCONTENT_QUESTIONS = "getAddContentQuestions";
	

//	public static final String QUESTION_SETBESTANSWER = "setBestAnswer";
//	public static final String QUESTION_UNSETBESTANSWER = "unsetBestAnswer";
		
	private static final EcardService SERVICE = (EcardService) Globals
			.getBean("questionService");
	
//	private static final ServiceCommand EXECUTOR = new QuestionCommands();
	private static final ResultAdapter ADAPTER = new PHPQuestionListAdapter();
	public QuestionCommands() {
		registeCommand(QUESTION_GET, this);
		registeCommand("updateQuestion", this);
		registeCommand(QUESTION_ADD, this);
		registeCommand(GET_SOLVED_QUESTIONS, this);
		registeCommand(GET_SOLVED_QUESTIONS+"Count", this);
//		registeCommand(GET_UNSOLVED_QUESTIONS, this);
//		registeCommand(GET_NONEANSWER_QUESTIONS, this);
//		registeCommand(GET_HIGHSCORE_QUESTIONS, this);
		registeCommand(GET_QUESTIONS, this);
		registeCommand(GET_QUESTIONS+"Count", this);
		
//		registeCommand(GET_HIGHSCORE_QUESTIONS+"Count", this);
		registeCommand(GET_LATEST_SOLVED_QUESTIONS, this);
		
//		registeCommand(QUESTION_SETBESTANSWER, this);
//		registeCommand(QUESTION_UNSETBESTANSWER, this);
		registeCommand("passQuestions", this);
		registeCommand("forbidQuestions", this);
		registeCommand(GET_MEMBER_QUESTIONS, this);
		registeCommand(GET_NETFRIENDS_QUESTIONS, this);
		registeCommand(GET_LAWYERANSWER_QUESTIONS, this);
		registeCommand(GET_ADDCONTENT_QUESTIONS, this);
		registeCommand("getMemberQuestionsCount", this);
		registeCommand("getNetFriendsQuestionsCount", this);
		registeCommand("getLawyeranswerQuestionsCount", this);
		registeCommand("getAddContentQuestionsCount", this);
		
		registeCommand("passQuestionsAddcontent", this);
		registeCommand("forbidQuestionAddcontent", this);
		
		registeCommand("setAddContent", this);
		registeCommand("questionOperate", this);
		registeCommand("setQuestionOrder", this);
		registeCommand("addQuestionPv", this);
		
	}
	@Override
	public Object execute(ConnectionMessage message) {
		message.setResultAdapter(ADAPTER);
		return executeCommand(message.getMessageName(), SERVICE, new QuestionCondition(message));		
	}

}
