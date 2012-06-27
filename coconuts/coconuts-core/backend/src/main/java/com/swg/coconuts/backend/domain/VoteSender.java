package com.swg.coconuts.backend.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class VoteSender implements Serializable{

	private static final long serialVersionUID = 8622212006891038679L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@NotNull
	private String name;
	//@Pattern(regexp="^((\\+62)|0)\\d{7,11}+")
	//@Column(unique=true, length=15)
	private String cellularNumber;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCellularNumber() {
		return cellularNumber;
	}
	public void setCellularNumber(String cellularNumber) {
		this.cellularNumber = cellularNumber;
	}
	
	
}
