package com.swg.coconuts.messaging.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.swg.coconuts.gateway.IOutgoingMessage;

@Entity
public class OutgoingMessage implements Serializable, IOutgoingMessage{

	private static final long serialVersionUID = 8354760871264674939L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String recipient;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@Enumerated
	private SendStatus sendStatus;
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public Integer getId() {
		return id;
	}
	@Override
	public String getRecipient() {
		return recipient;
	}
	@Override
	public void setRecipient(String recipient) {
		this.recipient = recipient;
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
	public Date getSentDate() {
		return sentDate;
	}
	@Override
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	@Override
	public Date getCreateDate() {
		return createDate;
	}
	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setSendStatus(SendStatus sendStatus) {
		this.sendStatus = sendStatus;
	}
	@Override
	public SendStatus getSendStatus() {
		return sendStatus;
	}
	

}
