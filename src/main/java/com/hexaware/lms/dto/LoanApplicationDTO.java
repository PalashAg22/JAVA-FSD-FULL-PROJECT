package com.hexaware.lms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class LoanApplicationDTO {
	@Min(5000)
	@Max(10000000)
	private double principal;
	
	@Min(6)
	@Max(72)
	private int tenureInMonths;
	
	private long loanTypeId;
	
	private long propertyId;
	
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

	public double getPrincipal() {
		return principal;
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
