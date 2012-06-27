package com.swg.coconuts.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.swg.coconuts.initiator.param.Area;
import com.swg.coconuts.initiator.param.BigArea;

@Entity
public class Provinsi implements BigArea{
	private static final long serialVersionUID = -8847763089170458284L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String code;
	@OneToMany(mappedBy="provinsi",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Kabupaten> kabupatens=new ArrayList<Kabupaten>();

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setCode(String code) {
		this.code=code;
	}

	@Override
	public String getCode() {
		return code;
	}
	
	public void setKabupatens(List<Kabupaten> kabupatens) {
		this.kabupatens = kabupatens;
	}
	
	public List<Kabupaten> getKabupatens() {
		return kabupatens;
	}

	@Override
	public Area getParent() {
		throw new IllegalArgumentException(getClass().getName()+" is currently the head of all area. No parent for this class");
	}

	@Override
	public List<? extends Area> getChilds() {
		return getKabupatens();
	}
	
	public void addCity(Kabupaten kabupaten){
		kabupaten.setProvinsi(this);
		this.getKabupatens().add(kabupaten);
	}

}
