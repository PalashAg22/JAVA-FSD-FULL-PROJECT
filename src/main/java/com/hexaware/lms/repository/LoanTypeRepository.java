package com.hexaware.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexaware.lms.entities.LoanType;

public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {

	@Query("select ltp.loanInterestBaseRate from LoanType ltp where loanTypeId =?1")
	double findLoanInterestBaseRateByLoanId(long loanTypeId);
	
	List<LoanType> findAllByLoanTypeName(String loanTypeName);
}
