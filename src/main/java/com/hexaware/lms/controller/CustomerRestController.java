package com.hexaware.lms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.lms.dto.CustomerDTO;
import com.hexaware.lms.dto.LoanApplicationRequestDTO;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.PropertyAlreadyExistException;
import com.hexaware.lms.service.ICustomerService;
import com.hexaware.lms.service.ILoanService;
import com.hexaware.lms.service.ILoanTypeService;
import com.hexaware.lms.service.IPropertyService;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
	
	Logger log = LoggerFactory.getLogger(CustomerRestController.class);
	
	@Autowired
	ICustomerService customerService;
	
	@Autowired
	IPropertyService propService;
	
	@Autowired
	ILoanService loanService;
	
	@Autowired
	ILoanTypeService loanTypeService;
	
	@PostMapping("/register")
	public boolean registerCustomer(@RequestBody CustomerDTO customerDTO) throws DataAlreadyPresentException {
		log.info("Request Received to register new Customer: "+customerDTO);
		return customerService.register(customerDTO);
	}
	
	@GetMapping("/login/{username}/{password}")
	public boolean login(@PathVariable (name="username") String username,@PathVariable(name="password") String password) {
		log.info("Request Received to login Customer");
		return customerService.login(username,password);
	}
	
	@PostMapping(value="/loan-application/applyLoan",consumes="application/json")
	public LoanApplication applyLoan(@RequestBody LoanApplicationRequestDTO loanRequest) throws PropertyAlreadyExistException {
		return loanService.applyLoan(loanRequest.getLoanApplicationDto(),loanRequest.getPropertyDto());
	}
	
	@GetMapping("/searchLoanById/{customerId}/{loanId}")
	public LoanApplication searchLoanById(@PathVariable long customerId, @PathVariable long loanId) throws LoanNotFoundException {
		log.info("Request Received to search loan of Customer: "+customerId);
		return loanService.searchAppliedLoan(customerId,loanId);
	}
	
	@GetMapping("/viewAllAppliedLoans/{customerId}")
	public List<LoanApplication> viewAllAppliedLoans(@PathVariable long customerId){
		log.info("Request Received to view all loans of Customer: "+customerId);
		return loanService.allAppliedLoansOfCustomer(customerId);
	}
	
	@GetMapping("/viewAllAppliedLoansByStatus/{status}/{customerId}")
	public List<LoanApplication> filterAppliedLoanByStatus(@PathVariable long customerId,@PathVariable String status) throws LoanNotFoundException {
		log.info("Request Received to view loans by Status: "+status);
		return loanService.filterAppliedLoanByStatus(customerId, status);
	}
	@GetMapping("/viewAllAppliedLoansByType/{loanType}/{customerId}")
	public List<LoanApplication> filterAppliedLoanByType(@PathVariable long customerId,@PathVariable String loanType) throws LoanNotFoundException {
		log.info("Request Received to view loan by loanType: "+loanType);
		return loanService.filterAppliedLoanByType(customerId, loanType);
	}
	
	@GetMapping("/dashboard")
	public List<LoanType> viewAllAvailableLoans() {
		log.info("Customer is logged In");
		return loanTypeService.viewAvailableLoanType();
	}
	
	@GetMapping("/dashboard/{loanType}")
	public List<LoanType> filterDashboardLoans(@PathVariable String loanType) throws LoanNotFoundException{
		log.info("Request Received filter DashBoard Loans by type");
		return loanTypeService.searchDashboardLoansToApply(loanType);
	}
	
	@ExceptionHandler({PropertyAlreadyExistException.class})
	public ResponseEntity<String> handlePropertyException(PropertyAlreadyExistException e){
		log.warn("Some Exception has occurred");
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
}
