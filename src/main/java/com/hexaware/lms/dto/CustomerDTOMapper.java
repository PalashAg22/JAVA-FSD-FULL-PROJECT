package com.hexaware.lms.dto;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerDTOMapper {
	public static CustomerDTO mapFromString(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(json);
			String customerFirstName = jsonNode.get("customerFirstName").asText();
			String customerLastName = jsonNode.get("customerLastName").asText();
			long phoneNumber = jsonNode.get("phoneNumber").asLong();
			String email = jsonNode.get("email").asText();
			String password = jsonNode.get("password").asText();
			LocalDate dateOfBirth = LocalDate.parse(jsonNode.get("dateOfBirth").asText());
			String gender = jsonNode.get("gender").asText();
			String fullAddress = jsonNode.get("fullAddress").asText();
			String state = jsonNode.get("state").asText();
			int creditScore = jsonNode.get("creditScore").asInt();
			String panCardNumber = jsonNode.get("panCardNumber").asText();

			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setCustomerFirstName(customerFirstName);
			customerDTO.setCustomerLastName(customerLastName);
			customerDTO.setPhoneNumber(phoneNumber);
			customerDTO.setEmail(email);
			customerDTO.setPassword(password);
			customerDTO.setDateOfBirth(dateOfBirth);
			customerDTO.setGender(gender);
			customerDTO.setAddress(fullAddress);
			customerDTO.setState(state);
			customerDTO.setCreditScore(creditScore);
			customerDTO.setPanCardNumber(panCardNumber);

			return customerDTO;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
