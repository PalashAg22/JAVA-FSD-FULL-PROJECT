package com.hexaware.lms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.repository.AdminRepository;

@Service
public class AdminServiceImpl implements IAdminService {
	
	Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	AdminRepository repo;
	
	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(AdminDTO adminDto) throws DataAlreadyPresentException {
		Admin localAdmin = repo.findByEmail(adminDto.getEmail());
		if(localAdmin!=null) {
			throw new DataAlreadyPresentException("Email Id already present");
		}
		Admin admin = new Admin();
		admin.setAdminFirstName(adminDto.getAdminFirstName());
		admin.setAdminLastName(adminDto.getAdminLastName());
		admin.setEmail(adminDto.getEmail());
		admin.setPassword(adminDto.getPassword());
		admin.setRole("Admin");
		Admin savedAdmin = repo.save(admin);
		logger.info("Saved Admin: "+savedAdmin);
		return true;
	}

}
