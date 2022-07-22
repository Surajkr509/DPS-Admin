package com.dps_admin.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.dps_admin.model.Admin;
import com.dps_admin.service.AdminDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	
	@Value("${com.dps.jwtSecret}")
	private String jwtSecret;
	
	@Value("${com.dps.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {
		AdminDetailsImpl adminDetailsImpl = (AdminDetailsImpl) authentication.getPrincipal();
		return Jwts.builder().setSubject((adminDetailsImpl.getEmail())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			System.err.println("Invalid JWT signature: {}" + e.getMessage());
		} catch (MalformedJwtException e) {
			System.err.println("Invalid JWT token: {}" + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.err.println("JWT token is expired: {}" + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.err.println("JWT token is unsupported: {}" + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.err.println("JWT claims string is empty: {}" + e.getMessage());
		}
		return false;
	}

}
