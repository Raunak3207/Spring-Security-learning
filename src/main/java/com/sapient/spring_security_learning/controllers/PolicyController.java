package com.sapient.spring_security_learning.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.spring_security_learning.repository.CustomerRespository;

@RestController
@RequestMapping("/policy")
public class PolicyController {
	
	@Autowired
	CustomerRespository repo;
	
	@GetMapping("/get/{email}")
	public String policy(@PathVariable String email) {
		repo.findByEmail(email).get(0).getAuthorities().stream().forEach(X->System.out.println(X.getName()));
		return "hello";
	}
}


