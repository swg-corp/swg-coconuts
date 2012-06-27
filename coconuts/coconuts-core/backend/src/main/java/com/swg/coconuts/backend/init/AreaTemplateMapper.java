package com.swg.coconuts.backend.init;

import java.util.Map;

import org.apache.log4j.Logger;

import com.swg.coconuts.initiator.TemplateMapper;
import com.swg.coconuts.initiator.param.Area;
import com.swg.coconuts.initiator.param.CellParam;
import com.swg.coconuts.initiator.xls.Column;
import com.swg.coconuts.initiator.xls.Content;

public abstract class AreaTemplateMapper<T extends Area> implements TemplateMapper<T> {
	
	protected final Logger logger=Logger.getLogger(getClass());
	
	private Class<T> mappedClass;
	
	public void setMappedClass(Class<T> mappedClass) {
		this.mappedClass = mappedClass;
	}

	@SuppressWarnings("unchecked")
	protected T create(Content content){
		T area=null;
		try{
			area=mappedClass.newInstance();
		}catch(Throwable t){
			logger.error("failed to create instance of: "+mappedClass.getSimpleName()+" object");
		}
		Map<Integer, Column> columnMap=content.getHeader().getColumns();
		for(int i=0;i<columnMap.size();i++){
			Column source=columnMap.get(i);
			if(source.getName().contains("Nama "+mappedClass.getSimpleName())){
				CellParam<String> nameParam=(CellParam<String>) content.getExternal(source);
				if(!nameParam.isProceed()){
					area.setName(nameParam.getValue());
					nameParam.setProceed(true);
					logger.info(area.getName());
				}
			}else if(source.getName().contains("Kode "+mappedClass.getSimpleName())){
				CellParam<String> nameParam=(CellParam<String>) content.getExternal(source);
				if(!nameParam.isProceed()){
					area.setCode(nameParam.getValue());
					nameParam.setProceed(true);
					logger.info(area.getCode());
				}
			}
		}
		return area;
	}
}
