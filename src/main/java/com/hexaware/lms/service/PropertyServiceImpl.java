package com.hexaware.lms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hexaware.lms.entities.Property;
import com.hexaware.lms.repository.PropertyRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PropertyServiceImpl implements IPropertyService {

	private final PropertyRepository propertyRepo;
	
	Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);

    public PropertyServiceImpl(PropertyRepository propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

	@Override
	public Property viewPropertyForLoan(long loanId) {
		logger.info("Searching for property for loanId: "+loanId);
		return propertyRepo.findPropertyByLoanId(loanId);
	}

}
