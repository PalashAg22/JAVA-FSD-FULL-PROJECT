package com.hexaware.lms.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hexaware.lms.config.UserInfoUserDetailsService;
import com.hexaware.lms.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


	Logger loggers = LoggerFactory.getLogger(JwtAuthFilter.class);
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		loggers.info("Request received in JwtAuthFilter");
		String authHeader = request.getHeader("Authorization");
		loggers.info("AuthFilter: "+authHeader);
		String token = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
			loggers.info("Extracted UserName: "+username);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			loggers.info("validating username and userdetails");

			boolean isTokenValid = jwtService.validateToken(token, userDetails);
			if (isTokenValid) {

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);

				 loggers.info("Credentials Verified");

			}
		}
		filterChain.doFilter(request, response);
	}

}
