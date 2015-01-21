package com.oneminuut.hbr.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.oneminuut.hbr.dao.DepartmentDao;
import com.oneminuut.hbr.dao.domain.Department;

@Repository("departmentDao")
public class DepartmentDaoImpl extends GenericDaoImpl<Department, Long> implements
		DepartmentDao {

	private static final Logger logger = Logger.getLogger(DepartmentDaoImpl.class);

	public DepartmentDaoImpl() {
		super(Department.class);
	}

	

}
