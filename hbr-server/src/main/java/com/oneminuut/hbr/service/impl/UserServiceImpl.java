package com.oneminuut.hbr.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneminuut.hbr.controller.form.RegistrationForm;
import com.oneminuut.hbr.dao.RoleDao;
import com.oneminuut.hbr.dao.UserDao;
import com.oneminuut.hbr.dao.domain.Role;
import com.oneminuut.hbr.dao.domain.User;
import com.oneminuut.hbr.dto.UserValidationDTO;
import com.oneminuut.hbr.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	@Override
	public void saveUser(RegistrationForm registrationForm) {
		String email = registrationForm.getEmail();
		String roleType = registrationForm.getRole();

		Role role = roleDao.getRoleByType(roleType);

		User user = new User();
		user.setEmail(email);
		user.setRole(role);
		user.setPassword(registrationForm.getPassword());
		user.setCreated(new Date());

		userDao.saveUser(user);
	}

	@Override
	public UserValidationDTO validateUser(User user) {
		UserValidationDTO userValidationDTO = null;
		if (null == user) {
			userValidationDTO = new UserValidationDTO();
			userValidationDTO.setMessage("User data is not present");
			userValidationDTO.setStatusCode("ERROR_001");
		} else if (null == user.getEmail()) {
			userValidationDTO = new UserValidationDTO();
			userValidationDTO.setMessage("Email is not present");
			userValidationDTO.setStatusCode("ERROR_002");
		}
		return userValidationDTO;
	}

	public User getUser(long userId) {
		return userDao.get(userId);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
