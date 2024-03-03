package com.hexaware.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class LoanTypeDTO {
	@Size(min = 6, message = "Loan type name must be at least 6 characters")
	private String loanTypeName;

	@Min(value = 8, message = "Interest base rate must be at least 8")
	@Max(value = 30, message = "Interest base rate cannot be greater than 30")
	private double loanInterestBaseRate;

	@Min(value = 1000, message = "Management fees must be at least 1000")
	@Max(value = 20000, message = "Management fees cannot be greater than 20000")
	private double loanManagementFees;
	
	public LoanTypeDTO() {
		super();
	}

	public LoanTypeDTO(String loanTypeName, double loanInterestBaseRate, double loanManagementFees) {
		super();
		this.loanTypeName = loanTypeName;
		this.loanInterestBaseRate = loanInterestBaseRate;
		this.loanManagementFees = loanManagementFees;
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

	@Override
	public String toString() {
		return "LoanTypeDTO [loanTypeName=" + loanTypeName + ", loanInterestBaseRate=" + loanInterestBaseRate
				+ ", loanManagementFees=" + loanManagementFees + "]";
	}

	
}
