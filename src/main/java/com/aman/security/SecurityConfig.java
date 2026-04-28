package com.aman.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.aman.jwt.JwtAuthenticationFilter;
import com.aman.service.UserService;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

	private final UserService service;
	private final JwtAuthenticationFilter authenticationFilter;
	

	public SecurityConfig(UserService service, JwtAuthenticationFilter authenticationFilter) {
		super();
		this.service = service;
		this.authenticationFilter = authenticationFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req -> req
						.requestMatchers("/login/**", "/register/**")
						.permitAll()
						
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.requestMatchers("/user/**").hasAnyAuthority("ADMIN","USER")
						.requestMatchers("/employee/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
						.anyRequest()
						.authenticated()
						
						)
				.userDetailsService(service)
				.sessionManagement(
							session ->
								session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						)
			
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
							
	}

	

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuraiton) {
		return configuraiton.getAuthenticationManager();
		
	}
	
}
