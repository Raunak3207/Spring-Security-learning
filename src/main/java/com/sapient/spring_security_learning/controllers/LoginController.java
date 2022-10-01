package com.sapient.spring_security_learning.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	
	@GetMapping("/userlogin")
	public String login() {
		return "login success";
	}
}
