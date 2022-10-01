package com.sapient.spring_security_learning.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sapient.spring_security_learning.utils.Security_Constants;

public class JWTTokenValidator  extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(Security_Constants.JWT_HEADER);
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(Security_Constants.JWT_KEY.getBytes());
		    JWTVerifier verifier = JWT.require(algorithm)
		        .build();
		    DecodedJWT jwt = verifier.verify(token);
		    Claim name =jwt.getClaim("username");
		    String username =name.asString();
		    Claim authorities  =  jwt.getClaim("authorities");
		    String authoritiesString  = authorities.asString();
		    Authentication auth = new UsernamePasswordAuthenticationToken( username,null,AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesString));
		    SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (JWTVerificationException exception){
		    throw new BadCredentialsException("Bad Credentials");
		    
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		if(request.getServletPath().equals("/userlogin") || new AntPathMatcher().match("/h2-console/**", request.getServletPath())) {
			return true;
		}
	return false;
	}

}
