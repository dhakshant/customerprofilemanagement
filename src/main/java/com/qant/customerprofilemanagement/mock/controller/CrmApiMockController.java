package com.qant.customerprofilemanagement.mock.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qant.customerprofilemanagement.util.ApplicationUtils;
import com.qant.customerprofilemanagement.model.Customer;

@RestController
public class CrmApiMockController {

	private static Map<Integer, Customer> customerMap = new HashMap<>();

	@PostMapping(path = "/crmapi/customer", produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> addCustomer(@RequestBody final Customer customer) {
		customer.setCustomerId(ApplicationUtils.generateId());
		customerMap.put(customer.getCustomerId(), customer);
		return new ResponseEntity<>(customer, null, HttpStatus.CREATED);
	}

	@GetMapping(path = "/crmapi/customers", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> findAllCustomers() {
		return new ResponseEntity<>(
				customerMap.entrySet().stream().map(Map.Entry::getValue)
				.collect(Collectors.toList()),
				HttpStatus.OK);
	}

	@GetMapping(path = "/crmapi/customer/{customerId}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> findCustomerById(
			@PathVariable("customerId") final Integer customerId) {
		if (customerMap.get(customerId) != null) {
			Optional<Customer> optCustomer = customerMap.entrySet().stream()
					.filter(entry -> entry.getKey().equals(customerId))
					.map(Map.Entry::getValue).findAny();
			if (optCustomer.isPresent()) {
				return new ResponseEntity<>(optCustomer.get(), HttpStatus.OK);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PutMapping(path = "/crmapi/customer/{customerId}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateCustomerById(
			@PathVariable("customerId") final Integer customerId,
			@RequestBody final Customer customer) {
		if (customerMap.entrySet().stream()
				.filter(entry -> entry.getKey().equals(customerId))
				.map(Map.Entry::getValue).findAny().isPresent()) {
			customer.setCustomerId(customerId);
			customerMap.replace(customerId, customer);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping(path = "/crmapi/customer/{customerId}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> removeCustomerById(
			@PathVariable("customerId") final Integer customerId) {
		if (customerMap.get(customerId) != null) {
			customerMap.remove(customerId);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}

}
