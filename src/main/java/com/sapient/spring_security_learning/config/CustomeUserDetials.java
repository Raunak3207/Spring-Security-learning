package com.sapient.spring_security_learning.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sapient.spring_security_learning.model.Customer;
import com.sapient.spring_security_learning.model.SecurityCustomer;
import com.sapient.spring_security_learning.repository.CustomerRespository;
/*
 *this class needs to be implemented for custom table for authentication
 */
@Service
public class CustomeUserDetials implements UserDetailsService {
	
	@Autowired
	CustomerRespository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  List<Customer> users = repo.findByEmail(username);
		  if(users.size()==0) {
			  throw  new UsernameNotFoundException("user does not exists");
		  }
		  
		return new SecurityCustomer(users.get(0));
	}

}
