package com.hexaware.lms.dto;

public class LoanApplicationRequestDTO {
	private LoanApplicationDTO loanApplicationDto;
    private PropertyDTO propertyDto;
	public LoanApplicationRequestDTO() {
		super();
	}
	public LoanApplicationRequestDTO(LoanApplicationDTO loanApplicationDto, PropertyDTO propertyDto) {
		super();
		this.loanApplicationDto = loanApplicationDto;
		this.propertyDto = propertyDto;
	}
	public LoanApplicationDTO getLoanApplicationDto() {
		return loanApplicationDto;
	}
	public void setLoanApplicationDto(LoanApplicationDTO loanApplicationDto) {
		this.loanApplicationDto = loanApplicationDto;
	}
	public PropertyDTO getPropertyDto() {
		return propertyDto;
	}
	public void setPropertyDto(PropertyDTO propertyDto) {
		this.propertyDto = propertyDto;
	}
    
}
