package com.swg.coconuts.web.util;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class CoconutsSecurityService implements SecurityService{

	private AuthenticationManager authenticationManager;
	
	@Override
	public boolean login(String username, String password) {
		try{
			Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if(authentication.isAuthenticated()){
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return true;
			}
		}catch(AuthenticationException e){
			
		}
		return false;
	}
	
	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

}
