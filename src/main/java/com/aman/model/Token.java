package com.aman.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@Data
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="token")
	private String token;
	
	@Column(name="is_logged_out")
	private Boolean loggedOut;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Token(Integer id, String token, Boolean loggedOut, User user) {
		super();
		this.id = id;
		this.token = token;
		this.loggedOut = loggedOut;
		this.user = user;
	}

	public Token() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getLoggedOut() {
		return loggedOut;
	}

	public void setLoggedOut(Boolean loggedOut) {
		this.loggedOut = loggedOut;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
