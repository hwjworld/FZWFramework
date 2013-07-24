package com.fzw.domain;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.legaldaily.estension.ecard.model.post.Post;
import com.legaldaily.estension.ecard.model.question.Answer;
import com.legaldaily.estension.ecard.repository.listener.AnswerDeleter;
import com.legaldaily.estension.ecard.repository.listener.AnswerPasser;
import com.legaldaily.estension.ecard.repository.listener.BestAnswerSetter;
import com.legaldaily.estension.ecard.repository.listener.BestAnswerUnSetter;
import com.legaldaily.estension.ecard.repository.listener.LawCaseDeleter;
import com.legaldaily.estension.ecard.repository.listener.LawCasePasser;
import com.legaldaily.estension.ecard.repository.listener.LawCategoryUpdater;
import com.legaldaily.estension.ecard.repository.listener.LogWriter;
import com.legaldaily.estension.ecard.repository.listener.PostCounter;
import com.legaldaily.estension.ecard.repository.listener.PostOrderChanger;
import com.legaldaily.estension.ecard.repository.listener.PvUpdater;
import com.legaldaily.estension.ecard.repository.listener.QuestionAddContentForbid;
import com.legaldaily.estension.ecard.repository.listener.QuestionAddContentPasser;
import com.legaldaily.estension.ecard.repository.listener.QuestionDeleter;
import com.legaldaily.estension.ecard.repository.listener.QuestionPasser;
import com.legaldaily.estension.ecard.repository.listener.QuestionPvAdder;

public class DomainEvents {

	public DomainMessage computePostsCount(int catId) {
		DomainMessage message = new DomainMessage(catId);
		MessageListener listener = new PostCounter();
		asyncCall(listener, message);
		return message;
	}

	public DomainMessage passQuestion(long qid) {
		DomainMessage message = new DomainMessage(qid);
		MessageListener listener = new QuestionPasser();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage passQuestionsAddcontent(long qid) {
		DomainMessage message = new DomainMessage(qid);
		MessageListener listener = new QuestionAddContentPasser();
		asyncCall(listener, message);
		return message;
	}

	public DomainMessage forbidQuestionAddcontent(long qid) {
		DomainMessage message = new DomainMessage(qid);
		MessageListener listener = new QuestionAddContentForbid();
		asyncCall(listener, message);
		return message;
	}


	public DomainMessage forbidQuestions(long qid) {
		DomainMessage message = new DomainMessage(qid);
		MessageListener listener = new QuestionDeleter();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage passLawCase(int []qid) {
		DomainMessage message = new DomainMessage(qid);
		MessageListener listener = new LawCasePasser();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage deleteLawCase(int []qid) {
		DomainMessage message = new DomainMessage(qid);
		MessageListener listener = new LawCaseDeleter();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage passAnswer(long[] answerid) {
		DomainMessage message = new DomainMessage(answerid);
		MessageListener listener = new AnswerPasser();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage forbitAnswer(long[] answerid) {
		DomainMessage message = new DomainMessage(answerid);
		MessageListener listener = new AnswerDeleter();
		asyncCall(listener, message);
		return message;
	}

	public DomainMessage setBestAnswer(Answer answer) {
		DomainMessage message = new DomainMessage(answer);
		MessageListener listener = new BestAnswerSetter();
		asyncCall(listener, message);
		return message;
	}

	public DomainMessage unsetBestAnswer(Answer answer) {
		DomainMessage message = new DomainMessage(answer);
		MessageListener listener = new BestAnswerUnSetter();
		asyncCall(listener, message);
		return message;
	}
	
	public DomainMessage updateLawCategory(String name, int parentid, int catid) {
		String[] params = new String[]{name,String.valueOf(parentid),String.valueOf(catid)};
		DomainMessage message = new DomainMessage(params);
		MessageListener listener = new LawCategoryUpdater();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage writeLog(int userid, String userip, int objid, String action, String category) {
		String[] params = new String[]{String.valueOf(userid),userip,String.valueOf(objid),action,category};
		DomainMessage message = new DomainMessage(params);
		MessageListener listener = new LogWriter();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage pv(String date) {
		DomainMessage message = new DomainMessage(date);
		MessageListener listener = new PvUpdater();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage changePostOrder(int postid, int order) {
		String po[] = new String[]{String.valueOf(postid),String.valueOf(order)};
		DomainMessage message = new DomainMessage(po);
		MessageListener listener = new PostOrderChanger();
		asyncCall(listener, message);
		return message;
	}
	public DomainMessage addQuestionPv(long qid) {
		DomainMessage message = new DomainMessage(qid);
		MessageListener listener = new QuestionPvAdder();
		asyncCall(listener, message);
		return message;
	}

	private static void asyncCall(final MessageListener listener, final DomainMessage message){
		message.addFutureTask(
				new FutureTask<Object>(new Callable<Object>() {

					@Override
					public Object call() throws Exception {
						listener.action(message);
						return null;
					}
					
				}));
	}

}
