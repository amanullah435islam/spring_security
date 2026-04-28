package com.aman.jwt;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.aman.model.Token;
import com.aman.model.User;
import com.aman.repository.ITokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor

public class JwtService {

	private final ITokenRepository iTokenRepository;

	public JwtService(ITokenRepository iTokenRepository) {
		this.iTokenRepository = iTokenRepository;
	}
	
	private final String SECREAT_KEY = "30c86f039f5e75d7c85abaa9c40ae9c54eb8a861fb756833899a8ac0d50b6848";
	
	
	
	// Extracts username from JWT token
	//6 no:
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
				
	}
	
	
	//Checks if the token is expired
	// 7 no:
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	
	//Extracts expiration date from the token
	// 8 no:
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}


	//Validates whether the token is valid for a given user
	// 5 no:
	public boolean isValid(String token, UserDetails user) {
		String username = extractUsername(token);
		
		// Checks if the token is valid and not expired
		boolean validToken = iTokenRepository
				.findByToken(token)
				.map(t -> !t.getLoggedOut())
				.orElse(false);
		
		return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
	}
	
	
	
	
	//Extracts a specific claim from the token's claims
	// 4 no:
	public <T> T extractClaim(String token, Function<Claims, T>resolver) {
		
		Claims claims = extractAllClaim(token);
		return resolver.apply(claims);
		
	}
	
	// Parser and verifies the token to extract all claims
	// 1 no:
	private Claims extractAllClaim(String token) {
		return Jwts
				.parser()   //convert
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)   
				.getPayload();
	}

	
	// Reetrieves the signing key used for JWT signing and verification
	// 2 no:
	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(SECREAT_KEY);
		return Keys.hmacShaKeyFor(keyBytes);   //algoridom convert
	}
	
	
	// Token genetate
	// 3 no:
public String generateToken(User user) {

	String token = Jwts
			.builder()
			//Setting the subject of the token to the user's address.
			.subject(user.getEmail())
			
			//Setting the timestamp when the token was issued to the current time
			.issuedAt(new Date(System.currentTimeMillis()))
			
			//Setting the expiration time of the token to 24 hours from the current time
			.expiration(new Date(System.currentTimeMillis()+24*60*60*10000))
			
			//signing the token with a signing key obtained from a method called getSigningKey() 
			.signWith(getSigninKey())
			
			//Compacting the token into its final string representation
			.compact();
	
	//returning the generate token
	return token;
	
}	
	
	
	
}
