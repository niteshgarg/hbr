package com.oneminuut.hbr.service;

import com.oneminuut.hbr.controller.form.RegistrationForm;
import com.oneminuut.hbr.dao.domain.User;
import com.oneminuut.hbr.dto.UserValidationDTO;

public interface UserService {

	public User getUserByEmail(String email);
	
	public void saveUser(RegistrationForm registrationForm);
	
	public User getUser(long userId);
	
	public UserValidationDTO validateUser(User user);
}
