package com.swg.coconuts.backend.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.VoteSender;
import com.swg.coconuts.backend.init.mapper.VoteSenderTemplateMapper;
import com.swg.coconuts.backend.repo.VoteSenderRepository;
import com.swg.coconuts.initiator.Template;
import com.swg.coconuts.initiator.TemplateMapper;
import com.swg.coconuts.initiator.xls.Column;
import com.swg.coconuts.initiator.xls.Content;
import com.swg.coconuts.initiator.xls.Header;

@Component
public class VoteSenderTemplate extends Template implements InitializingBean {
	
	private static final Header header;
	
	static{
		header=new Header();
		header.putColumn(new Column("Nama Pemantau", 1));
		header.putColumn(new Column("No Seluler", 2));
		
	}
	
	@Autowired
	private VoteSenderTemplateMapper templateMapper;
	
	@Autowired
	private VoteSenderRepository repository;


	public VoteSenderTemplate() {
		super(header);
	}
	
	@Override
	public void configureColumnMap() {
		addColumn(new Column("Nama Pemantau", 1)); 
		addColumn(new Column("No Seluler", 2));    
		
	}

	@Override
	public void doMap(Content content) {
		VoteSender voteSender=templateMapper.getTarget(content);
		VoteSender toSave=repository.findByNameAndCellularNumber(voteSender.getName(), voteSender.getCellularNumber());
		if(toSave==null)
			repository.save(voteSender);
		
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		List<TemplateMapper<?>> templateMappers=new ArrayList<TemplateMapper<?>>();
		templateMappers.add(templateMapper);
		setTemplateMappers(templateMappers);
	}

	
}
