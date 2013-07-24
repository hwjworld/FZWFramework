package com.legaldaily.estension.ecard.repository.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.legaldaily.estension.ecard.model.condition.UserCondition;
import com.legaldaily.estension.ecard.model.user.Score;
import com.legaldaily.estension.ecard.model.user.User;
import com.legaldaily.estension.ecard.model.user.UserGroup;

@Repository("userdao")
public class UserDaoSql extends EcardDao implements UserDao{

	
	@Override
	public User selectUser(int userid) {
		User user = null;
		SqlSession session = sessionFactory.openSession();
		try {
			user = (User) session.selectOne("selectUserInfo", userid);
		} finally {
			session.close();
		}
		return user;
	}

	@Override
	public boolean insertUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> selectAllUsers() {
		return (List<User>) selectList("selectAllUsers");
	}
	@Override
	public int selectAllUsersCount() {
		return (Integer) selectOne("selectAllUsersCount");
	}

	@Override
	public List<UserGroup> selectAllUserGroups() {
		List<UserGroup> list = (List<UserGroup>) selectList("selectAllUserGroups");
//		List<UserGroup> list = null;
//		SqlSession session = sessionFactory.openSession();
//		try {
//			list = session.selectList("selectAllUserGroups");
//		} finally {
//			session.close();
//		}
		return list;
	}
	
	@Override
	public UserGroup selectUserGroup(int groupid) {
		UserGroup group = null;
		SqlSession session = sessionFactory.openSession();
		try {
			group = (UserGroup) session.selectOne("selectUserGroup",groupid);
		} finally {
			session.close();
		}
		return group;
	}

	@Override
	public List<Score> getScoreRank(UserCondition condition) {
		return (List<Score>) selectList("getScoreRank",condition);
	}

	@Override
	public int setUserGroup(int userid, int groupid) {
		return update("setUserGroup",genMabatisMapParameter(userid, groupid));
	}

	@Override
	public int addUser(User user) {
		return insert("insertUser",user);
//		return getMysqlLastId();
	}

}
