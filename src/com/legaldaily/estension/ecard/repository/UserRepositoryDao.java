package com.legaldaily.estension.ecard.repository;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Element;

import org.apache.commons.collections.CollectionUtils;

import com.legaldaily.estension.ecard.model.EcardModel;
import com.legaldaily.estension.ecard.model.condition.UserCondition;
import com.legaldaily.estension.ecard.model.question.Question;
import com.legaldaily.estension.ecard.model.user.Score;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;
import com.legaldaily.estension.ecard.repository.dao.UserDao;
import com.legaldaily.estension.ecard.repository.dao.UserDaoSql;

public class UserRepositoryDao extends RepositoryDao implements UserRepository {
	private static UserDao userDao = null;
	
	public UserRepositoryDao() {
		userDao = new UserDaoSql();
	}
	@Override
	public User getUser(int userid) {

		if(userid <= 0){
			return null;
		}
		User user = null;
		if(!containKeyInCache(userCache, userid)){

			user =  userDao.selectUser(userid);
			if (user == null) {
				return null;
			}
			setUsersGroup(user);
			storeUser(user);		
		}
		user = (User) getValueFromCache(userCache, userid);
		return user;
	}
	
	@Override
	public List<User> getAllUsers() {
		List<? extends EcardModel> list = getAllCacheValues(userCache);
		if(CollectionUtils.isEmpty(list) || list.size()!=userDao.selectAllUsersCount()){
			List<User> users = userDao.selectAllUsers();
			setUsersGroup(users);
			for(User user: users){
				storeUser(user);
			}
			list = users;
		}
		return (List<User>) list;
	}
	@Override
	public List<UserGroup> getAllUserGroups() {
		List<? extends EcardModel> list = getAllCacheValues(userGroupCache);
		if(CollectionUtils.isEmpty(list)){
			List<UserGroup> userGroups = userDao.selectAllUserGroups();
			for(UserGroup group: userGroups){
				storeUserGroup(group);
			}
			list = userGroups;
		}
		return (List<UserGroup>) list;
	}

	@Override
	public int addUser(User user) {
		int userid = userDao.addUser(user);
		if(userid > 0)
			getUser(userid);
		return userid;
	}

	@Override
	public boolean removeUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserGroup getUserGroup(int groupid) {
		UserGroup group = null;
		if (!containKeyInCache(userGroupCache, groupid)) {
			group = userDao.selectUserGroup(groupid);
			if (group == null) {
				return null;
			}
			storeUserGroup(group);
		}
		group = (UserGroup) getValueFromCache(userGroupCache, groupid);
		return group;
	}
	@Override
	public List<Score> getScoreRank(UserCondition condition) {
		List<Score> list = userDao.getScoreRank(condition);
		List<Score> rvList = new ArrayList<Score>();
		
		for (Score score : list) {
			if(score!= null && score.getUser().getUid()>0){
				score.setUser(getUser(score.getUser().getUid()));
				rvList.add(score);
			}
		}
		return rvList;
	}
	@Override
	public boolean setUserGroup(int userid, int groupid) {
		return userDao.setUserGroup(userid,groupid)>0?true:false;
	}
}
