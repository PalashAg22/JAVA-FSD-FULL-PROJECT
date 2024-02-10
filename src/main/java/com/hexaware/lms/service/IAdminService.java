package com.hexaware.lms.service;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.exception.DataAlreadyPresentException;

public interface IAdminService {
	String login(String username, String password);

	boolean register(AdminDTO adminDto) throws DataAlreadyPresentException;

}
