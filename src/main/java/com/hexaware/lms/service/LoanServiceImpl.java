package com.hexaware.lms.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.lms.dto.LoanApplicationDTO;
import com.hexaware.lms.dto.PropertyDTO;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.entities.Property;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.PropertyAlreadyExistException;
import com.hexaware.lms.repository.CustomerRepository;
import com.hexaware.lms.repository.LoanRepository;
import com.hexaware.lms.repository.LoanTypeRepository;
import com.hexaware.lms.repository.PropertyRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoanServiceImpl implements ILoanService{

	Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
	
	private final PropertyRepository propertyRepo;

	public LoanServiceImpl(PropertyRepository propertyRepo) {
        this.propertyRepo = propertyRepo;
    }
	
	@Autowired
	LoanTypeRepository loanTypeRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	LoanRepository loanRepo;
	
	@Autowired
	PropertyRepository propRepo;
	
	@Override
	public LoanApplication applyLoan(LoanApplicationDTO loanDto,PropertyDTO propertyDto) throws PropertyAlreadyExistException {
		long loanTypeId=loanDto.getLoanTypeId();
		long customerId =loanDto.getCustomerId();
		
		LoanType loanType = loanTypeRepo.findById(loanTypeId).orElse(null);
		double interestRate = loanTypeRepo.findLoanInterestBaseRateByLoanId(loanDto.getLoanTypeId());
		Customer customer = customerRepo.findById(customerId).orElse(null);
		LocalDate loanApplicationDate = LocalDate.now();
		
		LoanApplication loan = new LoanApplication();
		loan.setCustomer(customer);
		loan.setLoanType(loanType);
		loan.setPrincipal(loanDto.getPrincipal());
		loan.setTenureInMonths(loanDto.getTenureInMonths());
		loan.setLoanApplyDate(loanApplicationDate);
		loan.setInterestRate(interestRate);
		
		logger.info("Loan application started...");
		String propertyDtoAddress = propertyDto.getPropertyAddress();
		double propertyDtoArea = propertyDto.getPropertyAreaInm2();
		
		Property tempProperty =propRepo.getProperty(propertyDtoAddress,propertyDtoArea);
		logger.info("Property details read...");
		if(tempProperty!=null) {
			logger.warn("User is entering duplicate property details");
			throw new PropertyAlreadyExistException("Property details already taken.");
		}
		Property property = new Property();
		property.setPropertyAddress(propertyDto.getPropertyAddress());
		property.setPropertyAreaInm2(propertyDto.getPropertyAreaInm2());
		property.setPropertyValue(propertyDto.getPropertyValue());
		property.setPropertyProof(propertyDto.getPropertyProof());
		logger.info("Property details saved...");
		loan.setProperty(property);
		logger.info("loanApplication submitted successfully");
		return loanRepo.save(loan);
	}


	@Override
	public double interestCalculator(long customerId) {
		LoanApplication loan= loanRepo.propertiesToCalculate(customerId);
		logger.info("Interest is being calculated for the customer: "+customerId);
		return (loan.getPrincipal()*loan.getInterestRate()*loan.getTenureInMonths())/12;
	}

	@Override
	public double emiCalculator(double principal, double rate, int tenure) {
		return (principal*rate*Math.pow((1+rate),tenure))/(Math.pow((1+rate),(tenure-1)));
	}
	@Override
	public double emiCalculator(long customerId) {
		LoanApplication loan= loanRepo.propertiesToCalculate(customerId);
		double p = loan.getPrincipal();
		double r = loan.getInterestRate();
		double t = loan.getTenureInMonths();
		
		double emi=(p*r*Math.pow((1+r),t))/(Math.pow((1+r),(t-1)));
		logger.info("EMI for the laon is calculated as: "+emi);
		return emi;
	}

	@Override
	public List<LoanApplication> filterAppliedLoanByType(long customerId,String loanType) throws LoanNotFoundException {
		isLoanTypePresent(customerId, loanType);
		logger.info("Loan Application with customerId "+customerId+" and loanType "+loanType+" is present");
		return loanRepo.filterAppliedLoanByType(customerId,loanType);
	}

	@Override
	public List<LoanApplication> filterAppliedLoanByStatus(long customerId,String status) throws LoanNotFoundException {
		isLoanStatusPresent(customerId, status);
		logger.info("Loan Application with customerId "+customerId+" and status "+status+" is present");
		return loanRepo.filterAppliedLoanByStatus(customerId, status);
	}

	@Override
	public LoanApplication searchAppliedLoan(long customerId,long loanId) throws LoanNotFoundException {
		isLoanIdPresent(customerId, loanId);
		logger.info("Loan Application with customerId "+customerId+" and loanId "+loanId+" is present");
		return loanRepo.findByLoanId(customerId,loanId);
	}

	@Override
	public List<LoanApplication> allAppliedLoansOfCustomer(long customerId) {
		logger.info("Customer is viewing all their loan applications...");
		return loanRepo.findAllByCustomerCustomerId(customerId);
	}
	
	@Override
	public List<LoanApplication> allAppliedLoansOfCustomerForAdmin() {
		logger.info("Admin is viewing all the latest loan applicaions on the portal...");
		return loanRepo.findAll();
	}
	
	@Override
	public void customerUpdateLoanStatus(long loanId, String status) {
		logger.info("Admin is updating the loan application: "+loanId);
		loanRepo.updateLoanStatus(status, loanId);
	}


	@Override
	public LoanApplication searchLoanById(long loanId) throws LoanNotFoundException {
		LoanApplication loan = isLoanIdPresent(loanId);
		logger.info("Loan found with id "+loanId);
		return loan;
	}
	
	
	public boolean isLoanTypeValid(String loanType) {
		loanType = loanType.strip();
		logger.info("validating entered loanType");
		if((loanType != null && loanType.length() > 0) && (loanType.equalsIgnoreCase("Home Loan") || loanType.equalsIgnoreCase("Gold Loan") || loanType.equalsIgnoreCase("Business Loan") || 
				loanType.equalsIgnoreCase("Vehicle Loan") || loanType.equalsIgnoreCase("Personal Loan"))) {
			logger.info("entered loanType is correct");
			return true;
		}
		return false;
	}
	
	public boolean isLoanTypePresent(long customerId, String loanType) throws LoanNotFoundException {
		//if(isLoanTypeValid(loanType))
		List<LoanApplication> list = loanRepo.findAllByCustomerCustomerId(customerId);
		boolean isPresent = false;
		logger.info("searching for loanType");
		for(LoanApplication lp : list) {
			if(lp.getLoanType().equals(loanType)) {
				logger.info("loanTtype found");
				isPresent = true;
				break;
			}
		}
		if(!isPresent) {
			logger.warn("No loan of type "+loanType+" found for that user");
			throw new LoanNotFoundException("You have not applied for any loan of type"+loanType);
		}
		return isPresent;
	}
	
	
	public boolean isLoanIdValid(long loanId) {
		logger.info("validating entered loanId");
		if(loanId >= 2001 && loanId <= 5000) {
			logger.info("entered loanId is correct");
			return true;
		}
		return false;
	}
	
	public boolean isLoanIdPresent(long customerId,long loanId) throws LoanNotFoundException {
		List<LoanApplication> loans =  loanRepo.findAllByCustomerCustomerId(customerId);
		boolean isPresent=false;
		for(LoanApplication lp:loans) {
			if(lp.getLoanId()==loanId) {
				logger.info("Loan is present for the customer...");
				isPresent=true;
				break;
			}
		}
		if(!isPresent) {
			logger.warn("Customer has input wrong loan number "+ loanId +" to search for...");
			throw new LoanNotFoundException("No Loan found with that loan number "+loanId);
		}
		return isPresent;
	}
	
	public boolean isLoanStatusValid(String status) {
		status = status.strip();
		logger.info("validating entered loan application status");
		if((status != null && status.length() > 0) && (status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Approved")|| status.equalsIgnoreCase("Rejected"))) {
			logger.info("entered loan application status is correct");
			return true;
		}
		return false;
	}
	
	public boolean isLoanStatusPresent(long customerId, String status) throws LoanNotFoundException {
		List<LoanApplication> loans = loanRepo.findAllByCustomerCustomerId(customerId);
		boolean isPresent=false;
		for(LoanApplication lp:loans) {
			if(lp.getStatus().equals(status)) {
				logger.info("Loan status found...");
				isPresent=true;
				break;
			}
		}
		if(!isPresent) {
			logger.warn("No loan of status " + status +" found for that user");
			throw new LoanNotFoundException("None of your loan is "+status);
		}
		return isPresent;
	}
	
	public LoanApplication isLoanIdPresent(long loanId) throws LoanNotFoundException {
		LoanApplication loan =loanRepo.findById(loanId).orElse(null);
		if(loan==null) {
			logger.warn("No Record Found for loanID "+loanId);
			throw new LoanNotFoundException("No Record Found for loanID "+loanId);
		}
		logger.info("Loan found with id "+loanId);
		return loan;
	}
	
}
