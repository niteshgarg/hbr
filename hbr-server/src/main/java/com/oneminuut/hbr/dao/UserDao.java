package com.oneminuut.hbr.dao;

import com.oneminuut.hbr.dao.domain.User;

public interface UserDao extends GenericDao<User, Long> {

	public void saveUser(User user);
	
	public User getUserByEmail(String email);

}
