package com.hexaware.lms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.service.ProfileImageStorageService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/file")
@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
public class ProfileImageStorageController {

	@Autowired
	private ProfileImageStorageService service;

	@PostMapping("/updateProfileImage/{userId}/{fileName}")
	public ResponseEntity<Map<String, String>> updateProfile(@RequestParam(value = "file") MultipartFile file,
	        @PathVariable String fileName, @PathVariable long userId) {
	    if (fileName.equals("null") || fileName == null) {
	        String uploadedFileName = service.uploadFile(file, userId);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "File uploaded successfully");
	        response.put("fileName", uploadedFileName);
	        return ResponseEntity.ok().body(response);
	    } else {
	        service.deleteFile(fileName, userId);
	        String uploadedFileName = service.uploadFile(file, userId);
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "File uploaded successfully");
	        response.put("fileName", uploadedFileName);
	        return ResponseEntity.ok().body(response);
	    }
	}


	@DeleteMapping("/delete/{userId}/{fileName}")
	public ResponseEntity<Map<String, String>> deleteFile(@PathVariable String fileName, @PathVariable long userId) {
	    String deletedFileName = service.deleteFile(fileName, userId);
	    Map<String, String> response = new HashMap<>();
	    response.put("message", "File deleted successfully");
	    response.put("fileName:", deletedFileName);
	    return ResponseEntity.ok().body(response);
	}

}
