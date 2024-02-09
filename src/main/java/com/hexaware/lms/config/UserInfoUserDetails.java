package com.hexaware.lms.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexaware.lms.entities.Admin;
import com.hexaware.lms.entities.Customer;


public class UserInfoUserDetails implements UserDetails {

	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	 
	public UserInfoUserDetails (Admin adminInfo) {
		username=adminInfo.getEmail();
        password=adminInfo.getPassword();
        authorities= Arrays.stream(adminInfo.getRole().split(","))

                .map(SimpleGrantedAuthority::new) // .map(str -> new SimpleGrantedAuthority(str))
                .collect(Collectors.toList());
	}

	public UserInfoUserDetails (Customer customerInfo) {
		username=customerInfo.getEmail();
        password=customerInfo.getPassword();
        authorities= Arrays.stream(customerInfo.getRole().split(","))
                .map(SimpleGrantedAuthority::new) // .map(str -> new SimpleGrantedAuthority(str))
                .collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
