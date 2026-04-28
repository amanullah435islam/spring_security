package com.aman.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aman.service.UserService;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtService jwtService;
	
	private final UserService service;
	
	public JwtAuthenticationFilter(JwtService jwtService, UserService service) {
		this.jwtService = jwtService;
		this.service = service;
	}




	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, 
									@NonNull HttpServletResponse response, 
									@NonNull FilterChain filterChain)throws ServletException, IOException {

		
		String authHeader = request.getHeader("AUTHORIZATION");
		
		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			//If no token or token formate is incorrect, proced with the filterchain
			
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authHeader.substring(7);
		String username = jwtService.extractUsername(token);
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			//Load user details by username from userDetailsService
			
			UserDetails userDetails = service.loadUserByUsername(username);
			
			//Validate token and user details
			if (jwtService.isValid(token, userDetails)) {
				// If token is valid, create authentication token
				
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()
						);
				
				//set authentication token details
				authToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
						);
				
				//set authentication token in security contex
				SecurityContextHolder.getContext().setAuthentication(authToken);
				 
			}
		}
		
		//Proceed with the filter chain
		
		filterChain.doFilter(request, response);
		
	}

}
