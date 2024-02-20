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
		CustomerProof customerProof = uploadIdRepo.save(new CustomerProof.Builder()
						.name(file.getOriginalFilename())
						.type(file.getContentType())
						.idProofData(UploadHelper.compressImage(file.getBytes())).build());
		if(customerProof!=null) {
			return customerProof;
		}
		return null;
	}
	
	@Override
	public byte[] downloadImage(String fileName){
        Optional<CustomerProof> dbImageData = uploadIdRepo.findByFileName(fileName);
        byte[] images=UploadHelper.decompressImage(dbImageData.get().getMainFile());
        return images;
    }
}
