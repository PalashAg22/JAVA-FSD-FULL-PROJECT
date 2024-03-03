package com.hexaware.lms.entities;

public class AdminLoginResponse {
	private Admin admin;
	private String jwtToken;
	public AdminLoginResponse() {
		super();
	}
	public AdminLoginResponse(Admin admin, String jwtToken) {
		super();
		this.admin = admin;
		this.jwtToken = jwtToken;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String token) {
		this.jwtToken = token;
	}
	
}
