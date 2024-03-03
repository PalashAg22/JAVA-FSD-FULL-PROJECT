package com.hexaware.lms.service;

import java.util.List;
import java.util.stream.Stream;

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

	    if (viewAvailableLoanType().stream()
	            .anyMatch(loan -> loan.getLoanTypeName().equalsIgnoreCase(loanTypeDto.getLoanTypeName()))) {
	        throw new LoanTypeAlreadyExistException(
	                "The loan-type with name " + loanTypeDto.getLoanTypeName() + " already exists");
	    }

	    LoanType loanType = new LoanType();
	    loanType.setLoanTypeName(loanTypeDto.getLoanTypeName());
	    loanType.setLoanInterestBaseRate(loanTypeDto.getLoanInterestBaseRate());
	    loanType.setLoanManagementFees(loanTypeDto.getLoanManagementFees());
	    logger.info("Creating Loan Type: " + loanType.toString());
	    repo.save(loanType);
	}


	@Override
	public LoanType searchDashboardLoansToApply(String loanType) throws LoanNotFoundException {

		LoanType returnLoanType = null;
		if (isLoanTypeValid(loanType)) {
			Stream<LoanType> stream = viewAvailableLoanType().stream();
			LoanType isPresent = stream.filter(loan -> loan.getLoanTypeName().equalsIgnoreCase(loanType))
					.findAny().orElse(null);
			if(isPresent == null) {
				throw new LoanNotFoundException("The loan-type with name " + loanType + " not exist");
			}
			
			logger.info("Customer is Searching for Loan Type: " + loanType);
			returnLoanType = isPresent;
		}
		return returnLoanType;

	}

	@Override
	public LoanType updateLoanTypeById(LoanType loanType) {
		logger.info("Updating LoanType: " + loanType);
		return repo.save(loanType);
	}

	@Override
	public LoanType getLoanTypeById(long loanTypeId) {
		if (isLoanTypeIdValid(loanTypeId)) {
			logger.info("Viewing Loan Type: " + loanTypeId);
			return repo.findById(loanTypeId).orElse(null);
		}
		return null;

	}

	public boolean isLoanTypeValid(String loanType) {
		loanType = loanType.strip();
		logger.info("Validating entered loanType");
		if ((loanType != null && loanType.length() > 0) && (loanType.equalsIgnoreCase("Home Loan")
				|| loanType.equalsIgnoreCase("Gold Loan") || loanType.equalsIgnoreCase("Business Loan")
				|| loanType.equalsIgnoreCase("Vehicle Loan") || loanType.equalsIgnoreCase("Personal Loan"))) {
			logger.info("entered loanType is correct");
			return true;
		}
		logger.warn("validations failed for entered loanType");
		return false;
	}

	public boolean isLoanTypeIdValid(long loanTypeId) {
		logger.info("validating entered loanId");
		if (loanTypeId >= 2001 && loanTypeId <= 5000) {
			logger.info("entered loanId is correct");
			return true;
		}
		logger.warn("validations failed for entered loan id");
		return false;
	}

	@ExceptionHandler({ LoanTypeAlreadyExistException.class })
	public ResponseEntity<String> handleException(LoanTypeAlreadyExistException e) {
		logger.warn("Some Exception has Occurred");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

	}

}
