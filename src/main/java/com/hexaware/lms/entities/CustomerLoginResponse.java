package com.hexaware.lms.entities;

public class CustomerLoginResponse {
	private Customer customer;
	private String jwtToken;
	public CustomerLoginResponse(Customer customer, String jwtToken) {
		super();
		this.customer = customer;
		this.jwtToken = jwtToken;
	}
	public CustomerLoginResponse() {
		super();
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	
}
