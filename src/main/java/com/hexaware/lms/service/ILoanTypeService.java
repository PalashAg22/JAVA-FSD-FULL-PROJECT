package com.hexaware.lms.service;

import java.util.List;

import com.hexaware.lms.dto.LoanTypeDTO;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.LoanTypeAlreadyExistException;

public interface ILoanTypeService {
	List<LoanType> viewAvailableLoanType();
	
	void createLoanType(LoanTypeDTO loanTypeDto) throws LoanTypeAlreadyExistException;

	List<LoanType> searchDashboardLoansToApply(String loanType) throws LoanNotFoundException;
	
	LoanType updateLoanTypeById(LoanType loanType);
	
	LoanType getLoanTypeById(long loanTypeId);
}
