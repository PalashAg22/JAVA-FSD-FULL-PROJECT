package com.hexaware.lms.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.CustomerDTO;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.entities.CustomerLoginResponse;
import com.hexaware.lms.entities.CustomerProof;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;
import com.hexaware.lms.repository.CustomerRepository;
import com.hexaware.lms.repository.UploadIdentityProofRepository;

@Service
public class CustomerServiceImpl implements ICustomerService {


	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	CustomerRepository repo;

	@Autowired
	IUploadIdProofService idUploadService;

	@Autowired
	UploadIdentityProofRepository proofRepo;

	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public CustomerLoginResponse login(String username, String password) throws LoginCredentialsNotFound {
		logger.info("Someone is logging in...");
		CustomerLoginResponse response = new CustomerLoginResponse();
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(username);
			Customer customer = getCustomerByEmail(username);
			response.setJwtToken(token);
			response.setCustomer(customer);
			if (token != null) {
				logger.info("Token for Customer: " + token);
			} else {
				logger.warn("Token not generated");
			}
		} else {
			throw new LoginCredentialsNotFound("Credentials not found");
		}
		return response;
	}

	@Override
	public boolean register(CustomerDTO customerDTO, MultipartFile file)
			throws DataAlreadyPresentException, IOException {
		logger.info("New Registration Request for: "+customerDTO);
		Customer customerByPhone = getCustomerByPhoneNumber(customerDTO.getPhoneNumber());
		Customer customerByEmail = getCustomerByEmail(customerDTO.getEmail());
		if ((customerByPhone != null || customerByEmail != null)) {
			logger.warn("User is trying to enter DUPLICATE data while registering");
			throw new DataAlreadyPresentException("PhoneNumber or Email already taken...Trying Logging in..!");
		}
		Customer customer = new Customer();
		customer.setCustomerFirstName(customerDTO.getCustomerFirstName());
		customer.setCustomerLastName(customerDTO.getCustomerLastName());
		customer.setEmail(customerDTO.getEmail());
		customer.setPhoneNumber(customerDTO.getPhoneNumber());
		customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
		LocalDate dob = customerDTO.getDateOfBirth();
		customer.setDateOfBirth(dob);
		LocalDate now = LocalDate.now();
		Period period = Period.between(dob, now);
		int age = period.getYears();		
		customer.setAge(age);
		customer.setGender(customerDTO.getGender());
		customer.setAddress(customerDTO.getAddress());
		customer.setState(customerDTO.getState());
		customer.setCreditScore(customerDTO.getCreditScore());
		customer.setPanCardNumber(customerDTO.getPanCardNumber());


		CustomerProof customerProof = idUploadService.uploadPdf(file);
		if (customerProof != null) {
			logger.info("Customer Id Proof uploaded succesfully");
		}

		customer.setIdProofFile(customerProof);
		logger.info("Registering Customer: " + customer);
		Customer addedCustomer = repo.save(customer);
		logger.info("Registerd Customer: " + addedCustomer);

		return true;
	}

	@Override
	public List<Customer> viewAllCustomers() {
		logger.info("Viewing all Customers");
		return repo.findAll();
	}

	@Override
	public Customer viewCustomerDetailsById(long customerId) throws CustomerNotFoundException {
		Stream<Customer> stream = repo.findAll().stream();
		Customer isPresent = stream.filter(customers -> customers.getCustomerId()==customerId).findAny().orElse(null);
		if (isPresent==null) {
			logger.warn("No customer found re...");
			throw new CustomerNotFoundException("No Customer found with id: " + customerId);
		}
		return repo.findById(customerId).orElse(null);
	}

	@Override
	public Customer getCustomerByPhoneNumber(long phoneNumber) {
		logger.info("Finding " + phoneNumber + " in database...");
		return repo.findByPhoneNumber(phoneNumber);
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		logger.info("Finding " + email + " in database");
		return repo.findByEmail(email).orElse(null);
	}

	@Override
	public Customer updateCustomerAccount(Customer customer) {
		return repo.save(customer);
	}

}
