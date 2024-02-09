package com.hexaware.lms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Admin{
	@Id
	@SequenceGenerator(name="admin_sequence",initialValue=10001)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="admin_sequence")
	private long AdminId;
	
	@Column(name = "firstName")
	private String adminFirstName;
	
	@Column(name = "lastName")
	private String adminLastName;

	private String email;

	private String password;
	
	@Column(columnDefinition = "varchar(5) default 'Admin'",nullable=false)
	private String role;
	
	public Admin() {
		super();
	}

	public Admin(String adminFirstName, String adminLastName, String email, String password) {
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
		return "Admin [AdminId=" + AdminId + ", adminFirstName=" + adminFirstName + ", adminLastName=" + adminLastName
				+ ", email=" + email + ", password=" + password + ", role=" + role + "]";
	}

	
	
	
	
}
