package com.hexaware.lms.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.PropertyHelper;
import com.hexaware.lms.entities.PropertyProof;
import com.hexaware.lms.repository.UploadPropertyRepository;

@Service
public class UploadPropertyServiceImpl implements IUploadPropertyService {

	@Autowired
	private UploadPropertyRepository uploadRepo;
	
	@Override
	public PropertyProof uploadPdf(MultipartFile file) throws IOException {
		PropertyProof propertyProof = uploadRepo.save(new PropertyProof.Builder()
						.name(file.getOriginalFilename())
						.type(file.getContentType())
						.propertyData(PropertyHelper.compressImage(file.getBytes())).build());
		if(propertyProof!=null) {
			return propertyProof;
		}
		return null;
	}
	
	@Override
	public byte[] downloadImage(String fileName){
        Optional<PropertyProof> dbImageData = uploadRepo.findByName(fileName);
        byte[] images=PropertyHelper.decompressImage(dbImageData.get().getPropertyData());
        return images;
    }
}
