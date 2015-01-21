package com.oneminuut.hbr.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.oneminuut.hbr.dao.UnitDao;
import com.oneminuut.hbr.dao.domain.Unit;

@Repository("unitDao")
public class UnitDaoImpl extends GenericDaoImpl<Unit, Long> implements
		UnitDao {

	private static final Logger logger = Logger
			.getLogger(UnitDaoImpl.class);

	public UnitDaoImpl() {
		super(Unit.class);
	}

	
	
	
}
