package com.swg.coconuts.backend.init.mapper;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.VoteSender;
import com.swg.coconuts.initiator.TemplateMapper;
import com.swg.coconuts.initiator.param.CellParam;
import com.swg.coconuts.initiator.xls.Column;
import com.swg.coconuts.initiator.xls.Content;

@Component
public class VoteSenderTemplateMapper implements TemplateMapper<VoteSender>{

	private final Logger logger=Logger.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public VoteSender getTarget(Content content) {
		VoteSender voteSender=new VoteSender();
		Map<Integer, Column> columnMap=content.getHeader().getColumns();
		for(int i=0;i<columnMap.size();i++){
			Column source=columnMap.get(i);
			if(source.getName().contains("Pemantau")){
				CellParam<String> nameParam=(CellParam<String>) content.getExternal(source);
				if(!nameParam.isProceed()){
					voteSender.setName(nameParam.getValue());
					nameParam.setProceed(true);
					logger.info(voteSender.getName());
				}
			}else if(source.getName().contains("Seluler")){
				CellParam<String> nameParam=(CellParam<String>) content.getExternal(source);
				if(!nameParam.isProceed()){
					voteSender.setCellularNumber(nameParam.getValue());
					nameParam.setProceed(true);
					logger.info(voteSender.getCellularNumber());
				}
			}
		}
		return voteSender;
	}

	@Override
	public Class<VoteSender> getTargetType() {
		return VoteSender.class;
	}

}
