package com.hexaware.lms.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.entities.PropertyProof;

public interface IUploadPropertyService {

	PropertyProof uploadPdf(MultipartFile file) throws IOException;

	byte[] downloadImageBytes(long propertyProofId);
}
