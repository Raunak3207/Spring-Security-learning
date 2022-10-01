package com.sapient.spring_security_learning.filters;

import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.sapient.spring_security_learning.utils.Security_Constants;

public class JWTTokenGenerator extends OncePerRequestFilter  {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication  =  SecurityContextHolder.getContext().getAuthentication();
		
		Calendar date = Calendar.getInstance();
		long datetimeinmillis = date.getTimeInMillis();
		Date expirationtime = new Date(datetimeinmillis+(10*60*1000)); 
		if(authentication!=null) {
			
		
		try {
		Algorithm algorithm = Algorithm.HMAC256(Security_Constants.JWT_KEY.getBytes());
		String token  = 	JWT.create()
								.withIssuer("autho0")
								.withClaim("username", authentication.getName())
								.withClaim("authorities",populateAuthorities(authentication.getAuthorities()))
								.withIssuedAt(new Date())
								.withExpiresAt(expirationtime)
								.sign(algorithm);
		
		response.setHeader(Security_Constants.JWT_HEADER, token);
								
		}catch(JWTCreationException ex) {
			ex.printStackTrace();
			throw new BadCredentialsException("failed to create tokem");
		}
		}
		filterChain.doFilter(request, response);
		
	}

	 @Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		 	return  !request.getServletPath().equals("/userlogin");
	}
	 
	 private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
			Set<String> authoritiesSet = new HashSet<>();
	        for (GrantedAuthority authority : collection) {
	        	authoritiesSet.add(authority.getAuthority());
	        }
	        return String.join(",", authoritiesSet);
		}
}
