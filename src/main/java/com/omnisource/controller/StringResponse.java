package com.omnisource.controller;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;

public class StringResponse {

	private String response;

	@JsonValue
	@JsonRawValue
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public StringResponse(String s) {
		this.response = s;
	}

}
