package com.oneminuut.hbr.dao;

import com.oneminuut.hbr.dao.domain.Role;

public interface RoleDao extends GenericDao<Role, Long> {
	
	public Role getRoleByType(String Type);
}
