package com.oneminuut.hbr.dto;

import java.util.Set;

public class DepartmentDTO {

	private long id;

	private String name;

	private Set<UnitDTO> units;

	private long departmentNumber;

	public long getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(long departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public Set<UnitDTO> getUnits() {
		return units;
	}

	public void setUnits(Set<UnitDTO> units) {
		this.units = units;
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
