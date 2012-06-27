package com.swg.coconuts.backend.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.swg.coconuts.backend.Votable;

@Entity
public class VoteCouple implements Votable,Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private static final long serialVersionUID = 5385133221663027129L;

	@OneToOne(targetEntity=CandidateCouple.class)
	private CandidateCouple candidateCouple;
	
	private Integer total=0;
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setCandidateCouple(CandidateCouple candidateCouple) {
		this.candidateCouple = candidateCouple;
	}
	
	public CandidateCouple getCandidateCouple() {
		return candidateCouple;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getTotal() {
		return total;
	}
}
