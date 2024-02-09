package com.hexaware.lms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hexaware.lms.entities.UserInfo;
import com.hexaware.lms.repository.AdminRepository;
import com.hexaware.lms.repository.CustomerRepository;

public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo = null;
		if(username.endsWith("@hexaware.com")) {
			userInfo = new UserInfo(adminRepo.findByName(username).orElse(null));
		}
		else {
			userInfo = new UserInfo(customerRepo.findByName(username).orElse(null));
		}
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}

}
