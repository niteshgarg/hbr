package com.oneminuut.hbr.dao;

import java.util.Date;
import java.util.List;

import com.oneminuut.hbr.dao.domain.Bed;
import com.oneminuut.hbr.dao.domain.BedReservation;

public interface BedDao extends GenericDao<Bed, Long> {

	public List<Bed> getBedsForUnit(long id);

	public List<BedReservation> getReservationsForBed(long id, Date date);
}
