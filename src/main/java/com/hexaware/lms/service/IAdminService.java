package com.hexaware.lms.service;

import java.util.List;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.entities.AdminLoginResponse;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;

public interface IAdminService {

	boolean register(AdminDTO adminDto) throws DataAlreadyPresentException;

	AdminLoginResponse login(String username, String password) throws LoginCredentialsNotFound;

	boolean updateAccount(AdminDTO adminDto);

	List<Admin> getAllAdmins();

}
