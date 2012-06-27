package com.swg.coconuts.backend.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.swg.coconuts.initiator.param.Area;

@Entity
public class Kelurahan implements Area{

	private static final long serialVersionUID = 5357313049689181072L;
	
	private String name;
	private String code;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(targetEntity=Kecamatan.class)
	private Kecamatan kecamatan;
	
	@OneToMany(mappedBy="kelurahan", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Tps> daftarTps=new ArrayList<Tps>();
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public void setCode(String code) {
		this.code=code;
	}
	
	@Override
	public Area getParent() {
		return getKecamatan();
	}

	public Kecamatan getKecamatan() {
		return kecamatan;
	}

	public void setKecamatan(Kecamatan kecamatan) {
		this.kecamatan = kecamatan;
	}

	public List<Tps> getDaftarTps() {
		return daftarTps;
	}

	public void setDaftarTps(List<Tps> daftarTps) {
		this.daftarTps = daftarTps;
	}

	@Override
	public String toString() {
		return "Kelurahan [name=" + name + ", code=" + code + ", kecamatan="
				+ kecamatan.getName() + "]";
	}
	
	

}
