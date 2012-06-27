package com.swg.coconuts.gateway;

import java.util.Date;

/**
 * Inteface ini mewakili pesan keluar/pesan yang dikirim oleh system
 * @author satriaprayoga
 *
 */
public interface IOutgoingMessage {

	public static enum SendStatus{
		NEW_MESSAGE,
		SEND,
		SENT,
		NOT_SENT,
		FAILED
	}
	void setId(Integer id);

	Integer getId();

	String getRecipient();

	void setRecipient(String recipient);

	String getContent();

	void setContent(String content);

	Date getSentDate();

	void setSentDate(Date sentDate);

	Date getCreateDate();

	void setCreateDate(Date createDate);
	
	void setSendStatus(SendStatus sendStatus);
	
	SendStatus getSendStatus();

}