package com.hexaware.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.Property;

public interface LoanRepository extends JpaRepository<LoanApplication,Long> {
	
	@Query(value="select * from loan_application where customer_id=? and loan_id=?",nativeQuery=true)
	LoanApplication findByLoanId(long customerId,long loanId);
	
	List<LoanApplication> findAllByCustomerCustomerId(long customerId);
	
	@Query("update LoanApplication lp set lp.status=?1 where lp.loanId=?2")
	@Modifying
	void updateLoanStatus(String status,long loanId);

	@Query("select lp from LoanApplication lp where lp.customer.customerId = ?1")
	LoanApplication propertiesToCalculate(long customerId);
	
	@Query("select lp from LoanApplication lp where lp.customer.customerId=?1 and lp.loanType.loanTypeName=?2")
	List<LoanApplication> filterAppliedLoanByType(long custmoerId,String loanTypeName);
	
	@Query("select lp from LoanApplication lp where lp.customer.customerId=?1 and lp.status=?2")
	List<LoanApplication> filterAppliedLoanByStatus(long custmoerId,String status);
	
}
