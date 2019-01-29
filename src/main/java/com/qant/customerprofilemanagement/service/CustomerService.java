package com.qant.customerprofilemanagement.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.qant.customerprofilemanagement.exception.CustomerNotFoundException;
import com.qant.customerprofilemanagement.exception.DependencyFailureException;
import com.qant.customerprofilemanagement.model.Customer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

	private CrmApi crmApi;

	@HystrixCommand(fallbackMethod = "addCustomerFallback",
			commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",
			value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",
			value = "50"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",
			value = "60000"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",
			value = "60000"),
			@HystrixProperty(name = "execution.isolation.strategy",
			value = "SEMAPHORE"),
			@HystrixProperty(name = "execution.timeout.enabled",
			value = "false") })
	public Integer addCustomer(final Customer customer) {
		log.info("Entered into addCustomer() method of CustomerService class");
		try {
			return crmApi.addCustomer(customer).getCustomerId();
		} catch (HttpClientErrorException e) {
			throw new CustomerNotFoundException();
		}
	}

	protected Integer addCustomerFallback(final Customer customer,
			final Throwable exception) {
		log.info("Entered into addCustomerFallback() method of CustomerService class{}",
				customer.getCustomerId());
		throw new DependencyFailureException(exception);
	}

	@HystrixCommand(fallbackMethod = "updateCustomerFallback",
			commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",
			value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",
			value = "50"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",
			value = "60000"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",
			value = "60000"),
			@HystrixProperty(name = "execution.isolation.strategy",
			value = "SEMAPHORE"),
			@HystrixProperty(name = "execution.timeout.enabled",
			value = "false") })
	public void updateCustomer(final Integer customerId, final Customer customer) {
		log.info("Entered into updateCustomer() method of CustomerService class");
		Map<String, Integer> params = new HashMap<>();
		params.put("customerId", customerId);
		try {
			crmApi.findCustomer(params);
		} catch (HttpClientErrorException e) {
			throw new CustomerNotFoundException();
		}
		crmApi.updateCustomer(customer, params);
		log.info("Exitting from updateCustomer() method of CustomerService class");
	}

	protected void updateCustomerFallback(final Integer customerId,
			final Customer customer, final Throwable exception) {
		log.info("Entered into updateCustomerFallback() method of CustomerService class {}",
				customerId, customer);
		throw new DependencyFailureException(exception);
	}

	@HystrixCommand(fallbackMethod = "removeCustomerFallback",
			commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",
			value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",
			value = "50"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",
			value = "60000"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",
			value = "60000"),
			@HystrixProperty(name = "execution.isolation.strategy",
			value = "SEMAPHORE"),
			@HystrixProperty(name = "execution.timeout.enabled",
			value = "false") })
	public void removeCustomer(final Integer customerId) {
		log.info("Entered into removeCustomer() method of CustomerService class");
		Map<String, Integer> params = new HashMap<>();
		params.put("customerId", customerId);
		try {
			crmApi.findCustomer(params);
		} catch (HttpClientErrorException e) {
			throw new CustomerNotFoundException();
		}
		crmApi.removeCustomer(params);
		log.info("Entered into removeCustomer() method of CustomerService class");
	}

	protected void removeCustomerFallback(final Integer customerId,
			final Throwable exception) {
		log.info("Entered into removeCustomerFallback() method of CustomerService class {}",
				customerId);
		throw new DependencyFailureException(exception);
	}

}
