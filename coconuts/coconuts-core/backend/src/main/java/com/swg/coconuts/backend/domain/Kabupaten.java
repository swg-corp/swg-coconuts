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
public class Kabupaten implements BigArea{
	private static final long serialVersionUID = -5089605864540795094L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String code;
	
	@OneToMany(mappedBy="kabupaten", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Kecamatan> kecamatans=new ArrayList<Kecamatan>();
	
	@ManyToOne(targetEntity=Provinsi.class)
	private Provinsi provinsi;
	
	public Kabupaten() {
	}

	public Kabupaten(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void addDistrict(Kecamatan kecamatan){
		kecamatans.add(kecamatan);
		kecamatan.setCity(this);
		
	}
	
	public void setProvinsi(Provinsi provinsi) {
		this.provinsi = provinsi;
	}
	
	public Provinsi getProvinsi() {
		return provinsi;
	}

	@Override
	public Area getParent() {
		return getProvinsi();
	}
	
	public List<Kecamatan> getKecamatans() {
		return kecamatans;
	}

	public void setKecamatans(List<Kecamatan> kecamatans) {
		this.kecamatans = kecamatans;
	}

	@Override
	public List<? extends Area> getChilds() {
		for(Kecamatan d:kecamatans){
			if(!(d instanceof Kecamatan)){
				
			}
		}
		return getKecamatans();
	}
}
