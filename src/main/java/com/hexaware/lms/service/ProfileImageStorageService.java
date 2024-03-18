package com.hexaware.lms.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.repository.AdminRepository;
import com.hexaware.lms.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfileImageStorageService {

	@Autowired
	CustomerRepository custRepo;

	@Autowired
	AdminRepository adminRepo;

	Logger log = LoggerFactory.getLogger(ProfileImageStorageService.class);

	public String uploadFile(MultipartFile file, long userId) {
		Customer customer = null;
		Admin admin = null;
		customer = custRepo.findById(userId).orElse(customer);
		admin = adminRepo.findById(userId).orElse(admin);
		byte[] fileData = null;
		try {
			fileData = file.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (customer != null) {
			custRepo.updateCustomerProfilePic(file.getOriginalFilename(), fileData, userId);
		}
		if (admin != null) {
			adminRepo.updateAdminProfilePic(file.getOriginalFilename(), fileData, userId);
		}
		return "File uploaded : " + file.getOriginalFilename();
	}

	public String deleteFile(String fileName, long userId) {
		Customer customer = custRepo.findById(userId).orElse(null);
		Admin admin = adminRepo.findById(userId).orElse(null);
		if (customer != null) {
			custRepo.updateCustomerProfilePic("null", null, userId);
		}
		if (admin != null) {
			adminRepo.updateAdminProfilePic("null", null, userId);
		}
		return "File deleted successfully";
	}
}
