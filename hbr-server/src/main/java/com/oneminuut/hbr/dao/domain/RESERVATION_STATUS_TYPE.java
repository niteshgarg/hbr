package com.oneminuut.hbr.dao.domain;

import java.io.Serializable;

public enum RESERVATION_STATUS_TYPE implements Serializable {

	CLOSED("CLOSED"), MARKED_FREE("MARKED_FREE"), OCCUPIED("OCCUPIED"), DAY_CARE(
			"DAY_CARE"), MARKED_CLOSED_FREE("MARKED_CLOSED_FREE");
	private final String status;

	private RESERVATION_STATUS_TYPE(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
