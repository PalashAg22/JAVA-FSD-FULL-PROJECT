package com.hexaware.lms.dto;

import jakarta.validation.constraints.Email;

public class LoginDTO {
	@Email
	private String username;
	private String password;
	public LoginDTO() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
