package com.oneminuut.hbr.dto;

public class HBRResponse {
	private String statusCode;
	private String message;
	private String authToken;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	@Override
	public String toString() {
		return "JainBookResponse [statusCode=" + statusCode + ", message="
				+ message + ", authToken=" + authToken + "]";
	}

}
