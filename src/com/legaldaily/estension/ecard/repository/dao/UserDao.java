package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import com.legaldaily.estension.ecard.model.condition.UserCondition;
import com.legaldaily.estension.ecard.model.user.Score;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;

public interface UserDao {

	
	public User selectUser(int userid);
	
	public boolean insertUser(User user);

	public boolean deleteUser(User user);

	public UserGroup selectUserGroup(int groupid);

	public List<User> selectAllUsers();
	public int selectAllUsersCount();
	public List<UserGroup> selectAllUserGroups();
	public List<Score> getScoreRank(UserCondition condition);

	public int setUserGroup(int userid, int groupid);

	public int addUser(User user);
	
}
