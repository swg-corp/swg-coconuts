package com.swg.coconuts.action;

import com.swg.coconuts.action.format.Format;


/**
 * Kontrak dasar untu aksi yang bisa didispatch menggunakan pesan sms
 * (dengan format tertentu). Setiap action memiliki keyword yang sehingga
 * pesan dengan format.
 * 
 * @author zakyalvan
 */
public interface Action extends ValidationAware, Parameterizable {
	Keyword getKeyword();
	
	/**
	 * Retrieve format content masuk yang bisa diterima atau dieksekusi oleh
	 * action ini. Masing-masing action memiliki satu format yang unik, tentu saja
	 * isi format ini tergantung dari parameter yang diperlukan untuk mengeksekusi
	 * underlying business service.
	 * 
	 * @return {@link Format}
	 */
	Format getFormatReceived();
	
	/**
	 * Enable/disable response untuk action ini secara keseluruhan.
	 * Jika didisable disini, response untuk jenis event tertentu
	 * diabaikan.
	 * 
	 * @param enabled
	 */
	void setResponseEnabled(boolean enabled);
	
	/**
	 * Apakah action ini dienable untuk memberikan response.
	 * Ini mengenable response secara keseluruhan dalam action ini 
	 * (untuk semua jenis event dalam konteks action ini).
	 * 
	 * @return boolean
	 */
	boolean isResponseEnabled();
	
	/**
	 * Enable response untuk jenis event tertentu.
	 * Jenis event dibedakan berdasarkan code yang menjadi parameter.
	 * 
	 * @param code
	 */
	void setResponseEnabledFor(int code);
	
	/**
	 * Apakah response dienable untuk event tertentu. Jenis event dibedakan
	 * berdasarkan kode yang diberikan.
	 * 
	 * @param code
	 * @return {@link Boolean}
	 */
	boolean isResponseEnabledFor(int code);
	
	void execute() throws ActionException;
	
	boolean isExecuted();
	
	PayloadType getPayloadType();
}
