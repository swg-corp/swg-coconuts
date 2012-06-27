package com.swg.coconuts.messaging.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.swg.coconuts.gateway.IIncomingMessage;

@Entity
public class IncomingMessage implements Serializable, IIncomingMessage{

	private static final long serialVersionUID = 6202325215844746601L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String origin;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date receivedDate;
	@Enumerated
	private ProcessStatus processStatus;
	
	@Version
	private Timestamp version;
	
	public IncomingMessage() {
		processStatus=ProcessStatus.NEW_MESSAGE;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String getOrigin() {
		return origin;
	}
	@Override
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Override
	public String getContent() {
		return content;
	}
	@Override
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public Date getReceivedDate() {
		return receivedDate;
	}
	@Override
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	@Override
	public Date getVersion(){
		return version;
	}
	@Override
	public ProcessStatus getProcessStatus() {
		return processStatus;
	}
	@Override
	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus=processStatus;
	}

}
