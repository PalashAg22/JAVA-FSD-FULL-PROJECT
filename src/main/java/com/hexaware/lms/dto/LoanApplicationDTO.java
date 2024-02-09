package com.hexaware.lms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class LoanApplicationDTO {
	
	private long loanId;

	@Min(5000)
	@Max(10000000)
	private double principal;
	
	private double interestRate;
	
	@Min(6)
	@Max(72)
	private int tenureInMonths;
	
	private String status;
	
	private LocalDate loanApplyDate;
	
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

	public long getLoanId() {
		return loanId;
	}

	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getTenureInMonths() {
		return tenureInMonths;
	}

	public void setTenureInMonths(int tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getLoanApplyDate() {
		return loanApplyDate;
	}

	public void setLoanApplyDate(LocalDate loanApplyDate) {
		this.loanApplyDate = loanApplyDate;
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

	@Override
	public String toString() {
		return "LoanApplicationDTO [loanId=" + loanId + ", principal=" + principal + ", interestRate=" + interestRate
				+ ", tenureInMonths=" + tenureInMonths+ ", status=" + status
				+ ", loanApplyDate=" + loanApplyDate + ", loanTypeId=" + loanTypeId + ", propertyId=" + propertyId
				+ ", customerId=" + customerId + "]";
	}
	
	
}
