package com.sapient.spring_security_learning.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sapient.spring_security_learning.model.Authority;
import com.sapient.spring_security_learning.model.Customer;
import com.sapient.spring_security_learning.repository.CustomerRespository;

@Service
public class CustomAuthtenticationProvider implements AuthenticationProvider {
	
	@Autowired
	CustomerRespository repo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username  = authentication.getName();
		String pwd  = authentication.getCredentials().toString();
		List<Customer> users = repo.findByEmail(username);
		if(users.size()>0) {
			if(passwordEncoder.matches(pwd, users.get(0).getPwd())) {
				
				return new UsernamePasswordAuthenticationToken(username,pwd,getAuthorities(users.get(0)));
			}else {
				throw new BadCredentialsException("Bad Credentials"); 
			}
		}else {
			throw new BadCredentialsException("No user found");
		}
		
	}
	
	public List<GrantedAuthority> getAuthorities(Customer customer){
		List<GrantedAuthority> authorities  = new ArrayList<GrantedAuthority>();
		 for(Authority authority : customer.getAuthorities()) {
			 authorities.add(new SimpleGrantedAuthority(authority.getName()));
		 }
		 return authorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
