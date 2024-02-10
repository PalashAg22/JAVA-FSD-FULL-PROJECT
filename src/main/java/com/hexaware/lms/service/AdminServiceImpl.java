package com.hexaware.lms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.repository.AdminRepository;

@Service
public class AdminServiceImpl implements IAdminService {

	Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	AdminRepository repo;

	@Override
	public String login(String username, String password) {
		String token = null;
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (authentication.isAuthenticated()) {
			token = jwtService.generateToken(username);
			if (token != null) {
				logger.info("Token Generated for Admin: " + token);
			} else {
				logger.warn("Token not generated");
			}
		} else {
			throw new UsernameNotFoundException("Username not found");
		}
		return token;
	}

	@Override
	public boolean register(AdminDTO adminDto) throws DataAlreadyPresentException {
		Admin localAdmin = repo.findByEmail(adminDto.getEmail()).orElse(null);
		if (localAdmin != null) {
			throw new DataAlreadyPresentException("Email Id already present");
		}
		Admin admin = new Admin();
		admin.setAdminFirstName(adminDto.getAdminFirstName());
		admin.setAdminLastName(adminDto.getAdminLastName());
		admin.setEmail(adminDto.getEmail());
		admin.setPassword(new BCryptPasswordEncoder().encode(adminDto.getPassword()));
		Admin savedAdmin = repo.save(admin);
		logger.info("Admin Created: " + savedAdmin);
		return true;
	}

}
