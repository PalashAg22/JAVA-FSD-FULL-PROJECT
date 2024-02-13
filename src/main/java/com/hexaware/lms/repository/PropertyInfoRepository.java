package com.hexaware.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.lms.entities.PropertyInfo;

public interface PropertyInfoRepository extends JpaRepository<PropertyInfo, Long> {
    
    @Query(value = "SELECT p.* FROM PropertyInfo p JOIN LoanApplication l ON p.property_id = l.property_id WHERE l.loan_id = :loanId", nativeQuery = true)
    PropertyInfo findPropertyByLoanId(@Param("loanId") Long loanId);

    @Query("select p from PropertyInfo p where p.propertyAddress=?1 and p.propertyAreaInm2=?2")
	PropertyInfo getProperty(String propertyDtoAddress, double propertyDtoArea);
    
    
}
