package com.sapient.spring_security_learning.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*this class needs to implemented when we need to use custom 
 * table for authentication
 * 
 */
public class SecurityCustomer  implements UserDetails{
	
	private static final long serialVersionUID = -6690946490872875352L;
	
	private final Customer customer;
	
	public SecurityCustomer(Customer customer) {
		this.customer =customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	List<GrantedAuthority> authorities  = new ArrayList<GrantedAuthority>();
	for(Authority authority :customer.getAuthorities())
	authorities.add(new SimpleGrantedAuthority(authority.getName()));
		return authorities ;
	}

	@Override
	public String getPassword() {
		return customer.getPwd();
	}

	@Override
	public String getUsername() {
		return customer.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
