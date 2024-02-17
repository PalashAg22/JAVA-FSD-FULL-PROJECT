package com.hexaware.lms.service;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.exception.DataAlreadyPresentException;
import com.hexaware.lms.exception.LoginCredentialsNotFound;

@SpringBootTest
class AdminServiceImplTest {
	
	Logger log = LoggerFactory.getLogger(AdminServiceImplTest.class);
	
	@Autowired
	IAdminService testAdmin;

	@Test
	void testLogin() throws LoginCredentialsNotFound {
		String username="palash@hexaware.com";
		String password="palash123";
		assertNotNull(testAdmin.login(username, password));
	}

	@Test
	void testRegister() throws DataAlreadyPresentException {
		
		AdminDTO adminDto = new AdminDTO();
		adminDto.setAdminFirstName("Palash");
		adminDto.setAdminLastName("Agrawal");
		adminDto.setEmail("Suraj123@hexaware.com");
		adminDto.setPassword("suraj123");
		log.info("Registering a new Admin: "+adminDto);
		assertTrue(testAdmin.register(adminDto));
	}

}
