package com.oneminuut.hbr.service;

import java.util.Date;
import java.util.List;

import com.oneminuut.hbr.dao.domain.Bed;
import com.oneminuut.hbr.dao.domain.BedReservation;
import com.oneminuut.hbr.dao.domain.Hospital;
import com.oneminuut.hbr.dao.domain.Specialism;
import com.oneminuut.hbr.dto.HospitalDTO;

public interface HospitalService {

	public List<Hospital> getAllHospitals();

	public HospitalDTO getHospital(long id);

	public List<Bed> getBedsForUnit(long id);

	public List<BedReservation> getReservationsForBed(long id, Date date);

	public BedReservation getBedReservationForDate(long id, Date endDate,
			Date startDate);

	public void saveBedReservation(BedReservation bedReservation);

	public Bed getBed(long bedId);

	public List<Specialism> getSpecialismForHospital(long id);

	public Specialism getSpecialism(long id);

}
