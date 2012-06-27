package com.swg.coconuts.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.swg.coconuts.initiator.param.Area;
import com.swg.coconuts.initiator.param.BigArea;

@Entity
public class Kecamatan implements BigArea{
	private static final long serialVersionUID = 8303744617515537234L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	private String code;
	@ManyToOne(targetEntity=Kabupaten.class)
	private Kabupaten kabupaten;
	@OneToMany(mappedBy="kecamatan", cascade=CascadeType.ALL)
	private List<Kelurahan> kelurahans=new ArrayList<Kelurahan>();
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}
	
	public void setCity(Kabupaten kabupaten) {
		this.kabupaten = kabupaten;
	}
	
	public Kabupaten getCity() {
		return kabupaten;
	}
	
	public void addVillage(Kelurahan kelurahan){
		kelurahans.add(kelurahan);
		kelurahan.setKecamatan(this);
	}

	@Override
	public Area getParent() {
		return kabupaten;
	}

	public Kabupaten getKabupaten() {
		return kabupaten;
	}

	public void setKabupaten(Kabupaten kabupaten) {
		this.kabupaten = kabupaten;
	}

	public List<Kelurahan> getKelurahans() {
		return kelurahans;
	}

	public void setKelurahans(List<Kelurahan> kelurahans) {
		this.kelurahans = kelurahans;
	}

	@Override
	public List<? extends Area> getChilds() {
		for(Kelurahan v:kelurahans){
			if(!(v instanceof Kelurahan)){
				throw new IllegalArgumentException();
			}
		}
		return getKelurahans();
	}

}
