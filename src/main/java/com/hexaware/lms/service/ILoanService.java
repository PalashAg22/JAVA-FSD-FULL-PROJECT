package com.hexaware.lms.service;

import java.util.List;

import com.hexaware.lms.dto.LoanApplicationDTO;
import com.hexaware.lms.dto.PropertyDTO;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.PropertyAlreadyExistException;

public interface ILoanService {
	
	LoanApplication applyLoan(LoanApplicationDTO loan, PropertyDTO propertyDto) throws PropertyAlreadyExistException; //DONE

	List<LoanApplication> filterAppliedLoanByType(long customerId,String loanType) throws LoanNotFoundException; //DONE

	List<LoanApplication> filterAppliedLoanByStatus(long customerId,String status) throws LoanNotFoundException; //DONE
	
	void customerUpdateLoanStatus(long loanId, String status);  //DONE

	List<LoanApplication> allAppliedLoansOfCustomer(long customerId); //DONE

	List<LoanApplication> allAppliedLoansOfCustomerForAdmin(); //DONE

	double interestCalculator(long customerId);

	double emiCalculator(long customerId);

	double emiCalculator(double principal, double rate, int tenure);

	LoanApplication searchAppliedLoan(long customerId, long loanId) throws LoanNotFoundException;
	
	LoanApplication searchLoanById(long loanId) throws LoanNotFoundException;
	
}
