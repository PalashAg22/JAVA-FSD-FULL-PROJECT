package com.hexaware.lms.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.lms.dto.LoanApplicationDTO;
import com.hexaware.lms.dto.PropertyDTO;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.entities.LoanApplication;
import com.hexaware.lms.entities.LoanType;
import com.hexaware.lms.entities.PropertyInfo;
import com.hexaware.lms.entities.PropertyProof;
import com.hexaware.lms.exception.CustomerNotEligibleException;
import com.hexaware.lms.exception.LoanNotFoundException;
import com.hexaware.lms.exception.PropertyAlreadyExistException;
import com.hexaware.lms.repository.CustomerRepository;
import com.hexaware.lms.repository.LoanRepository;
import com.hexaware.lms.repository.LoanTypeRepository;
import com.hexaware.lms.repository.PropertyInfoRepository;
import com.hexaware.lms.repository.UploadPropertyRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoanServiceImpl implements ILoanService {

	Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

	@Autowired

	LoanTypeRepository loanTypeRepo;

	@Autowired
	CustomerRepository customerRepo;


	@Autowired
	private LoanRepository loanRepo;

	@Autowired

	IUploadPropertyService uploadService;
	
	@Autowired
	PropertyInfoRepository propRepo;
	
	@Autowired
	UploadPropertyRepository uploadPropertyRepo;
	
	@Override
	public LoanApplication applyLoan(LoanApplicationDTO loanDto, PropertyDTO propertyDto,MultipartFile file)

			throws PropertyAlreadyExistException, java.io.IOException, CustomerNotEligibleException {

		long customerId = loanDto.getCustomerId();
		LoanType loanType = loanTypeRepo.findAllByLoanTypeName(loanDto.getLoanTypeName());
		double interestRate = loanType.getLoanInterestBaseRate();
		Customer customer = customerRepo.findById(customerId).orElse(null);
		
		int age=customer.getAge();
		int creditScore=customer.getCreditScore();
		if(age<=18 && age>=60) {
			throw new CustomerNotEligibleException("You are not eligible to apply for a loan from our bank.");
		}
		if(creditScore<500) {
			throw new CustomerNotEligibleException("Your Credit score is too low to apply for a new loan");
		}

		LoanApplication loan = new LoanApplication();
		loan.setCustomer(customer);
		loan.setLoanType(loanType);
		loan.setPrincipal(loanDto.getPrincipal());
		loan.setTenureInMonths(loanDto.getTenureInMonths());
		loan.setInterestRate(interestRate);

		logger.info("Loan application started...");
		String propertyDtoAddress = propertyDto.getPropertyAddress();
		double propertyDtoArea = propertyDto.getPropertyAreaInm2();


		PropertyInfo tempProperty = propRepo.getProperty(propertyDtoAddress, propertyDtoArea);

		logger.info("Property details read...");
		if (tempProperty != null) {
			logger.warn("User is entering duplicate property details");
			throw new PropertyAlreadyExistException("Property details already taken.");
		}
		PropertyInfo property = new PropertyInfo();
		property.setPropertyAddress(propertyDto.getPropertyAddress());
		property.setPropertyAreaInm2(propertyDto.getPropertyAreaInm2());
		property.setPropertyValue(propertyDto.getPropertyValue());
		logger.info("Property details saved...");
		
		PropertyProof proof = uploadService.uploadPdf(file);
	    		
		property.setPropertyProof(proof);
		loan.setPropertyInfo(property);
		logger.info("loanApplication submitted successfully");
		return loanRepo.save(loan);
	}
	
	@Override
	public LoanApplication updateLoan(LoanApplicationDTO loan, PropertyDTO propertyDto, MultipartFile file) throws PropertyAlreadyExistException, IOException {
		Customer customer = customerRepo.findById(loan.getCustomerId()).orElse(null);
		LoanType loanType = loanTypeRepo.findAllByLoanTypeName(loan.getLoanTypeName());
		double interestRate = loanType.getLoanInterestBaseRate();
		LoanApplication loanApp = loanRepo.findById(loan.getLoanApplicationId()).orElse(null);
		PropertyInfo propertyInfo = loanApp.getPropertyInfo();
		long propertyProofId = propertyInfo.getPropertyProof().getPropertyProofId();
		
		loanApp.setLoanId(loan.getLoanApplicationId());
		loanApp.setCustomer(customer);
		loanApp.setLoanType(loanType);
		loanApp.setPrincipal(loan.getPrincipal());
		loanApp.setTenureInMonths(loan.getTenureInMonths());
		loanApp.setInterestRate(interestRate);
		
		String propertyDtoAddress = propertyDto.getPropertyAddress();
		double propertyDtoArea = propertyDto.getPropertyAreaInm2();
		PropertyInfo tempProperty = propRepo.getProperty(propertyDtoAddress, propertyDtoArea);

		logger.info("Property details read...");
		if (tempProperty != null) {
			logger.warn("User is entering duplicate property details");
			throw new PropertyAlreadyExistException("Property details already taken.");
		}
		propertyInfo.setPropertyAddress(propertyDto.getPropertyAddress());
		propertyInfo.setPropertyAreaInm2(propertyDto.getPropertyAreaInm2());
		propertyInfo.setPropertyValue(propertyDto.getPropertyValue());
		
		logger.info("Property details saved...");
				
		PropertyProof proof = uploadPropertyRepo.findById(propertyProofId).orElse(null);
		proof.setName(file.getName());
		proof.setPropertyData(file.getBytes());
		proof.setType(file.getContentType());
		uploadPropertyRepo.save(proof);
	    
		propertyInfo.setPropertyProof(proof);
		propRepo.save(propertyInfo);
		loanApp.setPropertyInfo(propertyInfo);
		logger.info("loanApplication submitted successfully");
		return loanRepo.save(loanApp);
	}
	
	@Override
	public void cancelLoanApplication(long loanId) {
		logger.info("request for cancellation having loan id: "+ loanId);
		LoanApplication loan = loanRepo.findById(loanId).orElse(null);
		loan.setStatus("CANCELLED");
		loanRepo.save(loan);
	}

	@Override
	public double interestCalculator(long loanId,long customerId) {
		LoanApplication loan = loanRepo.propertiesToCalculate(loanId,customerId);
		logger.info("Interest is being calculated for the customer: " + customerId);
		return (loan.getPrincipal() * loan.getInterestRate() * loan.getTenureInMonths()) / 12;
	}

	@Override
	public  double emiCalculator(double principal, double rate, int tenure) {
        double monthlyRate = rate / 1200.0;
        double emi = principal * monthlyRate / (1 - Math.pow(1 + monthlyRate, -tenure));
        DecimalFormat df = new DecimalFormat("#.##");
        emi = Double.parseDouble(df.format(emi));
        return emi;
    }

	@Override
	public double emiCalculator(long loanId,long customerId) {
		LoanApplication loan = loanRepo.propertiesToCalculate(loanId,customerId);
		double p = loan.getPrincipal();
		double r = loan.getInterestRate();
		double t = loan.getTenureInMonths();

		double emi = (p * r * Math.pow((1 + r), t)) / (Math.pow((1 + r), (t - 1)));
		logger.info("EMI for the laon is calculated as: " + emi);
		return emi;
	}

	@Override
	public List<LoanApplication> filterAppliedLoanByType(long customerId, String loanType)
			throws LoanNotFoundException {
		if (isLoanTypeValid(loanType)) {			
			Stream<LoanApplication> stream = loanRepo.findAllByCustomerCustomerId(customerId).stream();
			List<LoanApplication> isPresent = stream
					.filter(loanApplication -> 
					loanApplication.getLoanType().getLoanTypeName().equalsIgnoreCase(loanType))
					.collect(Collectors.toList());
			if (isPresent==null || isPresent.isEmpty()) {

				logger.warn("No loan of type " + loanType + " found for that user");
				throw new LoanNotFoundException("You have not applied for any loan of type " + loanType);
			}
			logger.info("Loan Application with customerId " + customerId + " and loanType " + loanType + " is present");
			return isPresent;
		}
		return null;

	}

	@Override
	public List<LoanApplication> filterAppliedLoanByStatus(long customerId, String status)
			throws LoanNotFoundException {

		List<LoanApplication> loans = null;
		if (isLoanStatusValid(status)) {
			Stream<LoanApplication> stream = loanRepo.findAllByCustomerCustomerId(customerId).stream();
			List<LoanApplication> isPresent = stream
					.filter(loanApplication -> 
					loanApplication.getStatus().equalsIgnoreCase(status))
					.collect(Collectors.toList());
			if (isPresent==null|| isPresent.isEmpty()) {
				logger.warn("No loan of status " + status + " found for that user");
				throw new LoanNotFoundException("None of your loan is " + status);
			}
			logger.info("Loan Application with customerId " + customerId + " and status " + status + " is present");
			loans = isPresent;
		}
		return loans;
	}
	
	@Override
	public LoanApplication searchAppliedLoan(long customerId, long loanId) throws LoanNotFoundException {

		LoanApplication loan = null;
		if (isLoanIdValid(loanId)) {
			Stream<LoanApplication> stream = loanRepo.findAllByCustomerCustomerId(customerId).stream();
			LoanApplication isPresent = stream.filter(loans -> loans.getLoanId() == loanId).findAny().orElse(null);
			if (isPresent==null) {

				logger.warn("Customer has input wrong loan number " + loanId + " to search for...");
				throw new LoanNotFoundException("No Loan found with that loan number " + loanId);
			}
			logger.info("Loan Application with customerId " + customerId + " and loanId " + loanId + " is present");

			loan = isPresent;
		}
		return loan;

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

		if (isLoanIdValid(loanId) && isLoanStatusValid(status)) {
			logger.info("Admin is updating the loan application: " + loanId);
			loanRepo.updateLoanStatus(status, loanId);
		}

	}

	@Override
	public LoanApplication searchLoanById(long loanId) throws LoanNotFoundException {

		LoanApplication loan = null;
		if (isLoanIdValid(loanId)) {
			loan = loanRepo.findById(loanId).orElse(null);
			if (loan == null) {
				logger.warn("No Record Found for loanID " + loanId);
				throw new LoanNotFoundException("No Record Found for loanID " + loanId);
			}
			logger.info("Loan found with id " + loanId);
		}
		return loan;

	}

	public boolean isLoanTypeValid(String loanType) {
		loanType = loanType.strip();
		logger.info("validating entered loanType");
		if ((loanType != null && loanType.length() > 0) && (loanType.equalsIgnoreCase("Home Loan")
				|| loanType.equalsIgnoreCase("Gold Loan") || loanType.equalsIgnoreCase("Business Loan")
				|| loanType.equalsIgnoreCase("Vehicle Loan") || loanType.equalsIgnoreCase("Personal Loan"))) {
			logger.info("entered loanType is correct");
			return true;
		}
		logger.info("validations failed for entered loan type");
		return false;
	}

	public boolean isLoanIdValid(long loanId) {
		logger.info("Validating entered loanId");
		if (loanId >= 2001 && loanId <= 5000) {
			logger.info("entered loanId is correct");
			return true;
		}
		logger.warn("validation failed for entered loanId");
		return false;
	}

	public boolean isLoanStatusValid(String status) {
		status = status.strip();
		logger.info("Validating entered loan application status");
		if ((status != null && status.length() > 0) && (status.equalsIgnoreCase("Pending")
				|| status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("In-Progress") || status.equalsIgnoreCase("Cancelled"))) {
			logger.info("Entered loan application status is correct");
			return true;
		}
		logger.warn("Validation failed for entered status");
		return false;
	}

}
