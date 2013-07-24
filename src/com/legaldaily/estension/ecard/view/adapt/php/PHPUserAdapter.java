package com.legaldaily.estension.ecard.view.adapt.php;

import java.util.List;
import java.util.Map;

import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.model.user.Score;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;
import com.legaldaily.estension.ecard.service.command.UserCommands;

public class PHPUserAdapter extends PHPAdapter {

	@Override
	public Object adapt(String methodName,Map<String, String> methodParam, Object returnValue) {

		if (returnValue == null) {
			LogUtils.warn("No result in method\" [ " + methodName + " ]\"");
			return returnValue;
		}

		if (methodName.equals(UserCommands.GET_SCORE_RANK)) {
			returnValue = adaptScoreList(returnValue);
		} else if (methodName.equals(UserCommands.GET_USER)||
				methodName.equals(UserCommands.GET_NONEHEADPIC_USERS)||
				methodName.equals(UserCommands.SEARCH_USERS)) {
			returnValue = adaptUserList(returnValue);
		}else if (methodName.equals(UserCommands.GET_ALL_USERGROUP)||
				methodName.equals(UserCommands.GET_USERGROUP)) {
			returnValue = adaptUserGroupList(returnValue);
		}
		return returnValue;
	}

	private Object adaptUserGroupList(Object returnValue) {
		if(!(returnValue instanceof List)){
			return returnValue;
		}
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			list.set(i, adaptUserGroupInfo(list.get(i)));
		}
		return list;
	}

	private Object adaptUserGroupInfo(Object returnValue) {
		UserGroup userGroup = (UserGroup) returnValue;
		PHPUserGroupModel model = new PHPUserGroupModel();
		model.setS_rulers(userGroup.getS_rulers());
		model.setU_rulers(userGroup.getU_rulers());
		model.setUg_id(userGroup.getGroupid());
		model.setUg_name(userGroup.getGroupname());
		return model;
	}

	private Object adaptScoreList(Object returnValue) {
		if(!(returnValue instanceof List)){
			return returnValue;
		}
		List list = (List) returnValue;
		for (int i = 0; i < list.size(); i++) {
			list.set(i, adaptScoreInfo(list.get(i)));
		}
		return list;
	}

	private Object adaptScoreInfo(Object returnValue) {
		Score score = (Score) returnValue;
		PHPScoreModel model = new PHPScoreModel();
		model.setNick_name(score.getUser().getNickname());
		model.setScore(score.getTotalScore());
		model.setU_id(score.getUser().getUid());
		return model;
	}

	private Object adaptUserList(Object returnValue) {
		List users = (List) returnValue;
		for (int i = 0; i < users.size(); i++) {
			users.set(i, adaptUserInfo(users.get(i)));
		}
		return users;
	}

	private Object adaptUserInfo(Object returnValue) {
		User user = (User) returnValue;
		PHPUserModel model = new PHPUserModel();
		model.setLogin_name(user.getLoginname());
		model.setNick_name(user.getNickname());
		model.setU_group(user.getUserGroup().getGroupid());
		model.setU_id(user.getUid());
		model.setU_email(user.getEmail());
		model.setUg_name(user.getUserGroup().getGroupname());
		model.setReg_time(getDateString(user.getRegtime()));
		model.setU_score(user.getScore());
		model.setU_sexes(user.getSex());
		return model;
	}
}

class PHPScoreModel {
	private int u_id;
	private long score;
	private String nick_name;

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
}

class PHPUserModel {
	private int u_id, u_group,u_score;
	private String login_name, nick_name,u_email,ug_name,login_time,reg_time,u_sexes,headpic,check_headpic;
//	private UserGroup group;

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public int getU_group() {
		return u_group;
	}

	public void setU_group(int u_group) {
		this.u_group = u_group;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getU_email() {
		return u_email;
	}

	public void setU_email(String u_email) {
		this.u_email = u_email;
	}

	public String getUg_name() {
		return ug_name;
	}

	public void setUg_name(String ug_name) {
		this.ug_name = ug_name;
	}

	public int getU_score() {
		return u_score;
	}

	public void setU_score(int u_score) {
		this.u_score = u_score;
	}

	public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

	public String getReg_time() {
		return reg_time;
	}

	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}

	public String getU_sexes() {
		return u_sexes;
	}

	public void setU_sexes(String u_sexes) {
		this.u_sexes = u_sexes;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public String getCheck_headpic() {
		return check_headpic;
	}

	public void setCheck_headpic(String check_headpic) {
		this.check_headpic = check_headpic;
	}


}
class PHPUserGroupModel {
	private int ug_id;
	private String ug_name,u_rulers,s_rulers;
	public int getUg_id() {
		return ug_id;
	}
	public void setUg_id(int ug_id) {
		this.ug_id = ug_id;
	}
	public String getUg_name() {
		return ug_name;
	}
	public void setUg_name(String ug_name) {
		this.ug_name = ug_name;
	}
	public String getU_rulers() {
		return u_rulers;
	}
	public void setU_rulers(String u_rulers) {
		this.u_rulers = u_rulers;
	}
	public String getS_rulers() {
		return s_rulers;
	}
	public void setS_rulers(String s_rulers) {
		this.s_rulers = s_rulers;
	}

}