package com.hexaware.lms.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="property_details")
public class PropertyInfo{
	@Id
	@SequenceGenerator(name="property_sequence",initialValue=3001)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="property_sequence")
	private long propertyId;
		
	private String propertyAddress;

	private double propertyAreaInm2;
	
	private double propertyValue;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="proofId")
	private PropertyProof propertyProof;
	
	public PropertyInfo() {
		super();
	}

	public PropertyInfo(String propertyAddress, double propertyAreaInm2, double propertyValue, PropertyProof propertyProof) {
		super();
		this.propertyAddress = propertyAddress;
		this.propertyAreaInm2 = propertyAreaInm2;
		this.propertyValue = propertyValue;
		this.propertyProof = propertyProof;
	}
	
	public long getPropertyId() {
		return propertyId;
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

	public PropertyProof getPropertyProof() {
		return propertyProof;
	}

	public void setPropertyProof(PropertyProof propertyProof) {
		this.propertyProof = propertyProof;
	}

	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}	
}
