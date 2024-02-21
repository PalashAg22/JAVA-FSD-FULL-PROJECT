package com.hexaware.lms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;
import com.hexaware.lms.repository.AdminRepository;

@Service
public class AdminServiceImpl implements IAdminService {

	Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AdminRepository repo;

	public AdminServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, AdminRepository repo) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.repo = repo;
	}

	public String login(String username, String password) throws LoginCredentialsNotFound {


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
			throw new LoginCredentialsNotFound("Credentials not matched");
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
