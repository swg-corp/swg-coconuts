package com.swg.coconuts.initiator.xls;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Header implements Serializable{
	private static final long serialVersionUID = -7006907902809993436L;
	
	private Map<Integer, Column> columns;
	
	public Header() {
		columns=new Hashtable<Integer, Column>();
	}
	
	public void setColumns(Map<Integer, Column> columns) {
		this.columns = columns;
	}
	
	public Map<Integer, Column> getColumns() {
		return columns;
	}
	
	public void putColumn(Column column){
		columns.put(column.getNumber(), column);
	}
	
	public Integer columnCount(){
		return columns.size();
	}
	
	public Iterator<Column> getColumnIterator(){
		return columns.values().iterator();
	}
	
	public Set<String> getColumnsName(){
		Set<String> columnsName=new HashSet<String>();
		for(Column c:columns.values()){
			columnsName.add(c.getName());
		}
		return columnsName;
	}
	
	public Column getColumnForName(String columnName){
		Column found=null;
		for(Column column:columns.values()){
			if(column.getName().equalsIgnoreCase(columnName)){
				found=column;
			}
		}
		return found;
	}

}
