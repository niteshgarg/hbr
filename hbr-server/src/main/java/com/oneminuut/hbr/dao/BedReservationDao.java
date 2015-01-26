package com.oneminuut.hbr.dao;

import java.util.Date;

import com.oneminuut.hbr.dao.domain.BedReservation;

public interface BedReservationDao extends GenericDao<BedReservation, Long> {
	
	public BedReservation getBedReservationForDate(long bedId, Date endDate,
			Date startDate);

}
