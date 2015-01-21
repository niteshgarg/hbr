package com.oneminuut.hbr.service;

import java.util.List;

import com.oneminuut.hbr.dao.domain.Bed;
import com.oneminuut.hbr.dao.domain.Hospital;
import com.oneminuut.hbr.dto.HospitalDTO;

public interface HospitalService {

	public List<Hospital> getAllHospitals();

	public HospitalDTO getHospital(long id);

	public List<Bed> getBedsForUnit(long id);

}
