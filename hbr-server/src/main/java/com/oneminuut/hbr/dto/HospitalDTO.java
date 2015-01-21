package com.oneminuut.hbr.dto;

import java.util.HashSet;
import java.util.Set;


public class HospitalDTO {

	private long id;

	private String name;

	private Set<DepartmentDTO> departments = new HashSet<>();
	
	public Set<DepartmentDTO> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<DepartmentDTO> departments) {
		this.departments = departments;
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
