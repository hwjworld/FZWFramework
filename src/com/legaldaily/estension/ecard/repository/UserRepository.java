package com.legaldaily.estension.ecard.repository;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.UserCondition;
import com.legaldaily.estension.ecard.model.user.Score;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;

public interface UserRepository {
	public User getUser(int userid);
	
	public int addUser(User user);

	public boolean removeUser(User user);

	public UserGroup getUserGroup(int groupid);
	
	public List<User> getAllUsers();
	public List<UserGroup> getAllUserGroups();
	public List<Score> getScoreRank(UserCondition condition);

	public boolean setUserGroup(int userid, int groupid);

}
