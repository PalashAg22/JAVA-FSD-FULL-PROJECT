package com.hexaware.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class LoanTypeDTO {
	
	@Pattern(regexp="^[A-Z0-9\s-]{1,20}$")
	private String loanTypeName;
	
	@Min(8)
	@Max(30)
	private double loanInterestBaseRate;
	
	@Min(1000)
	@Max(20000)
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
	
}
