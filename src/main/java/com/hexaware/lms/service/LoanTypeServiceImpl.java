package com.hexaware.lms.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hexaware.lms.dto.LoanTypeDTO;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.LoanTypeAlreadyExistException;
import com.hexaware.lms.repository.LoanTypeRepository;

@Service
public class LoanTypeServiceImpl implements ILoanTypeService {
	
	Logger logger = LoggerFactory.getLogger(LoanTypeServiceImpl.class);

	@Autowired
	LoanTypeRepository repo;
	
	@Override
	public List<LoanType> viewAvailableLoanType() {
		logger.info("Viewing all available Loan Types");
		return repo.findAll();
	}

	@Override
	public void createLoanType(LoanTypeDTO loanTypeDto) throws LoanTypeAlreadyExistException {
		List<LoanType> loanTypes =viewAvailableLoanType();
		for(LoanType loan : loanTypes) {
			if(loan.getLoanTypeName().equalsIgnoreCase(loanTypeDto.getLoanTypeName())) {
				throw new LoanTypeAlreadyExistException("The loan-type with name "+loanTypeDto.getLoanTypeName()+" already exist");
			}
		}
		LoanType loanType = new LoanType();
		loanType.setLoanTypeName(loanTypeDto.getLoanTypeName());
		loanType.setLoanInterestBaseRate(loanTypeDto.getLoanInterestBaseRate());
		loanType.setLoanManagementFees(loanTypeDto.getLoanManagementFees());
		logger.info("Creating Loan Type: "+loanType);
		repo.save(loanType);
	}
	
	@Override
	public List<LoanType> searchDashboardLoansToApply(String loanType) throws LoanNotFoundException {
		List<LoanType> loanTypes =viewAvailableLoanType();
		boolean isFound=false;
		for(LoanType loan : loanTypes) {
			if(loan.getLoanTypeName().equalsIgnoreCase(loanType)) {
				throw new LoanNotFoundException("The loan-type with name "+loanType+" not exist");
			}
		}
		logger.info("Customer is Searching for Loan Type: "+loanType);
		return repo.findAllByLoanTypeName(loanType);
	}

	@Override
	public LoanType updateLoanTypeById(LoanType loanType) {
		logger.info("Updating LoanType: "+loanType);
		return repo.save(loanType);
	}

	@Override
	public LoanType getLoanTypeById(long loanTypeId) {
		logger.info("Viewing Loan Type: "+loanTypeId);
		return repo.findById(loanTypeId).orElse(null);
	}
	
	@ExceptionHandler({LoanTypeAlreadyExistException.class})
	public ResponseEntity<String> handleException(LoanTypeAlreadyExistException e){
		logger.warn("Some Exception has Occurred");
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}

}
