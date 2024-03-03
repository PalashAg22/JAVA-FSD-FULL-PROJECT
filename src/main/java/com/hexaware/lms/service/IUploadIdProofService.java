package com.hexaware.lms.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.entities.CustomerProof;

public interface IUploadIdProofService {

	CustomerProof uploadPdf(MultipartFile file) throws IOException;

	byte[] downloadImageBytes(long idProofId);
}
