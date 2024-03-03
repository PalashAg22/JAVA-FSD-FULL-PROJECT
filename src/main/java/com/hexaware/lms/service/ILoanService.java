package com.hexaware.lms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.LoanApplicationDTO;
import com.hexaware.lms.dto.PropertyDTO;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.exception.CustomerNotEligibleException;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.PropertyAlreadyExistException;

public interface ILoanService {

	LoanApplication applyLoan(LoanApplicationDTO loan, PropertyDTO propertyDto, MultipartFile file) throws PropertyAlreadyExistException, IOException, CustomerNotEligibleException;

	List<LoanApplication> filterAppliedLoanByType(long customerId, String loanType) throws LoanNotFoundException; 

	List<LoanApplication> filterAppliedLoanByStatus(long customerId, String status) throws LoanNotFoundException; 

	void customerUpdateLoanStatus(long loanId, String status);

	List<LoanApplication> allAppliedLoansOfCustomer(long customerId); 

	List<LoanApplication> allAppliedLoansOfCustomerForAdmin(); 

	double interestCalculator(long loanId,long customerId);

	double emiCalculator(double principal, double rate, int tenure);

	LoanApplication searchAppliedLoan(long customerId, long loanId) throws LoanNotFoundException;

	LoanApplication searchLoanById(long loanId) throws LoanNotFoundException;

	double emiCalculator(long loanId, long customerId);
	
	LoanApplication updateLoan(LoanApplicationDTO loan, PropertyDTO propertyDto, MultipartFile file) throws PropertyAlreadyExistException, IOException;

	void cancelLoanApplication(long loanId);

}
