package com.swg.coconuts.backend.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;

@Embeddable
public class VoteTps implements Serializable{

	private static final long serialVersionUID = 4236585075265662577L;

	//private Tps tps;
	
	private Integer nomorTps;
	
	private String namaKelurahan;
	
	private Map<CandidateCouple, Integer> distribution=new HashMap<CandidateCouple, Integer>();
	
	private Integer invalid;
	
	private Integer abstain;

	public void setNomorTps(Integer nomorTps) {
		this.nomorTps = nomorTps;
	}
	
	public Integer getNomorTps() {
		return nomorTps;
	}
	
	public void setNamaKelurahan(String namaKelurahan) {
		this.namaKelurahan = namaKelurahan;
	}
	
	public String getNamaKelurahan() {
		return namaKelurahan;
	}

	public Map<CandidateCouple, Integer> getDistribution() {
		return distribution;
	}

	public void setDistribution(Map<CandidateCouple, Integer> distribution) {
		this.distribution = distribution;
	}

	public Integer getInvalid() {
		return invalid;
	}

	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}

	public Integer getAbstain() {
		return abstain;
	}

	public void setAbstain(Integer abstain) {
		this.abstain = abstain;
	}
	
	public Integer getTotal(){
		int total=0;
		for(CandidateCouple couple:distribution.keySet()){
			if(distribution.get(couple)!=null){
				total+=distribution.get(couple);
			}
		}
		total+=abstain;
		total+=invalid;
		return total;
	}
}
