package com.hexaware.lms.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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
	public String login(String username, String password) throws LoginCredentialsNotFound {
		logger.info("Customer is logging in...");
		String token = null;
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (authentication.isAuthenticated()) {
			token = jwtService.generateToken(username);
			if (token != null) {
				logger.info("Token for User: " + token);
			} else {
				logger.warn("Token not generated");
			}
		} else {
			throw new LoginCredentialsNotFound("Credentials not found");
		}
		return token;

	}

	@Override
	public boolean register(CustomerDTO customerDTO, MultipartFile file)
			throws DataAlreadyPresentException, IOException {
		Customer customerByPhone = getCustomerByPhoneNumber(customerDTO.getPhoneNumer());
		Customer customerByEmail = getCustomerByEmail(customerDTO.getEmail());
		if ((customerByPhone != null || customerByEmail != null)) {
			logger.warn("User is trying to enter DUPLICATE data while registering");
			throw new DataAlreadyPresentException("PhoneNumber or Email already taken...Trying Logging in..!");
		}
		Customer customer = new Customer();
		customer.setCustomerFirstName(customerDTO.getCustomerFirstName());
		customer.setCustomerLastName(customerDTO.getCustomerLastName());
		customer.setEmail(customerDTO.getEmail());
		customer.setPhoneNumer(customerDTO.getPhoneNumer());
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
		List<Customer> customers = repo.findAll();
		boolean isPresent = false;
		for (Customer c : customers) {
			if (c.getCustomerId() == customerId) {
				isPresent = true;
				break;
			}
		}
		if (!isPresent) {
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

}
