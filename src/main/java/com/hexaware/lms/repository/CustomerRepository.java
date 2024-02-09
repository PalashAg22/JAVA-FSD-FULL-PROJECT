package com.hexaware.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.lms.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

	@Query("select c from Customer c where c.phoneNumer = ?1")
	Customer findByPhoneNumber(long phoneNumber);

	@Query("select c from Customer c where c.email =?1")	
	Optional<Customer> findByEmail(String email);

	Optional<Customer> findByName(String username);
	
}
