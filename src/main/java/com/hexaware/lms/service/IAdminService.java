package com.hexaware.lms.service;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;

public interface IAdminService {
	String login(String username, String password) throws LoginCredentialsNotFound;

	boolean register(AdminDTO adminDto) throws DataAlreadyPresentException;

}
