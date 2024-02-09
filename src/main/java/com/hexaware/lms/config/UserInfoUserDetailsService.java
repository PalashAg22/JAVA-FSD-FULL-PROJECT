package com.hexaware.lms.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.repository.AdminRepository;
import com.hexaware.lms.repository.CustomerRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Admin> adminInfo = null;
		Optional<Customer> customerInfo = null;
		boolean isCustomer=false;
		if(username.endsWith("@hexaware.com")) {
			adminInfo = adminRepo.findByEmail(username);
		}
		else {
			customerInfo = customerRepo.findByEmail(username);
			isCustomer=true;
		}
		if(isCustomer) {
			return customerInfo.map(UserInfoUserDetails::new)
	                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
		}
        return adminInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}

}
