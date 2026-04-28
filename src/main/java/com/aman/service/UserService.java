package com.aman.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aman.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class UserService implements UserDetailsService{

	private final IUserRepository iUserRepository;
	
	public UserService(IUserRepository iUserRepository){
	    this.iUserRepository = iUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return iUserRepository.findByEmail(username)
				.orElseThrow(()->new UsernameNotFoundException("no user found with this mail"));
	}

}
