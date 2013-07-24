package com.legaldaily.estension.ecard.service.command;

import com.fzw.Globals;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;
import com.fzw.view.ResultAdapter;
import com.legaldaily.estension.ecard.model.condition.Cond;
import com.legaldaily.estension.ecard.service.qarecommend.IQARecommend;
import com.legaldaily.estension.ecard.view.adapt.php.PHPQAAdapter;

public class QACommands extends ServiceCommand {

	private static final String EXECUTE_BEAN = "qarecommendService";

	public static final String LIST_QARECOMMEND = "listQARecommend";
	public static final String LIST_WAITQARECOMMEND = "listWaitQARecommend";
	public static final String RECOMMEND_ADD = "addRecommend";
	public static final String RECOMMEND_REMOVE = "removeRecommend";
	public static final String RECOMMEND_MOVEPOSITION = "movePosition";

//	private static final ServiceCommand EXECUTOR = new QACommands();

	public static final String EXCUTOR_QARECOMMEND = "getQARList";
	public static final String EXCUTOR_WAITQARECOMMEND = "getQARWaitList";
	public static final String EXCUTOR_RECOMMEND_ADD = "addRecommend";
	public static final String EXCUTOR_RECOMMEND_REMOVE = "removeRecommend";
	public static final String EXCUTOR_RECOMMEND_MOVEPOSITION = "movePosition";

	private static final ResultAdapter ADAPTER = new PHPQAAdapter();

	public QACommands() {
		registeCommand(LIST_QARECOMMEND, this, EXCUTOR_QARECOMMEND);
		registeCommand(LIST_WAITQARECOMMEND, this, EXCUTOR_WAITQARECOMMEND);
		registeCommand(RECOMMEND_ADD, this, EXCUTOR_RECOMMEND_ADD);
		registeCommand(RECOMMEND_REMOVE, this, EXCUTOR_RECOMMEND_REMOVE);
		registeCommand(RECOMMEND_MOVEPOSITION, this,
				EXCUTOR_RECOMMEND_MOVEPOSITION);
	}

	private static final IQARecommend SERVICE = (IQARecommend) Globals
			.getBean(EXECUTE_BEAN);

	@Override
	public Object execute(ConnectionMessage message) {
		message.setResultAdapter(ADAPTER);
		String messageName = message.getMessageName();
		Cond cond = new Cond(message);
		Object result = executeCommand(messageName, SERVICE, cond);
		return result;

	}
}
