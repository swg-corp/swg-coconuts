package com.swg.coconuts.backend.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class Vote implements Serializable{
	private static final long serialVersionUID = 2189123688087729289L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Version
	private Timestamp version;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	@Embedded
	private VoteTps voteTps;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getVersion(){
		return version;
	}
	
	public void setVoteTps(VoteTps voteTps) {
		this.voteTps = voteTps;
	}
	
	public VoteTps getVoteTps() {
		return voteTps;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

}
