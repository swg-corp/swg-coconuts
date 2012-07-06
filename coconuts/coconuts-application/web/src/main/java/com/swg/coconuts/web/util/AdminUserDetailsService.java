package com.swg.coconuts.web.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminUserDetailsService implements UserDetailsService {
	
    final String adminUserKey="admin.username";
	final String adminPasswordKey="admin.password";
	
	private Properties adminProperties;

	
	@Override
	public UserDetails loadUserByUsername(String arg0)throws UsernameNotFoundException {
		if(arg0==null || arg0.trim().isEmpty()){
			throw new UsernameNotFoundException("username not found");
		}
		String adminUser=adminProperties.getProperty(adminUserKey);
		if(!adminUser.equals(arg0)){
			throw new UsernameNotFoundException("username not match");
		}
		Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		User admin=new User(arg0, adminProperties.getProperty(adminPasswordKey), true, true, true, true, authorities);
		
		return admin;
	}
	
	public void setAdminProperties(Properties adminProperties) {
		this.adminProperties = adminProperties;
	}
	
	public Properties getAdminProperties() {
		return adminProperties;
	}

}
