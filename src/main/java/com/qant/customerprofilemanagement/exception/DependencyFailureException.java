package com.qant.customerprofilemanagement.exception;

@SuppressWarnings("serial")
public class DependencyFailureException extends RuntimeException {

	public DependencyFailureException(final Throwable exp) {
		super(exp);
	}
}