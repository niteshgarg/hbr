package com.oneminuut.hbr.dto;

public class BedDTO {

	private long id;

	private String number;

	private String status;

	private String yesterdayStatus;

	public String getYesterdayStatus() {
		return yesterdayStatus;
	}

	public void setYesterdayStatus(String yesterdayStatus) {
		this.yesterdayStatus = yesterdayStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
