package com.hexaware.lms.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.lms.repository.LoanTypeRepository;

import io.jsonwebtoken.io.IOException;

public class LoanApplicationRequestDTOMapper {
	
	@Autowired
	private LoanTypeRepository loanTypeRepo;
	
	static Logger log = LoggerFactory.getLogger(LoanApplicationRequestDTOMapper.class);
	public static LoanApplicationRequestDTO mapFromString(String json) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		log.info("inside custom mapper for conversion of json to object");
		try {
			JsonNode jsonNode = objMapper.readTree(json);
			long customerId = jsonNode.get("customerId").asLong();
			int principal = jsonNode.get("principal").asInt();
			String propertyAddress = jsonNode.get("propertyAddress").asText();
			int propertyAreaInm2 = jsonNode.get("propertyAreaInm2").asInt();
			int propertyValue = jsonNode.get("propertyValue").asInt();
			int tenureInMonths = jsonNode.get("tenureInMonths").asInt();
			String loanTypeName = jsonNode.get("loanTypeName").asText();
			
			int loanId = 0;
			LoanApplicationDTO loanApp = new LoanApplicationDTO();
			loanApp.setPrincipal(principal);
			loanApp.setTenureInMonths(tenureInMonths);
			loanApp.setCustomerId(customerId);
			loanApp.setLoanTypeName(loanTypeName);
			
			if( jsonNode.get("loanId")!=null) {
				log.info("loanId is not null");
				loanId = jsonNode.get("loanId").asInt();
				loanApp.setLoanApplicationId(loanId);
				log.info(" "+loanId);
			}
			PropertyDTO property = new PropertyDTO();
			property.setPropertyAddress(propertyAddress);
			property.setPropertyAreaInm2(propertyAreaInm2);
			property.setPropertyValue(propertyValue);
			
			LoanApplicationRequestDTO requsetDTO = new LoanApplicationRequestDTO(loanApp,property);
			log.info("  "+requsetDTO);
			return requsetDTO;
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}