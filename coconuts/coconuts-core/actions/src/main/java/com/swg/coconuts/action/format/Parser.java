package com.swg.coconuts.action.format;

import java.util.Collection;
import java.util.Set;

import com.swg.coconuts.action.param.Parameter;

/**
 * Kontrak untuk parser. Parser ini adalah object yang bertugas memparsing
 * pesan sms sebelum action dieksekusi.
 * 
 * @author zakyalvan
 */
public interface Parser {
	public static final String DEFAULT_DELIMITER = "\\s+?";
	
	/**
	 * Parsing payload. Proses parsing dihint oleh {@link Format}, 
	 * yaitu format valid yang dimengerti oleh satu action tertentu.
	 * 
	 * @param payload
	 * @param format
	 * @return
	 * @throws ParsingException
	 */
	Result parse(String payload, Format format) throws ParsingException;
	
	/**
	 * Kelas object sederhana yang container hasil parsing content pesan masuk.
	 * 
	 * @author zakyalvan
	 */
	public static final class Result {
		private final String keyword;
		private final Set<? extends Parameter<?>> parameters;
		
		public Result(String keyword, Set<? extends Parameter<?>> parameters) {
			this.keyword = keyword;
			this.parameters = parameters;
		}
		
		public String getKeyword() {
			return keyword;
		}
		public boolean hasParameters() {
			return parameters.size() > 0;
		}
		public Integer countParameters() {
			return parameters.size();
		}
		public Collection<? extends Parameter<?>> getParameters() {
			return parameters;
		}
		@Override
		public String toString() {
			return "Result [keyword=" + keyword + ", parameters=" + parameters
					+ "]";
		}
	}
}
