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

	Logger logger = LoggerFactory.getLogger(JwtService.class);

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public String generateToken(String username) {
  logger.info("Generating token for username: {}", username);

		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	public String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		logger.info("generating keys");
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims extractAllClaims(String token) {
		logger.info("Extracting all claims from token");
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		logger.info("extracting claims from token");
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		logger.info("Extracting username from token");
		String userName= extractClaim(token, Claims::getSubject);
		logger.info("Extracted username from token: "+userName);
		return userName;
	}

	public Date extractExpiration(String token) {
		logger.info("Extracting expiration from token");
		return extractClaim(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {
		logger.info("Checking if token is expired");
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		logger.info("Validating Token");
		final String username = extractUsername(token);
		logger.info(username);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
