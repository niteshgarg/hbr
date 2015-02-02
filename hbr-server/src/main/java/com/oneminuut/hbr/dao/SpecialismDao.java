package com.oneminuut.hbr.dao;

import java.util.List;

import com.oneminuut.hbr.dao.domain.Specialism;

public interface SpecialismDao extends GenericDao<Specialism, Long> {

	public List<Specialism> getSpecialismForHospital(long id);

}
