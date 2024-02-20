package com.hexaware.lms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CustomerDTO {

	@Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
	private String customerFirstName;

	@Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters")
	private String customerLastName;


	@Positive(message = "Phone number must be a positive number")
	@Digits(integer = 10, fraction = 0, message = "Phone number must be a 10-digit number")

	private long phoneNumer;

	@Email(message = "Invalid email format")
	private String email;

	@Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	private String password;


	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;


	private String gender;

	@Size(max = 100, message = "Address must be less than or equal to 100 characters")
	private String address;

	@NotBlank(message = "State cannot be blank")
	private String state;

	@Min(value = 500, message = "Credit score must be at least 500")
	@Max(value = 900, message = "Credit score cannot be more than 900")
	private int creditScore;

	@Pattern(regexp = "[A-Z]{5}\\d{4}[A-Z]")

	private String panCardNumber;


	private byte[] idProof;

	private String role;///?


	public CustomerDTO() {
		super();
	}



	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public long getPhoneNumer() {
		return phoneNumer;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPhoneNumer(long phoneNumer) {
		this.phoneNumer = phoneNumer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public String getPanCardNumber() {
		return panCardNumber;
	}

	public void setPanCardNumber(String panCardNumber) {
		this.panCardNumber = panCardNumber;
	}

	public byte[] getIdProof() {
		return idProof;
	}

	public void setIdProof(byte[] idProof) {
		this.idProof = idProof;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
