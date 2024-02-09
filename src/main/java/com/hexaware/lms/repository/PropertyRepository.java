package com.hexaware.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.lms.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    
    @Query(value = "SELECT p.* FROM Property p JOIN Loan l ON p.property_id = l.property_id WHERE l.loan_id = :loanId", nativeQuery = true)
    Property findPropertyByLoanId(@Param("loanId") Long loanId);

    @Query("select p from Property p where p.propertyAddress=?1 and p.propertyAreaInm2=?2")
	Property getProperty(String propertyDtoAddress, double propertyDtoArea);
}
