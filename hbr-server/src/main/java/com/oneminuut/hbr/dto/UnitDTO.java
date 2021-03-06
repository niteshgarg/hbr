package com.oneminuut.hbr.dto;

import java.util.Set;

public class UnitDTO {

	private long id;

	private String name;

	private Set<BedDTO> beds;
	
	private long unitNumber;

	
	public long getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(long unitNumber) {
		this.unitNumber = unitNumber;
	}

	public Set<BedDTO> getBeds() {
		return beds;
	}

	public void setBeds(Set<BedDTO> beds) {
		this.beds = beds;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
