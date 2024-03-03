package com.hexaware.lms.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.CustomerDTO;
import com.hexaware.lms.dto.CustomerDTOMapper;
import com.hexaware.lms.dto.LoginDTO;
import com.hexaware.lms.entities.AdminLoginResponse;
import com.hexaware.lms.entities.CustomerLoginResponse;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;
import com.hexaware.lms.service.IAdminService;
import com.hexaware.lms.service.ICustomerService;
import com.hexaware.lms.service.ILoanService;
import com.hexaware.lms.service.ILoanTypeService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class PublicEndpoints {
	
	@Autowired ICustomerService customerService;
	
	@Autowired ILoanService loanService;
	
	@Autowired ILoanTypeService loanTypeService;
	
	@Autowired IAdminService adminService;
	
	@Autowired ICustomerService custService;
	
	Logger log= LoggerFactory.getLogger(PublicEndpoints.class);

	@PostMapping(value="/customer/register", consumes="multipart/form-data")
	public boolean registerCustomer(@RequestParam("register") String customerDTO,@RequestParam("file") MultipartFile file) 
	    throws DataAlreadyPresentException, IOException {
	    log.info("Request Received to register new Customer: " + customerDTO);
	    CustomerDTO customerDto= CustomerDTOMapper.mapFromString(customerDTO);
	    return customerService.register(customerDto, file);
	}

	@PostMapping("/customer/login")
	public CustomerLoginResponse authenticateAndGetTokenForCustomer(@RequestBody @Valid  LoginDTO loginDto) throws LoginCredentialsNotFound {
		log.info("Request received to login: [Username:" + loginDto.getUsername() + ", Password: "
				+ loginDto.getPassword()+" as "+loginDto.getRole()+" ]");
		if(loginDto.getRole().equals("ADMIN")) {
			throw new LoginCredentialsNotFound("Bad Credentials to login as Customer");
		}
		return custService.login(loginDto.getUsername(), loginDto.getPassword());
	}

	@PostMapping("/admin/login")
	public AdminLoginResponse authenticateAndGetTokenForAdmin(@RequestBody @Valid  LoginDTO loginDto) throws LoginCredentialsNotFound {
		log.info("Request received to login as user: " + loginDto.getUsername() + ", Password: "
				+ loginDto.getPassword()+" as "+loginDto.getRole());
		if(loginDto.getRole().equals("USER")) {
			throw new LoginCredentialsNotFound("Bad Credentials to login as Customer");
		}
		return adminService.login(loginDto.getUsername(), loginDto.getPassword());
	}
	
	@GetMapping("/checkEMI/{principal}/{rate}/{tenure}")
	public double calculateEMI(@PathVariable(name="principal") double principal, @PathVariable(name= "rate") double rate, @PathVariable(name="tenure") int tenure) {
		log.info("Calculating EMI for principa: "+principal+", rate: "+rate+", tenure: "+tenure);
		return loanService.emiCalculator(principal, rate, tenure);
	}

	@GetMapping("/dashboard")
	public List<LoanType> viewAllAvailableLoans() {
		log.info("Customer is logged In");
		return loanTypeService.viewAvailableLoanType();
	}

}
