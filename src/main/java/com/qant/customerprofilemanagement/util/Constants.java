package com.qant.customerprofilemanagement.util;

public final class Constants {

	private Constants() {
		throw new UnsupportedOperationException(
				"You are not allowed to instantiate " + this.getClass().getName() + " class");
	}

	public static final String X_CORRELATION_ID = "X-Correlation-Id";
	public static final String CORRELATION_ID = "correlationId";
	public static final String X_REQUEST_ID = "X-Request-Id";
	public static final String AGENCY_CODE = "agencyCode";
	public static final String IMS_TRACK_ID = "imsTrackId";
	public static final int FILTER_ORDER_1 = 1;
	public static final int FILTER_ORDER_10 = 10;
	public static final String CONTENT_TYPE = "Content-Type";
	public static final int DEFAULT_READ_TIMEOUT = 30000;
	public static final int DEFAULT_CONNECT_TIMEOUT = 3000;
	public static final String STRING = "String";
	public static final String IMS_TOKEN = "IMS_TOKEN";
	public static final String REGION_CODE = "regionCode";
	public static final int ONE = 1;
	public static final String USERNAMES = "testuser1,testuser2";
	public static final String TESTUSER1 = "testuser1";
	public static final String TESTUSER2 = "testuser2";
	public static final String PASS1 = "password1";
	public static final String PASS2 = "password2";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ADMIN = "admin";
	public static final String LASTNAME = "LastName";
	public static final String UTF_8 = "UTF-8";
	public static final String KEY = "mykeys.jks";
	public static final String MYPASS = "mypass";
	public static final String MYKEYS = "mykeys";

}