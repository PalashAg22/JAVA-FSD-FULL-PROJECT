package com.hexaware.lms.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.UploadHelper;
import com.hexaware.lms.entities.CustomerProof;
import com.hexaware.lms.repository.UploadIdentityProofRepository;

@Service
public class UploadIdProofService implements IUploadIdProofService {

	@Autowired
	private UploadIdentityProofRepository uploadIdRepo;

	@Override
	public CustomerProof uploadPdf(MultipartFile file) throws IOException {
		return uploadIdRepo.save(new CustomerProof.Builder().name(file.getOriginalFilename())
				.type(file.getContentType()).idProofData(UploadHelper.compressImage(file.getBytes())).build());
	}

	@Override
	public byte[] downloadImageBytes(long idProofId) {
		Optional<CustomerProof> customerIdProof = uploadIdRepo.findById(idProofId);
		if(customerIdProof.isPresent()) {
			return UploadHelper.decompressImage(customerIdProof.get().getMainFile());
		}
		return new byte[0];
	}
}
