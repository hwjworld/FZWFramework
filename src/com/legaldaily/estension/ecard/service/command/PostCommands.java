package com.legaldaily.estension.ecard.service.command;

import com.fzw.Globals;
import com.fzw.model.ConnectionMessage;
import com.fzw.service.command.ServiceCommand;
import com.fzw.view.ResultAdapter;
import com.legaldaily.estension.ecard.model.condition.PostCondition;
import com.legaldaily.estension.ecard.service.post.PostServices;
import com.legaldaily.estension.ecard.view.adapt.php.PHPPostAdapter;

public class PostCommands extends ServiceCommand {

	public static final String POST_GET = "getPost";
	public static final String POST_LIST = "listPosts";
	public static final String GET_POST_CATEGORY = "getPostCategory";
	public static final String GET_All_POST_CATEGORY = "getAllPostCategory";
	public static final String GET_POST_CATEGORY_BYPARENTID = "getPostCategoryByParentid";
	

//	public static final ServiceCommand EXCUTOR = new PostCommands();
	public final ServiceCommand EXCUTOR = this;
	private static final ResultAdapter ADAPTER = new PHPPostAdapter();

	public PostCommands() {
		registeCommand(POST_GET, EXCUTOR);
		registeCommand("delPost", EXCUTOR);
		registeCommand("savePost", EXCUTOR);
		registeCommand(POST_LIST, EXCUTOR);
		registeCommand(GET_POST_CATEGORY, EXCUTOR);
		registeCommand(GET_POST_CATEGORY_BYPARENTID, EXCUTOR);
		registeCommand(GET_All_POST_CATEGORY, EXCUTOR);
		registeCommand("setPostStatus", EXCUTOR);
		registeCommand("changeOrder", EXCUTOR);
		registeCommand("listPostsCount", EXCUTOR);
	}

	private static final PostServices SERVICE = (PostServices) Globals
			.getBean("postService");

	@Override
	public Object execute(ConnectionMessage message) {
		message.setResultAdapter(ADAPTER);
		return executeCommand(message.getMessageName(), SERVICE, new PostCondition(message));
	}
}
