package com.legaldaily.estension.ecard.service.users;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.UserCondition;
import com.legaldaily.estension.ecard.model.law.Lawyer;
import com.legaldaily.estension.ecard.model.user.Score;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;
import com.legaldaily.estension.ecard.service.EcardService;

public interface UserServices extends EcardService {
	public List<Score> getScoreRank(UserCondition condition);
	public List<User> getUser(String ids[],String loginnames[], String nicknames[]);
	public boolean setUserGroup(int userid, int groupid);
	public List<UserGroup> getUserGroup(UserCondition condition);
	public List<User> getNoneHeadpicUsers();
	public int addUser(String loginname, String nickname, String password, String email);
	/**
	 * 
	 * @param user
	 * @return new user id
	 */
	public int addUser(User user);
	
	public List<UserGroup> getAllUserGroup();
	public List<User> getAllUser();
	public List<User> getUserByGroup(int intValue);
	public List<User> searchUsers(String loginname, String nickname, String email);
	public User getUser(int userid);
}
