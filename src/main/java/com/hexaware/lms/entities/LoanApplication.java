package com.hexaware.lms.entities;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class LoanApplication {
	@Id
	@SequenceGenerator(name="loan_sequence",initialValue=2001)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="loan_sequence")// Set the initial value(1001) for LId in the database
	private long loanId;

	private double principal;
	
	private double interestRate;
	
	private int tenureInMonths;
	
	private String status="PENDING";
	
	private LocalDate loanApplyDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name="loanTypeId")
	private LoanType loanType;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="propertyInfo_id")
	private PropertyInfo propertyInfo;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;

	public LoanApplication() {
		super();
	}

	public LoanApplication(double principal, int tenureInMonths, LoanType loanType, PropertyInfo propertyInfo,
			Customer customer) {
		super();
		this.principal = principal;
		this.tenureInMonths = tenureInMonths;
		this.loanType = loanType;
		this.propertyInfo = propertyInfo;
		this.customer = customer;
	}

	public long getLoanId() {
		return loanId;
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

	public LocalDate getLoanApplyDate() {
		return loanApplyDate;
	}

	public void setLoanApplyDate(LocalDate loanApplyDate) {
		this.loanApplyDate = loanApplyDate;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public PropertyInfo getPropertyInfo() {
		return propertyInfo;
	}

	public void setPropertyInfo(PropertyInfo propertyInfo) {
		this.propertyInfo = propertyInfo;
	}

	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
