package com.hexaware.lms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class PropertyDTO {

	@Size(min=10)
	private String propertyAddress;

	@Min(10000)
	private double propertyAreaInm2;

	@Min(1000)
	private double propertyValue;
	
	private long propertyProofId;

	public PropertyDTO() {
		super();
	}

	public PropertyDTO(String propertyAddress, double propertyAreaInm2, double propertyValue, int propertyProof) {
		super();
		this.propertyAddress = propertyAddress;
		this.propertyAreaInm2 = propertyAreaInm2;
		this.propertyValue = propertyValue;
		this.propertyProofId = propertyProof;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public double getPropertyAreaInm2() {
		return propertyAreaInm2;
	}

	public void setPropertyAreaInm2(double propertyAreaInm2) {
		this.propertyAreaInm2 = propertyAreaInm2;
	}

	public double getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(double propertyValue) {
		this.propertyValue = propertyValue;
	}

	public long getPropertyProof() {
		return propertyProofId;
	}

	public void setPropertyProof(long propertyProof) {
		this.propertyProofId = propertyProof;
	}
	
}
