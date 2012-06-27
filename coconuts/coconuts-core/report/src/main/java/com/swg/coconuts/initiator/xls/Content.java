package com.swg.coconuts.initiator.xls;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import com.swg.coconuts.initiator.param.CellParam;

public class Content implements Serializable{
	private static final long serialVersionUID = 6046443814327462925L;
	
	private Header header;
	private Map<Column, CellParam<? extends Serializable>> parameters; 
	
	public Content() {
		parameters=new Hashtable<Column, CellParam<? extends Serializable>>();
	}
	
	public void putData(Column column,CellParam<?> param){
		parameters.put(column, param);
	}
	
	public CellParam<?> getExternal(Column column){
		return parameters.get(column);
	}
	
	public void setHeader(Header header) {
		this.header = header;
	}
	
	public Header getHeader() {
		return header;
	}
	
	
}
