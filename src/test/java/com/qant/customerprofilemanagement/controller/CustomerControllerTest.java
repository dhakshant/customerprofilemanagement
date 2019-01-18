package com.qant.customerprofilemanagement.controller;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.qant.customerprofilemanagement.model.Customer;
import com.qant.customerprofilemanagement.model.Response;
import com.qant.customerprofilemanagement.service.CustomerService;

public class CustomerControllerTest {
	
	private CustomerController customerController;
	private CustomerService customerService = Mockito.mock(CustomerService.class);
	
	@Before
	public void setup() {
		customerController = new CustomerController(customerService);
	}
	
	@Test
	public void testAddCustomer() {
		Customer c = new Customer();
		Mockito.when(customerService.addCustomer(c)).thenReturn(100);
		ResponseEntity<Response> addCustomer = customerController.addCustomer(c);
		assertThat(addCustomer.getBody().getCustomerId(), Matchers.equalTo(100));
	}

	@Test
	public void testUpdateCustomer() {
		Customer c = new Customer();
		Mockito.doNothing().when(customerService).updateCustomer(100, c);
		ResponseEntity<Response> updateCustomer = customerController.updateCustomer(100, c);
		assertThat(updateCustomer.getBody().getStatus(), Matchers.equalTo("Customer Updated Successfully."));
	}

	@Test
	public void testRemoveCustomer() {
		Mockito.doNothing().when(customerService).removeCustomer(100);
		ResponseEntity<Response> removeCustomer = customerController.removeCustomer(100);
		assertThat(removeCustomer.getBody().getStatus(), Matchers.equalTo("Customer removed Successfully."));
	}

}
