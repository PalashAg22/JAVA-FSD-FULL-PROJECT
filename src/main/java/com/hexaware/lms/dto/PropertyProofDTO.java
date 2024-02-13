package com.hexaware.lms.dto;

import jakarta.persistence.Lob;

public class PropertyProofDTO {
	private long propertyProofId;
	private String name;
	private String type;
	@Lob
	private byte[] propertyData;
	public PropertyProofDTO() {
		super();
	}
	public PropertyProofDTO(String name, String type, byte[] propertyData) {
		super();
		this.name = name;
		this.type = type;
		this.propertyData = propertyData;
	}
	public long getPropertyProofId() {
		return propertyProofId;
	}
	public void setPropertyProofId(long propertyProofId) {
		this.propertyProofId = propertyProofId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getPropertyData() {
		return propertyData;
	}
	public void setPropertyData(byte[] propertyData) {
		this.propertyData = propertyData;
	}
	
	
}
