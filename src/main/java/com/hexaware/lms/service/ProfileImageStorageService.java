package com.hexaware.lms.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
	 @Value("${application.bucket.name}")
	    private String bucketName;

	    @Autowired
	    private AmazonS3 s3Client;

	    public String uploadFile(MultipartFile file,long userId) {
	    	Customer customer=null;
	    	Admin admin=null;
			customer = custRepo.findById(userId).orElse(customer);
			admin = adminRepo.findById(userId).orElse(admin);
	        File fileObj = convertMultiPartFileToFile(file);
	        String fileName = userId+ "_" + file.getOriginalFilename();
	        if(customer!=null) {
	    		custRepo.updateCustomerProfilePic(fileName, userId);
	    	}
	        if(admin!=null) {
	    		adminRepo.updateAdminProfilePic(fileName, userId);
	    	}
	        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
	        fileObj.delete();
	        return "File uploaded : " + fileName;
	    }

	    public String deleteFile(String fileName, long userId) {
	        if (fileName != null || !fileName.equals("null")) {
	            s3Client.deleteObject(bucketName, fileName);
	        }
	        Customer customer = custRepo.findById(userId).orElse(null);
	        Admin admin = adminRepo.findById(userId).orElse(null);
	        if (customer != null) {
	            custRepo.updateCustomerProfilePic(null, userId);
	        }
	        if (admin != null) {
	            adminRepo.updateAdminProfilePic(null, userId);
	        }
	        return "File deleted successfully";
	    }



	    private File convertMultiPartFileToFile(MultipartFile file) {
	        File convertedFile = new File(file.getOriginalFilename());
	        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
	            fos.write(file.getBytes());
	        } catch (IOException e) {
	            log.error("Error converting multipartFile to file", e);
	        }
	        return convertedFile;
	    }
}
