package com.hexaware.lms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.CustomerDTO;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.entities.CustomerLoginResponse;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;

public interface ICustomerService {
	CustomerLoginResponse login(String username, String password) throws LoginCredentialsNotFound;

	List<Customer> viewAllCustomers();

	Customer viewCustomerDetailsById(long customerId) throws CustomerNotFoundException;

	Customer getCustomerByPhoneNumber(long phoneNumber);

	Customer getCustomerByEmail(String email);

	boolean register(CustomerDTO customerDTO, MultipartFile file) throws DataAlreadyPresentException, IOException;

	Customer updateCustomerAccount(Customer customer);
}
