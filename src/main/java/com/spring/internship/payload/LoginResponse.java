package com.spring.internship.payload;

import java.util.Optional;

import com.spring.internship.model.User;

public class LoginResponse {
	private String accessToken;
    private String tokenType = "Bearer";
    private Optional<User> user;
    
    public LoginResponse(String token,Optional<User> user) {
		this.accessToken = token;
		this.user = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Optional<User> getUser() {
		return user;
	}

	public void setUser(Optional<User> user) {
		this.user = user;
	}
    
    
}
