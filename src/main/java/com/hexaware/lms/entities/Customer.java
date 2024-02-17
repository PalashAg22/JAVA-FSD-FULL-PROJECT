package com.hexaware.lms.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Customer {
	@Id
	@SequenceGenerator(name = "customer_sequence", initialValue = 1001)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")																					// value for id field in the																			
	private long customerId;

	@Column(name = "firstName")
	private String customerFirstName;

	@Column(name = "lastName")
	private String customerLastName;

	private long phoneNumer;

	private String email;

	private String password;

	@Column(name = "dob")
	private LocalDate dateOfBirth;

	private String address;

	private String country = "India";

	private String state;

	private int creditScore;

	private String panCardNumber;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customer_proof_id")
	private CustomerProof idProofFile;

	private String role = "USER";

	public long getCustomerId() {
		return customerId;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public CustomerProof getIdProofFile() {
		return idProofFile;
	}

	public void setIdProofFile(CustomerProof idProofFile) {
		this.idProofFile = idProofFile;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerFirstName=" + customerFirstName + ", customerLastName="
				+ customerLastName + ", phoneNumer=" + phoneNumer + ", email=" + email + ", password=" + password
				+ ", dateOfBirth=" + dateOfBirth + ", address=" + address + ", country=" + country + ", state=" + state
				+ ", creditScore=" + creditScore + ", panCardNumber=" + panCardNumber +", role=" + role + "]";
	}

}
