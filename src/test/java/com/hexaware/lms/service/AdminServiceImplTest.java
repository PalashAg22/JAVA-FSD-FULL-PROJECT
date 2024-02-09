package com.hexaware.lms.service;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.lms.dto.AdminDTO;
import com.hexaware.lms.exception.DataAlreadyPresentException;

@SpringBootTest
class AdminServiceImplTest {
	
	Logger log = LoggerFactory.getLogger(AdminServiceImplTest.class);
	
	@Autowired
	ICustomerService testService;
	
	@Autowired
	IAdminService testAdmin;

	@Test
	void testLogin() {
//		String username="suraj@kumar.com";
//		String password="password123";
//		assertTrue(testService.login(username, password));
	}

	@Test
	void testRegister() throws DataAlreadyPresentException {
		
		AdminDTO adminDto = new AdminDTO();
		adminDto.setAdminFirstName("Palash");
		adminDto.setAdminLastName("Agrawal");
		adminDto.setEmail("palash@agrawal.com");
		adminDto.setPassword("palash123");
		adminDto.setRole("Admin");
		log.info("Registering a new Admin: "+adminDto);
		assertTrue(testAdmin.register(adminDto));
	}

}
