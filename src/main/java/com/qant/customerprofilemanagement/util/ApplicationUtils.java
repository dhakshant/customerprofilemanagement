package com.qant.customerprofilemanagement.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ApplicationUtils {

	private ApplicationUtils() {
	        throw new UnsupportedOperationException(
	        		"You are not allowed to instantiate "
	        + this.getClass().getName() + " class");
	        }

	public static Integer generateId() {
		log.info("Generating next id sequence...");
		return CustomerIdGenerator.getInstance().getNextSequence();
	}
}
