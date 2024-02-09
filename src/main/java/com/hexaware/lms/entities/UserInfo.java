package com.hexaware.lms.entities;

public class UserInfo {

	private Admin admin;
	private Customer customer;
	
	public UserInfo() {
		super();
	}
	
	public UserInfo(Admin admin, Customer customer) {
		super();
		this.admin = admin;
		this.customer = customer;
	}

	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
	
}
