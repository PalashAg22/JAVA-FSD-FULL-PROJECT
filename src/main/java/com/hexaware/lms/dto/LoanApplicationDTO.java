package com.hexaware.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class LoanApplicationDTO {
	private long loanApplicationId;
	
	@Min(5000)
	@Max(10000000)
	private double principal;

	@Min(6)
	@Max(72)
	private int tenureInMonths;
	
	@NotBlank(message = "Loan type name cannot be blank")
	private String loanTypeName;

	@Positive(message = "Loan type ID must be a positive number")
	private long loanTypeId;

	@Positive(message = "Property ID must be a positive number")
	private long propertyId;

	@Positive(message = "Customer ID must be a positive number")
	private long customerId;

	public LoanApplicationDTO() {
		super();
	}

	public LoanApplicationDTO(double principal, int tenureInMonths,int loanTypeId,int propertyId,
			int customerId) {
		super();
		this.principal = principal;
		this.tenureInMonths = tenureInMonths;
		this.loanTypeId = loanTypeId;
		this.propertyId = propertyId;
		this.customerId = customerId;
	}
	
	

	public long getLoanApplicationId() {
		return loanApplicationId;
	}

	public void setLoanApplicationId(long loanApplicationId) {
		this.loanApplicationId = loanApplicationId;
	}

	public double getPrincipal() {
		return principal;
	}
	

	public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public int getTenureInMonths() {
		return tenureInMonths;
	}

	public void setTenureInMonths(int tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}

	public long getLoanTypeId() {
		return loanTypeId;
	}

	public void setLoanTypeId(long loanTypeId) {
		this.loanTypeId = loanTypeId;
	}

	public long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long l) {
		this.customerId = l;
	}

}
