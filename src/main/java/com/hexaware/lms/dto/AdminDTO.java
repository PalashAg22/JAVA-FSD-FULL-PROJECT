package com.hexaware.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class AdminDTO {
	private long AdminId;

	@Size(min=3,max=20)
	private String adminFirstName;

	@Size(min=3,max=20)
	private String adminLastName;

	@Email
	private String email;

	@Size(min=5,max=20)
	private String password;

	private String role;

	public AdminDTO() {
		super();
	}

	public AdminDTO(String adminFirstName, String adminLastName, String email, String password) {
		super();
		this.adminFirstName = adminFirstName;
		this.adminLastName = adminLastName;
		this.email = email;
		this.password = password;
	}

	public long getAdminId() {
		return AdminId;
	}

	public void setAdminId(long adminId) {
		AdminId = adminId;
	}

	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AdminDTO [AdminId=" + AdminId + ", adminFirstName=" + adminFirstName + ", adminLastName="
				+ adminLastName + ", email=" + email + ", password=" + password + ", role=" + role + "]";
	}
	
	

}
