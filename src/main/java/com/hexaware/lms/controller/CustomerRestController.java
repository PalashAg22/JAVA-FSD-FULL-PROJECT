package com.hexaware.lms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.CustomerDTO;
import com.hexaware.lms.dto.LoanApplicationRequestDTO;
import com.hexaware.lms.dto.LoanApplicationRequestDTOMapper;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.exception.CustomerNotEligibleException;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.PropertyAlreadyExistException;
import com.hexaware.lms.repository.LoanTypeRepository;
import com.hexaware.lms.service.ICustomerService;
import com.hexaware.lms.service.ILoanService;
import com.hexaware.lms.service.ILoanTypeService;

import jakarta.validation.Valid;

@CrossOrigin("http://fsd-final-project-angular.s3-website-us-east-1.amazonaws.com")
@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasAuthority('USER')")
public class CustomerRestController {

	Logger log = LoggerFactory.getLogger(CustomerRestController.class);

	@Autowired
	private ILoanService loanService;

	@Autowired
	private ILoanTypeService loanTypeService;

	@Autowired
	private ICustomerService custService;

	@Autowired
	private LoanTypeRepository loanTypeRepo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping(value = "/loan-application/applyLoan", consumes = "multipart/form-data")
	public boolean applyLoan(@RequestPart("loanRequest") @Valid String loanRequest,
			@RequestPart("file") MultipartFile file)
			throws PropertyAlreadyExistException, IOException, CustomerNotEligibleException {
		LoanApplicationRequestDTO requestDTO = LoanApplicationRequestDTOMapper.mapFromString(loanRequest);
		LoanApplication loanApplication = loanService.applyLoan(requestDTO.getLoanApplicationDto(),
				requestDTO.getPropertyDto(), file);
		return loanApplication !=null;
	}
	
	@PostMapping(value="/update-loan", consumes="multipart/form-data")
	public boolean updateLoan(@RequestPart("loanRequest") @Valid String loanRequest,
			@RequestPart("file") MultipartFile file) throws PropertyAlreadyExistException, IOException {
		log.info("sending request for update loan application");
		LoanApplicationRequestDTO requestDTO = LoanApplicationRequestDTOMapper.mapFromString(loanRequest);
		LoanApplication loanApplication = loanService.updateLoan(requestDTO.getLoanApplicationDto(),
				requestDTO.getPropertyDto(), file);
		return loanApplication!=null;
	}
	
	@GetMapping("/cancel-applied-loan/{loanId}")
	public void cancelAppliedLoan(@PathVariable  long loanId) {
		log.info(""+loanId);
		loanService.cancelLoanApplication(loanId);
	}

	@GetMapping("/dashboard")
	public List<LoanType> viewAllAvailableLoans() {
		log.info("Customer is logged In");
		return loanTypeService.viewAvailableLoanType();
	}

	@GetMapping("/searchLoanById/{customerId}/{loanId}")
	public LoanApplication searchLoanById(@PathVariable long customerId, @PathVariable long loanId)
			throws LoanNotFoundException {
		log.info("Request Received to search loan of Customer: " + customerId);
		return loanService.searchAppliedLoan(customerId, loanId);
	}

	@GetMapping("/viewAllAppliedLoans/{customerId}")
	public List<LoanApplication> viewAllAppliedLoans(@PathVariable long customerId) {
		log.info("Request Received to view all loans of Customer: " + customerId);
		return loanService.allAppliedLoansOfCustomer(customerId);
	}

	@GetMapping("/viewAllAppliedLoansByStatus/{status}/{customerId}")
	public List<LoanApplication> filterAppliedLoanByStatus(@PathVariable long customerId, @PathVariable String status)
			throws LoanNotFoundException {
		log.info("Request Received to view loans by Status: " + status);
		return loanService.filterAppliedLoanByStatus(customerId, status);
	}

	@GetMapping("/viewAllAppliedLoansByType/{loanType}/{customerId}")
	public List<LoanApplication> filterAppliedLoanByType(@PathVariable long customerId, @PathVariable String loanType)
			throws LoanNotFoundException {
		log.info("Request Received to view loan by loanType: " + loanType);
		return loanService.filterAppliedLoanByType(customerId, loanType);
	}

	@GetMapping("/calculateInterest/{loanId}/{customerId}")
	public double calculateInterest(@PathVariable(name = "loanId") long loanId,
			@PathVariable(name = "customerId") long customerId) {
		log.info("Calculating Interest of loanId: " + loanId + " for customer: " + customerId);
		return loanService.interestCalculator(loanId, customerId);
	}

	@GetMapping("/calculateEMI/{loanId}/{customerId}")
	public double calculateEMI(@PathVariable(name = "loanId") long loanId,
			@PathVariable(name = "customerId") long customerId) {
		log.info("Calculating Interest of loanId: " + loanId + " for customer: " + customerId);
		return loanService.emiCalculator(loanId, customerId);
	}
	@GetMapping("/calculateEMI/{loanAmount}/{loanDuration}/{loanType}")
	public double calculateEMI(@PathVariable double loanAmount, @PathVariable int loanDuration,
			@PathVariable String loanType) {
		log.info(loanAmount + "  " + loanDuration + "    " + loanType);
		LoanType loanObj = loanTypeRepo.findAllByLoanTypeName(loanType);
		double interest = loanObj.getLoanInterestBaseRate();
		double emi = loanService.emiCalculator(loanAmount, interest, loanDuration);
		log.info("calculated emi");
		return emi;
	}

	@GetMapping("/dashboard/{loanType}")
	public LoanType filterDashboardLoans(@PathVariable String loanType) throws LoanNotFoundException {
		log.info("Request Received filter DashBoard Loans by type");
		return loanTypeService.searchDashboardLoansToApply(loanType);
	}

	@PutMapping("/updateAccount")
	public Customer updateAccountDetails(@RequestBody CustomerDTO customerDto) throws CustomerNotFoundException {
		Customer customer = custService.viewCustomerDetailsById(customerDto.getCustomerId());
		customer.setAddress(customerDto.getAddress());
		customer.setCreditScore(customerDto.getCreditScore());
		customer.setCustomerFirstName(customerDto.getCustomerFirstName());
		customer.setCustomerLastName(customerDto.getCustomerLastName());
		customer.setDateOfBirth(customer.getDateOfBirth());
		LocalDate currentDate = LocalDate.now();
		Period period = Period.between(customer.getDateOfBirth(), currentDate);
		int age = period.getYears();
		customer.setAge(age);
		customer.setEmail(customerDto.getEmail());
		customer.setGender(customerDto.getGender());
		customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
		customer.setPhoneNumber(customerDto.getPhoneNumber());
		customer.setState(customerDto.getState());
		return custService.updateCustomerAccount(customer);
	}

	@ExceptionHandler({ PropertyAlreadyExistException.class })
	public ResponseEntity<String> handlePropertyException(PropertyAlreadyExistException e) {
		log.warn("Some Exception has occurred");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ CustomerNotEligibleException.class })
	public ResponseEntity<String> handleEligibilityException(CustomerNotEligibleException e) {
		log.warn("Customer is not eligible to apply for a loan but he is eager to.");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
	}

}
