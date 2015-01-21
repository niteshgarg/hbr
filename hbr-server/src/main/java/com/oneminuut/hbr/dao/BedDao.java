package com.oneminuut.hbr.dao;

import java.util.List;

import com.oneminuut.hbr.dao.domain.Bed;

public interface BedDao extends GenericDao<Bed, Long> {

	public List<Bed> getBedsForUnit(long id);
}
