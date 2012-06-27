package com.swg.coconuts.initiator.param;

import java.io.Serializable;

/**
 * Interface ini mewakili sebuah object yang mengabstrasikan suatu area/wilayah geografis
 * @author satriaprayoga
 *
 */
public interface Area extends Serializable {

	public void setName(String name);
	public String getName();
	public void setCode(String code);
	public String getCode();
	public Area getParent();
	
	public static class Default implements Area{
		private static final long serialVersionUID = 1L;
		
		private String name;
		private String code;
		
		public Default(String name, String code) {
			this.name = name;
			this.code = code;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getCode() {
			return code;
		}
		
		@Override
		public Area getParent() {
			throw new IllegalStateException("No Parent for default area");
			
		}

		@Override
		public void setName(String name) {
			this.name=name;
		}

		@Override
		public void setCode(String code) {
			this.code=code;
		}
		
	}
}
