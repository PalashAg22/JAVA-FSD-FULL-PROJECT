package com.hexaware.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.exception.CustomerNotFoundException;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.LoanTypeAlreadyExistException;
import com.hexaware.lms.service.IAdminService;
import com.hexaware.lms.service.ICustomerService;
import com.hexaware.lms.service.ILoanService;
import com.hexaware.lms.service.ILoanTypeService;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
	
	Logger log = LoggerFactory.getLogger(AdminRestController.class);

	private IAdminService adminService;

	private ILoanTypeService loanTypeService;

	private ILoanService loanService;

	private ICustomerService custService;
	
	public AdminRestController(IAdminService adminService, ILoanTypeService loanTypeService, ILoanService loanService,
			ICustomerService custService) {
		super();
		this.adminService = adminService;
		this.loanTypeService = loanTypeService;
		this.loanService = loanService;
		this.custService = custService;
	}

	@PostMapping("/createLoanType")
	public String createNewLoanType(@RequestBody LoanTypeDTO loanTypeDto) throws LoanTypeAlreadyExistException{
		log.info("Request received to update loanType: "+loanTypeDto);
		loanTypeService.createLoanType(loanTypeDto);
		return "Loan Type created";
	}

	@PostMapping("/createNewAdmin")
	public boolean createNewAdmin(@RequestBody AdminDTO adminDto) throws DataAlreadyPresentException {
		log.info("Request received to create new Admin: "+adminDto);
		return adminService.register(adminDto);
	}

	@PostMapping("/login/{username}/{password}")
	public boolean login(@PathVariable(name = "username") String username,
			@PathVariable(name = "password") String password) {
		log.info("Request received to login as user: "+username+", Password: "+password);
		return adminService.login(username, password);
	}

	@PutMapping("/update-customer-loan-status/{loanId}/{status}")
	public void updateLoanStatus(@PathVariable long loanId, @PathVariable String status) {
		log.info("Request received to update Loan Status to '"+status+"' "+"for loanNo: "+loanId);
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
		log.info("Request received to view customer with Id: "+customerId);
		return custService.viewCustomerDetailsById(customerId);
	}
	
	@GetMapping("/searchLoanForCustomer/{customerId}/{loanId}")
	public LoanApplication searchLoanForCustomer(@PathVariable long customerId,@PathVariable long loanId) throws LoanNotFoundException {
		log.info("Request received to search laon by the loan No. of customer: "+customerId);
		return loanService.searchAppliedLoan(customerId, loanId);
	}
	
	@GetMapping("/allLoanTypes")
	public List<LoanType> viewAllLoanTypes(){
		log.info("Request received to view All LoanTypes...");
		return loanTypeService.viewAvailableLoanType();
	}
	
	@GetMapping("/searchLoan/{loanId}")
	public LoanApplication searchLoan(@PathVariable long loanId) throws LoanNotFoundException {
		log.info("Request received to view Loan by loanId");
		return loanService.searchLoanById(loanId);
	}
	
	@PutMapping("updateLoanType")
	public LoanType updateLoanType(@RequestBody LoanType loanType) {
		log.info("Request received to update LoanType to: "+loanType);
		return loanTypeService.updateLoanTypeById(loanType);
	}
	
	@ExceptionHandler({LoanTypeAlreadyExistException.class})
	public ResponseEntity<String> handleLoanTypeRelated(LoanTypeAlreadyExistException e){
		log.warn("Some Exception has Occurred....See the logs above and below.");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.ALREADY_REPORTED);
	}
	
	@ExceptionHandler({CustomerNotFoundException.class})
	public ResponseEntity<String> handleLoanTypeRelated(CustomerNotFoundException e){
		log.warn("Some Exception has Occurred....See the logs above and below.");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.ALREADY_REPORTED);
	}
}
