package com.swg.coconuts.action;

import java.io.Serializable;
import java.util.Set;

/**
 * Kontrak dasar untuk action keyword.
 * Keyword ini merupakan identity untuk masing-masing action.
 * Untuk masing-masing keyword, keyword value merupakan pattern
 * utama yang akan dibandingkan dengan string lain, apakah sesuai atau tidak.
 * 
 * @see {@link SimpleKeyword}
 * @author zakyalvan
 */
public interface Keyword extends Serializable {
	/**
	 * Nilai dari keyword ini.
	 * 
	 * @return String
	 */
	String getValue();
	
	/**
	 * Retrieve pattern yang digunakan untuk {@link #compareMatch(String)} dengan keyword ini.
	 * Minimal isi dari koleksi pattern ini adalah nilai dari object keyword
	 * ini sendiri.
	 * 
	 * @return Set<String>
	 */
	Set<String> getPatterns();
	
	/**
	 * Query apakan object Keyword berkaitan memiliki pattern tertentu.
	 * 
	 * @param pattern
	 * @return boolean
	 */
	boolean hasPattern(String pattern);
	
	/**
	 * Tambahin pattern ke koleksi pattern.
	 * 
	 * @param String pattern
	 * @return boolean (Nandain apakah nambah pattern berhasil atau tidak.)
	 */
	boolean addPattern(String pattern);
	
	/**
	 * Remove pattern dari koleksi pattern.
	 * Hanya alternative pattern yang bisa diremove,
	 * dan jika pattern utama (atau nilai keyword) yang akan diremove,
	 * throw {@link IllegalArgumentException}.
	 * 
	 * @param pattern
	 * @return boolean
	 */
	boolean removePattern(String pattern);
	
	/**
	 * Bandingkan dengan string dari input parameter apakah sesuai dengan
	 * nilai dari keyword ini (atau alternativ pattern jika ada).
	 * 
	 * @param other
	 * @return boolean
	 */
	boolean tryMatching(String other);
}

