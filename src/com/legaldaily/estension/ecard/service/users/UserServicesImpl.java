package com.legaldaily.estension.ecard.service.users;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.fzw.Globals;
import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.comment.Constants;
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
import com.sun.org.apache.regexp.internal.REUtil;

public class UserServicesImpl implements UserServices {

	UserRepository userRepository = null;

	public UserServicesImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public UserServicesImpl() {
		this.userRepository = (UserRepository) Globals.getBean("userRepository");
	}


	@Override
	public List<Score> getScoreRank(UserCondition condition) {
		return userRepository.getScoreRank(condition);
	}

	@Override
	public List<User> getUser(String ids[], String loginnames[], String nicknames[]) {
		List<User> users = new ArrayList<User>();
		if (ArrayUtils.isNotEmpty(ids)) {
			for (String string : ids) {
				int userid = StringValueUtils.getInt(string);
				if (userid > 0) {
					users.add(userRepository.getUser(userid));
				}
			}
		}
		if (ArrayUtils.isNotEmpty(loginnames) || ArrayUtils.isNotEmpty(nicknames)) {
			List<User> allUsers = userRepository.getAllUsers();
			for (User user : allUsers) {
				if (ArrayUtils.contains(loginnames, user.getLoginname()) || ArrayUtils.contains(nicknames, user.getNickname())) {
					users.add(user);
				}
			}
		}

		return users;
	}
	@Override
	public User getUser(int userid) {
		return userRepository.getUser(userid);
	}

	@Override
	public boolean setUserGroup(int userid, int groupid) {
		User user = userRepository.getUser(userid);
		UserGroup userGroup = userRepository.getUserGroup(groupid);
		if (user == null || userGroup == null)
			return false;
		user.setUserGroup(userGroup);
		return userRepository.setUserGroup(userid, groupid);
	}

	@Override
	public List<UserGroup> getUserGroup(UserCondition condition) {
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		String ids[] = EcardServiceCondition.getIds(condition.getValue("id"));
		if (ArrayUtils.isNotEmpty(ids)) {
			for (String string : ids) {
				int groupid = StringValueUtils.getInt(string);
				if (groupid > 0) {
					userGroups.add(userRepository.getUserGroup(groupid));
				}
			}
		}
		return userGroups;
	}

	@Override
	public List<User> getNoneHeadpicUsers() {
		List<User> allUsers = userRepository.getAllUsers();
		List<User> rv = new ArrayList<User>();
		for (User user : allUsers) {
			if (StringUtils.isBlank(user.getHeadpic()))
				rv.add(user);
		}
		return rv;
	}

	@Override
	public int addUser(String loginname, String nickname, String password, String email) {
		if (CollectionUtils.isNotEmpty(getUser(null, new String[] { loginname }, new String[] { nickname }))) {
			User user = new User();
			user.setLoginname(loginname);
			user.setNickname(nickname);
			user.setPassword(password);
			user.setEmail(email);
			return userRepository.addUser(user);
		}
		return 0;
	}

	@Override
	public List<UserGroup> getAllUserGroup() {
		return userRepository.getAllUserGroups();
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.getAllUsers();
	}

	@Override
	public List<User> getUserByGroup(int intValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> searchUsers(String loginname, String nickname, String email) {
		List<User> allUsers = getAllUser();
		List<User> rv = new ArrayList<User>();
		for (User user : allUsers) {
			if (StringUtils.contains(user.getEmail(), email) || StringUtils.contains(user.getLoginname(), loginname)
					|| StringUtils.contains(user.getNickname(), nickname)) {
				rv.add(user);
			}
		}
		return rv;
	}

	@Override
	public int addUser(User user) {
		if (StringUtils.isBlank(user.getLoginname()) || StringUtils.isBlank(user.getNickname())) {
			return 0;
		}
		if (CollectionUtils.isEmpty(getUser(null, new String[] { user.getLoginname() }, null))) {
			if (user.getUserGroup() == null || userRepository.getUserGroup(user.getUserGroup().getGroupid()) == null) {
				user.setUserGroup(userRepository.getUserGroup(
						StringValueUtils.getInt(sideService.getDefaultSetting(Constants.DEFAULT_REG_GROUP))));
			}
			return userRepository.addUser(user);
		}
		return 0;
	}

}
