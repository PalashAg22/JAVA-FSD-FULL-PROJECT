package com.hexaware.lms.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.entities.Customer;
import com.hexaware.lms.repository.AdminRepository;
import com.hexaware.lms.repository.CustomerRepository;

public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Admin> adminInfo=null;
		Optional<Customer> customerInfo = null;
		
		if(username.endsWith("@hexaware.com")) {
			adminInfo = adminRepo.findByEmail(username);
		       return adminInfo.map(UserInfoUserDetails::new)
		                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
		}
			customerInfo = customerRepo.findByEmail(username);
			return customerInfo.map(UserInfoUserDetails::new)
					.orElseThrow(() -> new UsernameNotFoundException("user not found "+username));
		
	}

}
