/**
 * 
 */
package com.swg.coconuts.gateway;

import java.util.Date;

/**
 * Interface ini mewakili pesan yang masuk ke system
 * 
 * @author satriaprayoga
 *
 */
public interface IIncomingMessage {

	public static enum ProcessStatus{
		NEW_MESSAGE,
		PROCESSING_MESSAGE,
		PROCESSED_MESSAGE,
		FAILED_MESSAGE,
		REPROCESS_MESSAGE
	}
	/**
	 * Flag status yang menandakan message baru, belum/harus diproses 
	 * oleh system.
	 */
	public static final Integer NEW_MESSAGE = 1;
	
	/**
	 * Flag status yang menunjukan inbound message bersangkutan sedang diproses.
	 */
	public static final Integer PROCESSING_MESSAGE = 2;
	
	/**
	 * Flag status yang menunjukan pesan sudah berhasil diproses, dan
	 * action yang terkait sudah berhasil didispatch.
	 */
	public static final Integer PROCESSED_MESSAGE = 3;
	
	/**
	 * Flag status yang nunjukin pesan gagal diproses, dengan alasan apapun.
	 */
	public static final Integer FAILED_MESSAGE = 4;
	
	/**
	 * Flag status yang harus diset oleh sytem pada saat 
	 * message yang bersangkutan harus diproses ulang.
	 * Contohnya use case adalah pada saat perbaikan format pesan.
	 */
	public static final Integer REPROCESS_MESSAGE = 5;
	
	
	Integer getId();

	void setId(Integer id);

	String getOrigin();

	void setOrigin(String origin);

	String getContent();

	void setContent(String content);

	Date getReceivedDate();

	void setReceivedDate(Date receivedDate);

	ProcessStatus getProcessStatus();
	void setProcessStatus(ProcessStatus processStatus);
	
	Date getVersion();

}
