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
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtService jwtService;
	
	private final UserService service;
	

//	@Override
//	protected void doFilterInternal(@NonNull HttpServletRequest request,
//									@NonNull HttpServletResponse response,
//									@NonNull FilterChain filterChain)throws ServletException, IOException {
//
//		String authHeader = request.getHeader("Authorization");
//
//		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
////			//If no token or token formate is incorrect, proced with the filterchain
////			System.out.println(authHeader);
//			filterChain.doFilter(request, response);
//			return;
//		}
//
//		//String token = authHeader.substring(7);
//
//		// Replaces all whitespace characters anywhere in the string
//		String token = authHeader.substring(7).replaceAll("\\s", "");
//
//		String username = jwtService.extractUsername(token);
//
//		System.out.println("HEADER: " + authHeader);
//		System.out.println("TOKEN: [" + token + "]");
//
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			//Load user details by username from userDetailsService
//
//			UserDetails userDetails = service.loadUserByUsername(username);
////			System.out.println(username);
//
//			//Validate token and user details
//			if (jwtService.isValid(token, userDetails)) {
//				// If token is valid, create authentication token
//
//				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities()
//						);
//
//				//set authentication token details
//				authToken.setDetails(
//						new WebAuthenticationDetailsSource().buildDetails(request)
//						);
//
//				//set authentication token in security contex
//				SecurityContextHolder.getContext().setAuthentication(authToken);
////				System.out.println(authToken);
//			}
//		}
//
//		//Proceed with the filter chain
//		filterChain.doFilter(request, response);
//






@Override
protected void doFilterInternal(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull FilterChain filterChain) throws ServletException, IOException {

	final String authHeader = request.getHeader("Authorization");

	if (authHeader == null || !authHeader.startsWith("Bearer ")) {
		filterChain.doFilter(request, response);
		return;
	}

	try {
		// Use replaceAll("\\s", "") to remove ALL potential hidden spaces/newlines
		//final String token = authHeader.substring(7).replaceAll("\\s", "");
		// Removes ALL non-printable characters and whitespace
		//String token = authHeader.substring(7).replaceAll("[\\p{Cntrl}\\s]", "");
		String[] parts = authHeader.split(" ");
		if (parts.length != 2) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = parts[1].trim();


		System.out.println("TOKEN RAW: [" + token + "]");
		System.out.println("TOKEN LENGTH: " + token.length());
		System.out.println("AUTH HEADER: [" + authHeader + "]");


		final String username = jwtService.extractUsername(token);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = service.loadUserByUsername(username);

			System.out.println("AUTHORITIES: " + userDetails.getAuthorities());


			if (jwtService.isValid(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()
				);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
	} catch (Exception e) {
		// Log the error but don't let it crash the filter chain
		System.err.println("JWT Parsing failed: " + e.getMessage());
	}
	System.out.println("AUTH HEADER: [" + authHeader + "]");
	filterChain.doFilter(request, response);
}





}
