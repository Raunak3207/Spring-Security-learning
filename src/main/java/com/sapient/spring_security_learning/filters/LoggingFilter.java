package com.sapient.spring_security_learning.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LoggingFilter implements Filter{
	
	private final Logger logger  = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		String ipAddress = httprequest.getHeader("HTTP_X_FORWARDED_FOR");

		if (ipAddress == null) {
		    ipAddress = request.getRemoteAddr();
		}
		
		logger.info(ipAddress);
		
		chain.doFilter(request, response);
		
	}

}
