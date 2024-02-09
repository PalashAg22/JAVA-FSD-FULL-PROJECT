package com.hexaware.lms.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.lms.dto.CustomerDTO;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	CustomerRepository repo;
	
	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		logger.info("Customer is loggin in...");
		return false;
	}

	@Override
	public boolean register(CustomerDTO customerDTO) throws DataAlreadyPresentException {
		Customer customerByPhone = getCustomerByPhoneNumber(customerDTO.getPhoneNumer());
		Customer customerByEmail = getCustomerByEmail(customerDTO.getEmail());
		if(customerDTO.getPhoneNumer()==customerByPhone.getPhoneNumer() || customerDTO.getEmail()==customerByEmail.getEmail()) {
			logger.warn("User is trying to enter DUPLICATE data while registering");
			throw new DataAlreadyPresentException("PhoneNumber or Email already taken...Trying Logging in..!");
		}
		Customer customer = new Customer();
		customer.setCustomerFirstName(customerDTO.getCustomerFirstName());
		customer.setCustomerLastName(customerDTO.getCustomerLastName());
		customer.setEmail(customerDTO.getEmail());
		customer.setPhoneNumer(customerDTO.getPhoneNumer());
		customer.setPassword(customerDTO.getPassword());
		customer.setDateOfBirth(customerDTO.getDateOfBirth());
		customer.setAddress(customerDTO.getAddress());
		customer.setCountry("India");
		customer.setState(customerDTO.getState());
		customer.setCreditScore(customerDTO.getCreditScore());
		customer.setPanCardNumber(customerDTO.getPanCardNumber());
		customer.setIdProof(customerDTO.getIdProof());
		customer.setRole("Regular");
		logger.info("Registerd Customer: "+customer);
		Customer addedCustomer = repo.save(customer);

		if(addedCustomer != null) {
			logger.info("Registerd Customer: "+addedCustomer);
			return true;
		}
		logger.error("Customer not registered");
		return false;
	}

	@Override
	public List<Customer> viewAllCustomers() {
		logger.info("Viewing all Customers");
		return repo.findAll();
	}

	@Override
	public Customer viewCustomerDetailsById(long customerId) throws CustomerNotFoundException {
		List<Customer> customers = repo.findAll();
		boolean isPresent=false;
		for(Customer c:customers) {
			if(c.getCustomerId()==customerId) {
				isPresent=true;
				break;
			}
		}
		if(!isPresent) {
			logger.warn("No customer found re...");
			throw new CustomerNotFoundException("No Customer found with id: "+customerId);
		}
		return repo.findById(customerId).orElse(null);		
	}

	@Override
	public Customer getCustomerByPhoneNumber(long phoneNumber) {
		logger.info("Finding "+phoneNumber+" in database...");
		return repo.findByPhoneNumber(phoneNumber);
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		logger.info("Finding "+email+" in database");
		return repo.findByEmail(email);
	}

}
