package com.hexaware.lms.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

	Logger log = LoggerFactory.getLogger(ProfileImageStorageController.class);

	@Autowired
	private ProfileImageStorageService service;

	@PostMapping("/updateProfileImage/{userId}/{fileName}")
	public ResponseEntity<Map<String, String>> updateProfile(@RequestParam(value = "file") MultipartFile file,
			@PathVariable String fileName, @PathVariable long userId) {
		log.info("Request received to upload file: " + fileName);
		String uploadedFileName = service.uploadFile(file, userId);
		Map<String, String> response = new HashMap<>();
		response.put("message", "File uploaded successfully");
		response.put("fileName", uploadedFileName);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/delete/{userId}/{fileName}")
	public ResponseEntity<Map<String, String>> deleteFile(@PathVariable long userId, @PathVariable String fileName) {
		log.info("Request received to delete file: " + fileName);
		try {
			String deletedFileName = service.deleteFile(fileName, userId);
			Map<String, String> response = new HashMap<>();
			response.put("message", "File deleted successfully");
			response.put("fileName", deletedFileName);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			Map<String, String> response = new HashMap<>();
			response.put("error", "Failed to delete file");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}
