package com.hexaware.lms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.lms.entities.CustomerProof;

public interface UploadIdentityProofRepository extends JpaRepository<CustomerProof, Long> {
	Optional<CustomerProof> findByFileName(String fileName);
}
