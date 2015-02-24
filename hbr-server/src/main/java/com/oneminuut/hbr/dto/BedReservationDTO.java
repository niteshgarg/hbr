package com.oneminuut.hbr.dto;

public class BedReservationDTO {

	private long bedId;

	private String startDate;

	private String endDate;

	private String requestType;

	private long specialismId;

	private long yesterdayReservationId;

	public long getYesterdayReservationId() {
		return yesterdayReservationId;
	}

	public void setYesterdayReservationId(long yesterdayReservationId) {
		this.yesterdayReservationId = yesterdayReservationId;
	}

	public long getSpecialismId() {
		return specialismId;
	}

	public void setSpecialismId(long specialismId) {
		this.specialismId = specialismId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBedId() {
		return bedId;
	}

	public void setBedId(long bedId) {
		this.bedId = bedId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
