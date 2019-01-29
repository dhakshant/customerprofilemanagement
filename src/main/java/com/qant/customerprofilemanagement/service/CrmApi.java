package com.qant.customerprofilemanagement.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.qant.customerprofilemanagement.configuration.ApplicationConfig;
import com.qant.customerprofilemanagement.model.Customer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CrmApi {

	private RestTemplate restTemplate;
	private ApplicationConfig config;

	public Customer findCustomer(final Map<String, Integer> params) {
		return restTemplate.getForObject(
				config.getCrmApiGatewayBaseUrl()
				.concat(config.getCustomerFindByIdUrl()), Customer.class, params);
	}

	public Customer addCustomer(final Customer customer) {
		return restTemplate.postForEntity(
					config.getCrmApiGatewayBaseUrl().concat(
							config.getCustomerCreateUrl()),
					customer, Customer.class).getBody();
	}

	public void updateCustomer(final Customer customer, final Map<String, Integer> params) {
		restTemplate.put(config.getCrmApiGatewayBaseUrl().concat(
				config.getCustomerUpdateUrl()),	customer, params);
	}

	public void removeCustomer(final Map<String, Integer> params) {
		restTemplate.delete(config.getCrmApiGatewayBaseUrl()
				.concat(config.getCustomerDeleteUrl()), params);
	}
}

