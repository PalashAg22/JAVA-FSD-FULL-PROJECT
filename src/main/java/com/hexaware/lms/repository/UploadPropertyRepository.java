package com.hexaware.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.lms.entities.PropertyProof;

public interface UploadPropertyRepository extends JpaRepository<PropertyProof, Long> {
	
	Optional<PropertyProof> findByName(String fileName);
}
