package com.swg.coconuts.web;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.swg.coconuts.web.util.SecurityService;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{coconutsSecurityService}")
	private SecurityService securityService;
	
	private String username;
	private String password;
	
	
	public String login(){
		boolean success=securityService.login(username, password);
		if(success){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Welcome!!"));
			return "/pages/main.xhtml?faces-redirect=true";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("login failed"));
			return "/login";
		}
	}
	
	public String logout(){
		
		return "j_spring_security_logout?faces-redirect=true";
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public SecurityService getSecurityService() {
		return securityService;
	}
	
	
}
