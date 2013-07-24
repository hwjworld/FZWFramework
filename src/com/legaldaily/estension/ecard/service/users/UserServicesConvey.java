package com.legaldaily.estension.ecard.service.users;

import java.util.List;

import javax.print.attribute.standard.MediaSize.NA;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.ReuseExecutor;

import sun.print.resources.serviceui;

import com.fzw.service.ServiceConvey;
import com.legaldaily.estension.ecard.cache.RankCache;
import com.legaldaily.estension.ecard.model.area.City;
import com.legaldaily.estension.ecard.model.area.Province;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;
import com.legaldaily.estension.ecard.model.condition.UserCondition;
import com.legaldaily.estension.ecard.model.law.LawOffice;
import com.legaldaily.estension.ecard.model.law.Lawyer;
import com.legaldaily.estension.ecard.model.user.Score;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;
import com.legaldaily.estension.ecard.repository.UserRepository;

public class UserServicesConvey extends ServiceConvey<UserCondition> {

	UserServices userServices = null;
	public UserServicesConvey(UserRepository userRepository) {
		userServices = new UserServicesImpl(userRepository);
	}
	
	public List<Score> getScoreRank(EcardServiceCondition condition){
		return RankCache.getRankScore(getCondition(condition));
//		return userServices.getScoreRank(getCondition(condition));
	}
	
	public List<User> getUser(EcardServiceCondition condition){
		String ids [] = EcardServiceCondition.getIds(condition.getValue("id"));
		String loginnames [] = EcardServiceCondition.getIds(condition.getValue("loginname"));
		String nicknames [] = EcardServiceCondition.getIds(condition.getValue("nickname"));
		return userServices.getUser(ids, loginnames, nicknames);
	}
		
	public List<User> searchUsers(EcardServiceCondition condition){
		List<User> users = null;
		int groupid = condition.getIntValue("groupid");
		String loginname = condition.getValue("loginname");
		String nickname = condition.getValue("nickname");
		String email = condition.getValue("email");
		
		if(groupid > 0){
			users = userServices.getUserByGroup(groupid);
		}else {
			users = userServices.searchUsers(loginname,nickname,email);
		}
		return users;
	}
	public int searchUsersCount(EcardServiceCondition condition){
		return 0;
	}
	public List<UserGroup> getUserGroup(EcardServiceCondition condition){
		return userServices.getUserGroup(getCondition(condition));
	}
	
	public List<UserGroup> getAllUserGroup(EcardServiceCondition condition){
		return userServices.getAllUserGroup();
	}
	public boolean setUserGroup(EcardServiceCondition condition){
		return userServices.setUserGroup(condition.getIntValue("id"), condition.getIntValue("group"));
	}
	public List<User> getNoneHeadpicUsers(EcardServiceCondition condition){
		return userServices.getNoneHeadpicUsers();
	}
	public int addUser(EcardServiceCondition condition){
		User user = new User();
		user.setLoginname(condition.getValue("loginname"));
		user.setNickname(condition.getValue("nickname"));
		user.setPassword(condition.getValue("password"));
		user.setEmail(condition.getValue("email"));
		user.setSex(condition.getValue("sex","1"));
		UserGroup userGroup = new UserGroup();
		userGroup.setGroupid(condition.getIntValue("group"));
		return userServices.addUser(user);
	}
	
	public List<User> getAllUser(EcardServiceCondition condition){
		return userServices.getAllUser();	
	}
//	public boolean passHeadpic(){
//		
//	}
//	public booo
//	forbidHeadpic
}
