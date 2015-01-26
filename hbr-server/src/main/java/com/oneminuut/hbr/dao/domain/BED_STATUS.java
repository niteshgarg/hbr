package com.oneminuut.hbr.dao.domain;

import java.io.Serializable;

public enum BED_STATUS implements Serializable {

	GREEN("GREEN"), GRAY("GRAY"), BLUE("BLUE"), PINK("PINK"), ORANGE("ORANGE"), ORANGE_BLINKING(
			"ORANGE_BLINKING");
	
	private final String status;

	private BED_STATUS(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
