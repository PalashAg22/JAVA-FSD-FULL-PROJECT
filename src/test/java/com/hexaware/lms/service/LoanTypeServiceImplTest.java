package com.hexaware.lms.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.lms.dto.LoanTypeDTO;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.exception.LoanTypeAlreadyExistException;

@SpringBootTest
class LoanTypeServiceImplTest {

	Logger log = LoggerFactory.getLogger(AdminServiceImplTest.class);
	
	@Autowired
	ILoanTypeService serviceTest;
	
	@Test
	void testViewAvailableLoans() {
		log.info("Test running for viewing all available loans");
		List<LoanType> loans=serviceTest.viewAvailableLoanType();
		assertNotNull(loans);
	}

	@Test
	void testCreateLoanType() throws LoanTypeAlreadyExistException {
		LoanTypeDTO loanTypeDto = new LoanTypeDTO("HOME LOAN",8.5,200.0);
		log.info("Test running to create a new LoanType: "+loanTypeDto);
		serviceTest.createLoanType(loanTypeDto);
	}
	
	@Test
	void testUpdateLoanType() {
		long checkLoanType=1;
		
		LoanType oldLoanType =serviceTest.getLoanTypeById(checkLoanType);
		log.info("Test running to update LoanType: "+oldLoanType);
		long loanTypeId=oldLoanType.getLoanTypeId();
		String newName="Home Loan";
		double fees = 2500;
		double interestRate=25;
		
		LoanType toUpdateLoanType = new LoanType();
		toUpdateLoanType.setLoanTypeId(loanTypeId);
		toUpdateLoanType.setLoanInterestBaseRate(interestRate);
		toUpdateLoanType.setLoanManagementFees(fees);
		toUpdateLoanType.setLoanTypeName(newName);
		
		LoanType updatedLoanType = serviceTest.updateLoanTypeById(toUpdateLoanType);
		log.info("Updated loan type details: "+updatedLoanType);
		assertNotEquals(oldLoanType.getLoanTypeName(),updatedLoanType.getLoanTypeName());
	}

}
