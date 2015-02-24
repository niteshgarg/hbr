package com.oneminuut.hbr.dao.domain;

import java.io.Serializable;

public enum RESERVATION__REQUEST_TYPE implements Serializable {

	MARK_CLOSED("MARK_CLOSED"), REMOVE_CLOSED("REMOVE_CLOSED"), MARK_FREE(
			"MARK_FREE"), NEW_RESERVATION("NEW_RESERVATION"), DAY_CARE(
			"DAY_CARE");
	private final String status;

	private RESERVATION__REQUEST_TYPE(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
