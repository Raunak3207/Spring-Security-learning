package com.sapient.spring_security_learning.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sapient.spring_security_learning.model.Customer;

@Repository
public interface CustomerRespository extends JpaRepository<Customer, Long>{

	List<Customer> findByEmail(String email);
  
	
}
