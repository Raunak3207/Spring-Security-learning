package com.sapient.spring_security_learning.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MethodLevelSecurityExample {
       
	@GetMapping("/preAuthorize")
	@PreAuthorize(value = "read")  //this annotation is used to preAuthorize and method will be not accessed until authorization is successes
	public String acceesspreAuth() {
		return "preauthorized accessed";
	}
	
	@GetMapping("/postAuthorize")
	@PostAuthorize(value="read") // mehtod will be executed and if the auth success then data is returned to the client other wise not returned
	public String acceesspostAuth() {
		return "preauthorized accessed";
	}
	@GetMapping("/preFilter") //same as preauth but as a filter
	@PreFilter("filterObject == 'raunak'")
	public String acceesspreFilter(@RequestParam String name) {
		return "preauthorized accessed";
	}
	@GetMapping("/postFilter")
	@PostFilter("filterObject =='raunak'") // same as postAuth but as a filter
	public String acceesspostFilter() {
		return "preauthorized accessed";
	}
}
