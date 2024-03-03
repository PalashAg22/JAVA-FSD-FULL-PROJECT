package com.hexaware.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
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
import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.entities.PropertyInfo;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.LoanTypeAlreadyExistException;
import com.hexaware.lms.service.IAdminService;
import com.hexaware.lms.service.ICustomerService;
import com.hexaware.lms.service.ILoanService;
import com.hexaware.lms.service.ILoanTypeService;
import com.hexaware.lms.service.IPropertyService;
import com.hexaware.lms.service.IUploadPropertyService;
import com.hexaware.lms.service.UploadIdProofService;

import jakarta.validation.Valid;

@CrossOrigin("http://fsd-final-project-angular.s3-website-us-east-1.amazonaws.com")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
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
	
	@Autowired
	UploadIdProofService idProofService;
	
	@GetMapping("/getAllAdmins")
	public List<Admin> getAllAdmins(){
		log.info("Querying database to get all admins.");
		return adminService.getAllAdmins();
	}
	
	@PutMapping("/update-account")
	public boolean updateAccountDetails(@RequestBody @Valid AdminDTO adminDto) {
		log.info("Admin is trying to update his account details.");
		return adminService.updateAccount(adminDto);
	}
	
	@PostMapping("/createLoanType")
	public String createNewLoanType(@RequestBody @Valid LoanTypeDTO loanTypeDto) throws LoanTypeAlreadyExistException {
		log.info("Request received to create a new loanType: " + loanTypeDto);
		loanTypeService.createLoanType(loanTypeDto);
		return "Loan Type created";
	}

	@PostMapping("/createNewAdmin")
	public boolean createNewAdmin(@RequestBody @Valid AdminDTO adminDto) throws DataAlreadyPresentException {
		log.info("Request received to create new Admin: " + adminDto);
		return adminService.register(adminDto);
	}

	@PutMapping("/update-customer-loan-status/{loanId}/{status}")
	public void updateLoanStatus(@PathVariable long loanId, @PathVariable String status) {
		log.info("Request received to update Loan Status to: " + status +" for loanNo: " + loanId);
		loanService.customerUpdateLoanStatus(loanId, status);
	}

	@GetMapping(value = "/viewAllLoans", produces = "application/json")
	public List<LoanApplication> viewAllLoans() {
		log.info("Request received to view All Loans...");
		return loanService.allAppliedLoansOfCustomerForAdmin();
	}

	@GetMapping("/viewAllCustomers")
	public List<Customer> getAllCustomers() {
		log.info("Request received to view All Customers...");
		return custService.viewAllCustomers();
	}

	@GetMapping("/viewCustomerDetails/{customerId}")
	public Customer getCustomerById(@PathVariable long customerId) throws CustomerNotFoundException {
		log.info("Request received to view customer with Id: " + customerId);
		return custService.viewCustomerDetailsById(customerId);
	}

	@GetMapping("/searchLoanForCustomer/{customerId}/{loanId}")
	public LoanApplication searchLoanForCustomer(@PathVariable long customerId, @PathVariable long loanId)
			throws LoanNotFoundException {
		log.info("Request received to search laon by the loan No. of customer: " + customerId);
		return loanService.searchAppliedLoan(customerId, loanId);
	}

	@GetMapping("/allLoanTypes")
	public List<LoanType> viewAllLoanTypes() {
		log.info("Request received to view All LoanTypes...");
		return loanTypeService.viewAvailableLoanType();
	}

	@GetMapping("/searchLoan/{loanId}")
	public LoanApplication searchLoan(@PathVariable long loanId) throws LoanNotFoundException {
		log.info("Request received to view Loan by loanId");
		return loanService.searchLoanById(loanId);
	}

	@PutMapping("/updateLoanType")
	public LoanType updateLoanType(@RequestBody @Valid LoanType loanType) {
		log.info("Request received to update LoanType to: " + loanType);
		return loanTypeService.updateLoanTypeById(loanType);
	}
	
	@GetMapping("/viewPropertyForLoan/{loanId}")
	public PropertyInfo viewPropertyForLoan(@PathVariable long loanId) {
		log.info("Viewing Property set for loan application");
		return propService.viewPropertyForLoan(loanId);
	}

	@GetMapping("/property-file/{propertyProofId}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable Long propertyProofId) {
	    byte[] fileBytes = uploadService.downloadImageBytes(propertyProofId);
	    if (fileBytes != null) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("filename.ext").build());

	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(fileBytes);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/customerIdProof/{customerProofId}")
	public ResponseEntity<byte[]> downloadIdFile(@PathVariable Long customerProofId) {
		log.info("Downloading Customer Uploaded ID Proof");
	    byte[] fileBytes = idProofService.downloadImageBytes(customerProofId);
	    if (fileBytes != null) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("filename.ext").build());

	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(fileBytes);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
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
