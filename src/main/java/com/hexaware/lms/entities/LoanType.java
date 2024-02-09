package com.hexaware.lms.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class LoanType{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long loanTypeId;
	private String loanTypeName;
	@Column(name="interestRate")
	private double loanInterestBaseRate;
	
	private double loanManagementFees;
	
	public LoanType() {
		super();
	}
	public LoanType(String loanTypeName, double loanInterestBaseRate, double loanManagementFees) {
		super();
		this.loanTypeName = loanTypeName;
		this.loanInterestBaseRate = loanInterestBaseRate;
		this.loanManagementFees = loanManagementFees;
	}
	
	
	public long getLoanTypeId() {
		return loanTypeId;
	}
	public void setLoanTypeId(long loanTypeId) {
		this.loanTypeId = loanTypeId;
	}
	public String getLoanTypeName() {
		return loanTypeName;
	}
	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}
	public double getLoanInterestBaseRate() {
		return loanInterestBaseRate;
	}
	public void setLoanInterestBaseRate(double loanInterestBaseRate) {
		this.loanInterestBaseRate = loanInterestBaseRate;
	}
	public double getLoanManagementFees() {
		return loanManagementFees;
	}
	public void setLoanManagementFees(double loanManagementFees) {
		this.loanManagementFees = loanManagementFees;
	}	

}
