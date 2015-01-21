package com.oneminuut.hbr.dao;

import com.oneminuut.hbr.dao.domain.Hospital;

public interface HospitalDao extends GenericDao<Hospital, Long> {
	
	public Hospital getHospital(long id);

}
