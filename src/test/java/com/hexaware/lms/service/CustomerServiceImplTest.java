package com.hexaware.lms.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.lms.dto.CustomerDTO;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.DataAlreadyPresentException;

@SpringBootTest
class CustomerServiceImplTest {

	Logger log = LoggerFactory.getLogger(AdminServiceImplTest.class);
	
	@Autowired
	ICustomerService serviceTest;

	@Test
	void testLogin() {
//		String username="suraj@kumar.com";
//		String password="password123";
//		assertTrue(serviceTest.login(username, password));
	}

	@Test
	void testRegister() throws DataAlreadyPresentException {
		CustomerDTO customer = new CustomerDTO();
		customer.setCustomerFirstName("Suraj");
		customer.setCustomerLastName("Kumar");
		customer.setEmail("Suraj@kumar.com");
		customer.setPhoneNumer(7352442612L);
		customer.setPassword("password123");
		customer.setDateOfBirth(LocalDate.of(2010, 10, 10));
		customer.setAddress("Muzaffarpur");
		customer.setCountry("India");
		customer.setState("Bihar");
		customer.setCreditScore(345);
		customer.setPanCardNumber("PAN123");
		customer.setIdProof(new byte[5]);
		customer.setRole("Regular");

		log.info("Test running to register a new customer: "+customer);
		boolean isRegistered = serviceTest.register(customer);
		assertTrue(isRegistered);
	}

	@Test
	void testViewAllCustomers() {
		List<Customer> customers = serviceTest.viewAllCustomers();
		log.info("Test running to view all customers");
		assertNotNull(customers);
	}

	@Test
	void testViewCustomerDetails() throws CustomerNotFoundException {
		long customerId=1001;
		Customer customer = serviceTest.viewCustomerDetailsById(customerId);
		log.info("Test running to view details of customer: "+customer);
		assertNotNull(customer);
	}

}
