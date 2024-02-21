package com.hexaware.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


public class LoginDTO {
	@Email
	private String username;
	//@Size(min=5,max=20)
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
