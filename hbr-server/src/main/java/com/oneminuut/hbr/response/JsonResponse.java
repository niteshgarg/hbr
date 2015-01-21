package com.oneminuut.hbr.response;

import java.io.Serializable;
import java.util.Map;

public class JsonResponse implements Serializable {
	
	private static final long serialVersionUID = 3056859360668352746L;

	public String status;
	
	public Map<String ,String> errorMap;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}
}
