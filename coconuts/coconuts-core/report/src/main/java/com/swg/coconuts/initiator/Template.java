package com.swg.coconuts.initiator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.swg.coconuts.initiator.xls.Column;
import com.swg.coconuts.initiator.xls.Content;
import com.swg.coconuts.initiator.xls.Header;

public abstract class Template implements TemplateMapperAware{
	
	protected final Logger logger=Logger.getLogger(getClass());
	
	private final Header header;
	
	private final Map<String, Column> columnMap=new HashMap<String, Column>();
	
	private List<TemplateMapper<?>> templateMappers;
	
	public Template(Header header) {
		if(header==null || header.getColumns().isEmpty()){
			logger.error("header cannot be null or has empty column");
			throw new IllegalArgumentException("header cannot be null or has empty column");
		}
		logger.debug("#####configuring column map######");
		configureColumnMap();
		logger.debug("#####validating column map#######");
		validateHeader(header);
		this.header=header;
	}
	
	public void validateHeader(Header header){
		Set<String> columnNames=header.getColumnsName();
		if(columnNames.size()!=columnMap.size()){
			logger.error("column size of declared format not match with column size of required format");
			throw new IllegalArgumentException("column size of declared format not match with column size of required format");
		}
		Set<Column> noMatchedColumn=new HashSet<Column>(); 
		for(String c:columnNames){
			logger.debug("try matching: "+c);
			Column col=columnMap.get(c);
			if(col==null){
				logger.error("no column matched for: "+c);
				noMatchedColumn.add(col);
				throw new IllegalArgumentException("column not macthed");
			}
			logger.debug("column with name: "+c+" found");
		}
		
		if(noMatchedColumn.size()>0){
			logger.error("there are "+noMatchedColumn.size()+" not matched columns");
			throw new IllegalArgumentException("there are "+noMatchedColumn.size()+" not matched columns");
		}
		
		
	}
	
	public abstract void configureColumnMap();
	
	public abstract void doMap(Content content);
	
	protected void addColumn(Column column){
		columnMap.put(column.getName(), column);
	}
	
	public Header getHeader() {
		return header;
	}
	
	public Set<String> getColumnNames(){
		return new HashSet<String>(columnMap.keySet());
	}
	
	public Column getColumnForName(String name){
		return columnMap.get(name);
	}
	
	@Override
	public void setTemplateMappers(List<TemplateMapper<?>> templateMappers) {
		this.templateMappers = templateMappers;
	}
	
	public List<TemplateMapper<?>> getTemplateMappers() {
		return templateMappers;
	}
	
	public TemplateMapper<?> getTemplateMapperFor(Class<?> clazz){
		TemplateMapper<?> found=null;
		for(TemplateMapper<?> mapper:templateMappers){
			if(mapper.getTargetType()==clazz){
				found=mapper;
				break;
			}else{
				throw new IllegalStateException("no template mapper for "+clazz.getSimpleName()+" type");
			}
		}
		return found;
	}

}
