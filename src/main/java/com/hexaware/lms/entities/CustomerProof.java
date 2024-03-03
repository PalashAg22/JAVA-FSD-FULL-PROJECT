package com.hexaware.lms.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="customer_identity")
public class CustomerProof {
	
	@Id
	@SequenceGenerator(name="customerIdentitySequence",initialValue=101)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="customerIdentitySequence")
	private long customerProofId;
	
	private String fileName;
	private String fileType;
	@Lob
	private byte[] mainFile;
	public CustomerProof() {
		super();
	}
	public CustomerProof(String fileName, String fileType, byte[] mainFile) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.mainFile = mainFile;
	}
	
	public long getCustomerProofId() {
		return customerProofId;
	}
	public void setCustomerProofId(long customerProofId) {
		this.customerProofId = customerProofId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public byte[] getMainFile() {
		return mainFile;
	}
	public void setMainFile(byte[] mainFile) {
		this.mainFile = mainFile;
	}



	public static class Builder {
        private long customerProofId;
        private String fileName;
        private String fileType;
        private byte[] mainFile;

        public Builder propertyProofId(long customerProofId) {
            this.customerProofId = customerProofId;
            return this;
        }

        public Builder name(String name) {
            this.fileName = name;
            return this;
        }

        public Builder type(String type) {
            this.fileType = type;
            return this;
        }

        public Builder idProofData(byte[] mainFile) {
            this.mainFile = mainFile;
            return this;
        }

        public CustomerProof build() {
        	CustomerProof customerProof = new CustomerProof();
        	customerProof.customerProofId = this.customerProofId;
        	customerProof.fileName = this.fileName;
        	customerProof.fileType = this.fileType;
        	customerProof.mainFile = this.mainFile;
            return customerProof;
        }
    }

}
