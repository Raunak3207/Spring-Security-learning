package com.sapient.spring_security_learning.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {
	
	@GetMapping("/getrole")
	public String getRole() {
		return "role based auth succed";
	}

}
