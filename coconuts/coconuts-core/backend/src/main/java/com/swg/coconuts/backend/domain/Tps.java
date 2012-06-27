package com.swg.coconuts.backend.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tps implements Serializable{

	private static final long serialVersionUID = -4731447636293905168L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer nomor;
	
	@ManyToOne(targetEntity=Kelurahan.class)
	private Kelurahan kelurahan;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setNomor(Integer nomor) {
		this.nomor = nomor;
	}
	public Integer getNomor() {
		return nomor;
	}
	public void setKelurahan(Kelurahan kelurahan) {
		this.kelurahan = kelurahan;
	}
	public Kelurahan getKelurahan() {
		return kelurahan;
	}

	
	
	

}
