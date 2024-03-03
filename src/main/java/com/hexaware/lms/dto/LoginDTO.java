package com.hexaware.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDTO {
	@Email(message = "Invalid email format")
	private String username;

	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	private String password;

	@NotBlank(message = "Role cannot be blank")
	private String role;

	public LoginDTO() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
