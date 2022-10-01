package com.sapient.spring_security_learning.config;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.hibernate.type.TrueFalseType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.sapient.spring_security_learning.filters.AuthoritiesLoggingAtFilter;
import com.sapient.spring_security_learning.filters.JWTTokenGenerator;
import com.sapient.spring_security_learning.filters.JWTTokenValidator;
import com.sapient.spring_security_learning.filters.LoggingFilter;
import com.sapient.spring_security_learning.filters.RequestValidationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true ,jsr250Enabled = true)//this annotation is used to enable method level security
public class SecurityConfig{
	
	@Bean
	SecurityFilterChain securityconfig(HttpSecurity http) throws Exception  {
		http.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration configuration = new CorsConfiguration();
				//list of origins allowed
				configuration.setAllowedOrigins(Collections.singletonList("*"));
				//allowing any credentials
				configuration.setAllowCredentials(true);
				//allowing http methods
				configuration.setAllowedMethods(Collections.singletonList("*"));
				//allowing max age of the coockie
				configuration.setMaxAge(3600L);
				//this to add a header to the response for jwt token
				configuration.setExposedHeaders(Arrays.asList("Authorization"));
				return configuration;
			}
		}).and().csrf().disable()
		//this is to add any issue regarding the post method which can result in data change at backend
		.csrf().ignoringAntMatchers("/h2-console/**")
		//this to manage a csrf token
		//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and().headers().frameOptions().sameOrigin()
		.and()
		.addFilterAfter(new LoggingFilter(),BasicAuthenticationFilter.class)
		.addFilterBefore(new JWTTokenValidator(), BasicAuthenticationFilter.class)
		.addFilterAfter(new JWTTokenGenerator(), BasicAuthenticationFilter.class)
		//.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
		//.addFilterAt(new AuthoritiesLoggingAtFilter(), RequestValidationFilter.class)
		.authorizeHttpRequests((auth)->
			
					auth.antMatchers("/cart/add-item")
					//.hasRole("read") this line to be added for role based authorization
					.authenticated()
					.antMatchers("/userlogin").authenticated()
					//there are three types of matchers are available in spring
					//mvc is better than ant in terms of security
					//.mvcMatchers("/cart/add-item")
					.mvcMatchers("/getrole").hasAuthority("read")
					//.regexMatchers(null)  regex matcher provides more flexibility in terms if patttern matching
					.antMatchers("/policy/get").permitAll()
					.antMatchers("/h2-console/**").permitAll()
					
			
		).httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	/* used for in-Memory userdetails */
/*	@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
        UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
        userDetailsService.createUser(admin);
        userDetailsService.createUser(user);
        return userDetailsService;
    }  */

	/* used for database stored userdetails where in database we need some specific data view */
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//	  return new JdbcUserDetailsManager(dataSource);
//    }

    
	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return
	 * NoOpPasswordEncoder.getInstance(); }
	 */
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
