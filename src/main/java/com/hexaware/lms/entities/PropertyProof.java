package com.hexaware.lms.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "property_proof")
public class PropertyProof {
	
	@Id
	@SequenceGenerator(name="property_proof_sequence",initialValue=101)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="property_proof_sequence")
	private long propertyProofId;
	private String name;
	private String type;
	@Lob // In MYSQL, the datatype is tinyblob which can hold only 255 bytes file
	private byte[] propertyData;
	
	public PropertyProof(String name, String type, byte[] propertyData) {
		this.name = name;
		this.type = type;
		this.propertyData = propertyData;
	}
	
	public static class Builder {
        private long propertyProofId;
        private String name;
        private String type;
        private byte[] propertyData;

        public Builder propertyProofId(long propertyProofId) {
            this.propertyProofId = propertyProofId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder propertyData(byte[] propertyData) {
            this.propertyData = propertyData;
            return this;
        }

        public PropertyProof build() {
            PropertyProof propertyProof = new PropertyProof();
            propertyProof.propertyProofId = this.propertyProofId;
            propertyProof.name = this.name;
            propertyProof.type = this.type;
            propertyProof.propertyData = this.propertyData;
            return propertyProof;
        }
    }
	public PropertyProof() {
		super();
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
