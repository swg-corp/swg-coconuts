package com.swg.coconuts.backend.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Candidate implements Serializable{

	private static final long serialVersionUID = -8994927834153475950L;
	
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date birthDay;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	
}
