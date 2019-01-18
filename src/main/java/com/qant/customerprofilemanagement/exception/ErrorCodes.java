package com.qant.customerprofilemanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {

	CUSTOMER_NOT_FOUND("01", 404, "Customer profile not found"),
	BAD_REQUEST("02", 400, "Incorrect request message");

	private final String errorCode;
	private final int httpStatus;
	private final String errorMessage;

}