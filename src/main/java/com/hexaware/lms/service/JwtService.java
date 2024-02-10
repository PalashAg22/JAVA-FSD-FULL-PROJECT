package com.hexaware.lms.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	 private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	 public static final String SECRET = "cG9zaXRpb25iYXNlYmFsbHNwcmVhZG51bWVyYWxjdXJpb3VzaHVudGFtbGVhdmluZ20=";
	
		public String generateToken(String username) {
	        logger.info("Generating token for username: {}", username);

			Map<String,Object>  claims = new HashMap<>();
			
			return createToken(claims, username);
			
		}
		
		public String createToken(Map<String,Object> claims , String username) {
			
	        logger.info("Creating token for username: {}", username);

			return Jwts.builder()
					   .setClaims(claims)
					   .setSubject(username)
					   .setIssuedAt(new Date(System.currentTimeMillis()))
					   .setExpiration(new Date(System.currentTimeMillis()+1000*60*120))
					   .signWith(getSignKey() , SignatureAlgorithm.HS256).compact();
					   
					   
		}
		
		 private Key getSignKey() {
			 logger.info("generating keys");
		        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
		        return Keys.hmacShaKeyFor(keyBytes);
		    }
		 
		 
		 
		 
		 
		  public String extractUsername(String token) {
		        logger.info("Extracting username from token");

		        return extractClaim(token, Claims::getSubject);
		    }

		    public Date extractExpiration(String token) {
		        logger.info("Extracting expiration from token");

		        return extractClaim(token, Claims::getExpiration);
		    }

		    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		    	logger.info("extracting claims from token");
		        final Claims claims = extractAllClaims(token);
		        return claimsResolver.apply(claims);
		    }

		    private Claims extractAllClaims(String token) {
		        logger.info("Extracting all claims from token");

		        return Jwts
		                .parserBuilder()
		                .setSigningKey(getSignKey())
		                .build()
		                .parseClaimsJws(token)
		                .getBody();
		    }

		    private Boolean isTokenExpired(String token) {
		        logger.info("Checking if token is expired");

		        return extractExpiration(token).before(new Date());
		    }

		    public Boolean validateToken(String token, UserDetails userDetails) {
		    	logger.info("is valid token or not");
		        final String username = extractUsername(token);
		        logger.info(username +" ******************");
		        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		    }
	
}
