package com.hexaware.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.dto.LoanTypeDTO;
import com.hexaware.lms.dto.LoginDTO;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.entities.PropertyInfo;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.LoanTypeAlreadyExistException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;
import com.hexaware.lms.service.IAdminService;
import com.hexaware.lms.service.ICustomerService;
import com.hexaware.lms.service.ILoanService;
import com.hexaware.lms.service.ILoanTypeService;
import com.hexaware.lms.service.IPropertyService;
import com.hexaware.lms.service.IUploadPropertyService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

	Logger log = LoggerFactory.getLogger(AdminRestController.class);

	@Autowired
	private IAdminService adminService;

	@Autowired
	private ILoanTypeService loanTypeService;

	@Autowired
	private ILoanService loanService;

	@Autowired
	private ICustomerService custService;

	@Autowired
	private IUploadPropertyService uploadService;
	
	@Autowired
	private IPropertyService propService;


	@PostMapping("/createLoanType")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String createNewLoanType(@RequestBody @Valid LoanTypeDTO loanTypeDto) throws LoanTypeAlreadyExistException {
		log.info("Request received to update loanType: " + loanTypeDto);
		loanTypeService.createLoanType(loanTypeDto);
		return "Loan Type created";
	}

	@PostMapping("/createNewAdmin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean createNewAdmin(@RequestBody @Valid AdminDTO adminDto) throws DataAlreadyPresentException {
		log.info("Request received to create new Admin: " + adminDto);
		return adminService.register(adminDto);
	}

	@PostMapping("/login")
	public String login(@RequestBody @Valid LoginDTO loginDto) throws LoginCredentialsNotFound {
		log.info("Request received to login as admin: " + loginDto.getUsername() + ", Password: "
				+ loginDto.getPassword());
		return adminService.login(loginDto.getUsername(), loginDto.getPassword());
	}

	@PutMapping("/update-customer-loan-status/{loanId}/{status}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void updateLoanStatus(@PathVariable long loanId, @PathVariable String status) {
		log.info("Request received to update Loan Status to '" + status + "' " + "for loanNo: " + loanId);
		loanService.customerUpdateLoanStatus(loanId, status);
	}

	@GetMapping(value = "/viewAllLoans", produces = "application/json")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<LoanApplication> viewAllLoans() {
		log.info("Request received to view All Loans...");
		return loanService.allAppliedLoansOfCustomerForAdmin();
	}

	@GetMapping("/viewAllCustomers")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Customer> getAllCustomers() {
		log.info("Request received to view All Customers...");
		return custService.viewAllCustomers();
	}

	@GetMapping("/viewCustomerDetails/{customerId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Customer getCustomerById(@PathVariable long customerId) throws CustomerNotFoundException {
		log.info("Request received to view customer with Id: " + customerId);
		return custService.viewCustomerDetailsById(customerId);
	}

	@GetMapping("/searchLoanForCustomer/{customerId}/{loanId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public LoanApplication searchLoanForCustomer(@PathVariable long customerId, @PathVariable long loanId)
			throws LoanNotFoundException {
		log.info("Request received to search laon by the loan No. of customer: " + customerId);
		return loanService.searchAppliedLoan(customerId, loanId);
	}

	@GetMapping("/allLoanTypes")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<LoanType> viewAllLoanTypes() {
		log.info("Request received to view All LoanTypes...");
		return loanTypeService.viewAvailableLoanType();
	}

	@GetMapping("/searchLoan/{loanId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public LoanApplication searchLoan(@PathVariable long loanId) throws LoanNotFoundException {
		log.info("Request received to view Loan by loanId");
		return loanService.searchLoanById(loanId);
	}

	@PutMapping("/updateLoanType")
	@PreAuthorize("hasAuthority('ADMIN')")
	public LoanType updateLoanType(@RequestBody @Valid LoanType loanType) {
		log.info("Request received to update LoanType to: " + loanType);
		return loanTypeService.updateLoanTypeById(loanType);
	}
	
	@GetMapping("/viewPropertyForLoan/{loanId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public PropertyInfo viewPropertyForLoan(@PathVariable long loanId) {
		log.info("Viewing Property set for loan application");
		return propService.viewPropertyForLoan(loanId);
	}

	@GetMapping("/property-file/{fileName}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName){
		byte[] imageData=uploadService.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(imageData);
	}

	@ExceptionHandler({ LoanTypeAlreadyExistException.class })
	public ResponseEntity<String> handleLoanTypeRelated(LoanTypeAlreadyExistException e) {
		log.warn("Some Exception has Occurred....See the logs above and below.");

		return new ResponseEntity<>(e.getMessage(), HttpStatus.ALREADY_REPORTED);

	}

	@ExceptionHandler({ CustomerNotFoundException.class })
	public ResponseEntity<String> handleLoanTypeRelated(CustomerNotFoundException e) {
		log.warn("Some Exception has Occurred....See the logs above and below.");

		return new ResponseEntity<>(e.getMessage(), HttpStatus.ALREADY_REPORTED);

	}
}
