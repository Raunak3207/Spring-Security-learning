package com.sapient.spring_security_learning.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@GetMapping("/add-item")
	public String add_To_Cart() {
		return "item added and API Accessed";
	}
	
}
