package com.oneminuut.hbr.dto;

public class BedDTO {

	private long id;

	private String number;

	private String status;

	private String yesterdayStatus;

	private String name;

	private String reservationStartDate;

	private String reservationEndDate;
	
	private String specialism;
	
	private String userName;
	
	private String roomName;
	
		
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSpecialism() {
		return specialism;
	}

	public void setSpecialism(String specialism) {
		this.specialism = specialism;
	}

	public String getReservationStartDate() {
		return reservationStartDate;
	}

	public void setReservationStartDate(String reservationStartDate) {
		this.reservationStartDate = reservationStartDate;
	}

	public String getReservationEndDate() {
		return reservationEndDate;
	}

	public void setReservationEndDate(String reservationEndDate) {
		this.reservationEndDate = reservationEndDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
