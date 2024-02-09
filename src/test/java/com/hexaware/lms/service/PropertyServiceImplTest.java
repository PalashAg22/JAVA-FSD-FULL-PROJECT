package com.hexaware.lms.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.lms.entities.Property;

@SpringBootTest
class PropertyServiceImplTest {
	
	@Autowired
	IPropertyService serviceTest;
		
	Logger logger = LoggerFactory.getLogger(PropertyServiceImplTest.class);

	@Test
	void testViewPropertyForLoan() {
		long loanID=2001;
		logger.info("Test running for viewing property of laon: "+loanID);
		Property property =serviceTest.viewPropertyForLoan(loanID);
		logger.info("Property found Id: "+property.getPropertyId());
		assertNotNull(property);
	}

}
