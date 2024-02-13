package com.hexaware.lms.service;

import java.util.List;

import com.hexaware.lms.dto.CustomerDTO;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;

public interface ICustomerService {
	String login(String username, String password) throws LoginCredentialsNotFound;

	boolean register(CustomerDTO customer) throws DataAlreadyPresentException;

	List<Customer> viewAllCustomers();

	Customer viewCustomerDetailsById(long customerId) throws CustomerNotFoundException;

	Customer getCustomerByPhoneNumber(long phoneNumber);

	Customer getCustomerByEmail(String email);
}
