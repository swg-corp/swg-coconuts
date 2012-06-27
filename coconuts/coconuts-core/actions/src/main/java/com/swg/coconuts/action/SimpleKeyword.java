package com.swg.coconuts.action;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Implementasi dasar dari interface {@link Keyword}.
 * Keyword ini adalah identitas dari masing-masing action
 * yang dapat didispatch berdasarkan isi pesan masuk.
 * Keyword ini menggunakan mekanisme matching menggunakan
 * regular expression.
 * Sebagai catatan, character valid yang dapat menyusun simple keyword ini
 * adalah alphanumerik (regex : ^[a-zA-Z0-9]{1,}+)
 * 
 * @author zakyalvan
 */
public final class SimpleKeyword implements Keyword {
	private static final long serialVersionUID = 5593759927906392100L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * Precompiled regex pattern yang dipake untuk validasi nilai keyword yang diberikan.
	 */
	private final Pattern validValuePattern = Pattern.compile("^[a-zA-Z0-9]{1,}+");
	
	private final String value;
	private final Set<String> patterns = new HashSet<String>();
	
	/**
	 * Compiled pattern dari regular expression.
	 * Baca dokumentasi terkait dari object ini untuk jelasnya.
	 */
	private final Set<Pattern> regexPatterns = new HashSet<Pattern>();
	
	private boolean removeInvalidCharacter = true;
	private Set<Character> invalidCharactersToRemove = 
		new HashSet<Character>(Arrays.<Character> asList(' ', '.', ',', ':', ';'));
	
	public SimpleKeyword(String value) {
		if(value != null && !validValuePattern.matcher(value.trim()).matches())
			throw new IllegalArgumentException("Parameter keyword value null atau tidak valid. Format valid adalah : " + validValuePattern.pattern());
		
		this.value = value.toUpperCase();
		
		// Tambahin keyword value sebagai default pattern.
		addPattern(this.value);
	}
	public SimpleKeyword(String value, Set<String> patterns) {
		this(value);
		this.patterns.addAll(patterns);
	}
	
	public boolean isRemoveInvalidCharacter() {
		return removeInvalidCharacter;
	}
	public void setRemoveInvalidCharacter(boolean removeInvalidCharacter) {
		this.removeInvalidCharacter = removeInvalidCharacter;
	}
	
	public String getValue() {
		return value;
	}
	public Set<String> getPatterns() {
		return new HashSet<String>(patterns);
	}
	
	@Override
	public boolean hasPattern(String pattern) {
		return patterns.contains(pattern);
	}
	public boolean addPattern(String pattern) {
		return addPattern(pattern, true);
	}
	public boolean addPattern(String pattern, boolean enforceIngnoreCase) {
		if(patterns.add(pattern)) {
			// Langsung bikin compiled version dari pattern yang diberikan (Object {@link Pattern})
			if(enforceIngnoreCase) {
				Pattern regexPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
				regexPatterns.add(regexPattern);
			}
			else {
				Pattern regexPattern = Pattern.compile(pattern);
				regexPatterns.add(regexPattern);
			}
			return true;
		}
		return false;
	}
	public boolean removePattern(String pattern) {
		pattern = pattern.trim();
		
		// Ensure yang diremove bukan keyword value.
		if(pattern.equalsIgnoreCase(value))
			throw new IllegalArgumentException("Tidak dapat meremove pattern yang sama dengan keyword value ("+value+")");
		
		// Remove juga precompiled version dari pattern.
		if(patterns.remove(pattern)) {
			for(Pattern regexPattern : regexPatterns) {
				if(regexPattern.pattern().equalsIgnoreCase(pattern))
					regexPatterns.remove(pattern);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * TODO perbaiki strategy matching ini biar bisa handle advance matching use case.
	 */
	@Override
	public boolean tryMatching(String other) {
		/**
		 *  Trim terdahulu input parameter (Ingat, hanya valid character yang mungkin
		 *  untuk simple keyword adalah alphanumerik). Untuk perbaikan mungkin perlu juga hilangin
		 *  invalid character (misalnya tanda baca titik, koma dll.)
		 */
		other = other.trim();
		
		/**
		 * Jika dienable, remove invalid character.
		 */
		if(removeInvalidCharacter) {
			other = removeInvalidCharacter(other);
		}
		
		for(Pattern pattern : regexPatterns)  {
			if(pattern.matcher(other).matches())
				return true;
		}
		return false;
	}
	private String removeInvalidCharacter(String value) {
		logger.debug("Remove invalid character(s).");
		
		StringBuilder regexBuilder = new StringBuilder();
		regexBuilder.append("[");
		for(Character character : invalidCharactersToRemove) {
			regexBuilder.append(character.toString());
		}
		regexBuilder.append("]{1,}+");
		
		logger.debug("Remove invalid regex pattern : " + regexBuilder.toString());
		
		value = value.replaceAll(regexBuilder.toString(), "");
		
		logger.debug("Invalid character removed : " + value);
		return value;
	}
	
	public String toString() {
		return value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleKeyword other = (SimpleKeyword) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equalsIgnoreCase(other.value))
			return false;
		return true;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Object keyword tidak dapat di-clone.");
	}
}
