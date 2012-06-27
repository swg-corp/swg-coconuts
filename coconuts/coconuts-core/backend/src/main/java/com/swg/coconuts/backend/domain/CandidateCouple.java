package com.swg.coconuts.backend.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

@Entity
public class CandidateCouple implements Serializable{

	private static final long serialVersionUID = -8120311197697239378L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String nickName;
	private Integer nomorUrut;
	
	@OneToOne(mappedBy="candidateCouple", cascade=CascadeType.ALL)
	private VoteCouple vote;
	
	public static enum Position{
		HEAD,VICE
	}
	@ElementCollection(targetClass=Candidate.class, fetch=FetchType.EAGER)
	private Map<Position, Candidate> candidateMap=new HashMap<CandidateCouple.Position, Candidate>();
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Map<Position, Candidate> getCandidateMap() {
		return candidateMap;
	}
	public void setCandidateMap(Map<Position, Candidate> candidateMap) {
		this.candidateMap = candidateMap;
	}
	
	public Candidate getHead(){
		return candidateMap.get(Position.HEAD);
	}
	
	public void setHead(Candidate candidate){
		this.candidateMap.put(Position.HEAD, candidate);
	}
	
	public Candidate getVice(){
		return candidateMap.get(Position.VICE);
	}
	
	public void setVice(Candidate candidate){
		this.candidateMap.put(Position.VICE, candidate);
	}
	public VoteCouple getVote() {
		return vote;
	}
	public void setVote(VoteCouple vote) {
		this.vote = vote;
	}
	
	public void setNomorUrut(Integer nomorUrut) {
		this.nomorUrut = nomorUrut;
	}
	
	public Integer getNomorUrut() {
		return nomorUrut;
	}
	
	@PrePersist
	public void beforePersist(){
		VoteCouple couple=new VoteCouple();
		couple.setCandidateCouple(this);
		this.setVote(couple);
	}
	
}
