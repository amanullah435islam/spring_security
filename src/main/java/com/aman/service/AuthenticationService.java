package com.aman.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aman.jwt.JwtService;
import com.aman.model.Token;
import com.aman.model.User;
import com.aman.others.AuthenticationResponse;
import com.aman.repository.ITokenRepository;
import com.aman.repository.IUserRepository;

@Service
public class AuthenticationService {

	private final IUserRepository repository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final ITokenRepository tokenRepository;
	
	private final AuthenticationManager authenticationManager;

	public AuthenticationService(IUserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService,
			ITokenRepository tokenRepository, AuthenticationManager authenticationManager) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.tokenRepository = tokenRepository;
		this.authenticationManager = authenticationManager;
	}
	
	public AuthenticationResponse register(User request) {
		
		//Check if the user already exists
	if (repository.findByEmail(request.getUsername()).isPresent()) {
		return new AuthenticationResponse(null, "User already exists");
	}
	
	//Create a new user entity and save it to the database
	
	User user = new User();
	user.setName(request.getName());
	user.setEmail(request.getEmail());
	user.setPassword(passwordEncoder.encode(request.getPassword()));
	user.setRole(request.getRole());
	
	user = repository.save(user);
	
	
	//Generate JWT token for the newly registered user
	String jwt = jwtService.generateToken(user);
	
	//Save the token to the token repository
	saveUserToken(jwt, user);
	
	return new AuthenticationResponse(jwt, "User registration was successfully");
	}
	
	
	

	public AuthenticationResponse authenticate(User request) {
		
		//Authentiction user credentials using security's AuthenticationManager
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					request.getUsername(),
					request.getPassword()
						
						)
				
				);
		//Retrieve the user from the database
		User user = repository.findByEmail(request.getUsername()).orElseThrow();
		
	
		//Generate JWT token for the authenticated user
		String jwt = jwtService.generateToken(user);
		
		
		//Revoke all existing tokens for this user
		revoAllTokenByUser(user);
		
		
		//save the new token to the token repository
		saveUserToken(jwt, user);
		
		return new AuthenticationResponse(jwt, "User login was successfully");
	}

	private void revoAllTokenByUser(User user) {
		
		List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
		if (validTokens.isEmpty()) {
			return;
		}
		
		
		//set all valid tokens for the user to logged out
		validTokens.forEach( t -> {
				t.setLoggedOut(true);
		});
		
		//save the changes to the tokens in the token repository
		tokenRepository.saveAll(validTokens);
		
	}
	
	
	
	
	//method to save a token for a user to the token repository
	private void saveUserToken(String jwt, User user) {
		Token token = new Token();
		token.setToken(jwt);
		token.setLoggedOut(false);
		token.setUser(user);
		
		tokenRepository.save(token);
		
	}
	
}
