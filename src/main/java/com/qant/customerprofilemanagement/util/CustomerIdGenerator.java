package com.qant.customerprofilemanagement.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class CustomerIdGenerator {

	private AtomicInteger atomicInteger;
	private static CustomerIdGenerator obj = null;

	private CustomerIdGenerator(final int initialValue) {
		this.atomicInteger = new AtomicInteger(initialValue);
	}

	public static CustomerIdGenerator getInstance() {
		if (obj == null) {
			obj = new CustomerIdGenerator(Constants.ONE);
		}
		return obj;
	}

	public int getNextSequence() {
		return atomicInteger.getAndIncrement();
	}
}
