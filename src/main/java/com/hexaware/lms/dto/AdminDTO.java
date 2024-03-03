package com.hexaware.lms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdminDTO {
	
	private long adminId;
	
	@NotBlank(message = "First name cannot be blank")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
    private String adminFirstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters")
    private String adminLastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

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
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
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

}
