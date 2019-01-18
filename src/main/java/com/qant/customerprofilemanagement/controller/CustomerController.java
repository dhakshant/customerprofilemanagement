package com.qant.customerprofilemanagement.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.qant.customerprofilemanagement.model.Response;
import com.qant.customerprofilemanagement.api.CustomerApi;
import com.qant.customerprofilemanagement.exception.CustomerNotFoundException;
import com.qant.customerprofilemanagement.model.Customer;
import com.qant.customerprofilemanagement.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class CustomerController implements CustomerApi {

	private CustomerService customerService;

	@Override
	public ResponseEntity<Response> addCustomer(
			@Valid @RequestBody final Customer customer) {
		log.info("Customer creation request received..");
		Integer customerId = customerService.addCustomer(customer);
		Response response = new Response();
		response.setCustomerId(customerId);
		response.setStatus("Customer Created Successfully.");
		log.info("Customer {} created successfully", customerId);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Response> updateCustomer(
			@PathVariable("customerId") final Integer customerId,
			@Valid @RequestBody final Customer customer) {
		log.info("Customer update request received..");
		try {
			customerService.updateCustomer(customerId, customer);
			Response response = new Response();
			response.setCustomerId(customerId);
			response.setStatus("Customer Updated Successfully.");
			log.info("Customer {} updated successfully", customerId);
			return ResponseEntity.ok(response);
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<Response> removeCustomer(
			@PathVariable("customerId") final Integer customerId) {
		log.info("Customer delete request received..");
		try {
			customerService.removeCustomer(customerId);
			Response response = new Response();
			response.setCustomerId(customerId);
			response.setStatus("Customer removed Successfully.");
			log.info("Customer {} deleted successfully", customerId);
			return ResponseEntity.ok(response);
		} catch (CustomerNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
