package com.hexaware.lms.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.UploadHelper;
import com.hexaware.lms.entities.PropertyProof;
import com.hexaware.lms.repository.UploadPropertyRepository;

@Service
public class UploadPropertyServiceImpl implements IUploadPropertyService {

	@Autowired
	private UploadPropertyRepository uploadRepo;

	@Override
	public PropertyProof uploadPdf(MultipartFile file) throws IOException {
		return uploadRepo.save(new PropertyProof.Builder().name(file.getOriginalFilename())
				.type(file.getContentType()).propertyData(UploadHelper.compressImage(file.getBytes())).build());
	}

	@Override
	public byte[] downloadImageBytes(long propertyProofId) {
		Optional<PropertyProof> propertyProofFile = uploadRepo.findById(propertyProofId);
		if(propertyProofFile.isPresent()) {
			return UploadHelper.decompressImage(propertyProofFile.get().getPropertyData());
		}
		return new byte[0];
	}

}
