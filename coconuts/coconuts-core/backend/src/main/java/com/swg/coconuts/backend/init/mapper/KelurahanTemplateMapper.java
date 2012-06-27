package com.swg.coconuts.backend.init.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.init.AreaTemplateMapper;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.initiator.xls.Content;

@Component
public class KelurahanTemplateMapper extends AreaTemplateMapper<Kelurahan>{

	@Autowired
	private KelurahanRepository repository;
	
	public KelurahanTemplateMapper() {
		setMappedClass(Kelurahan.class);
	}
	
	@Override
	public Kelurahan getTarget(Content content) {
		Kelurahan kelurahan=create(content);
		Kelurahan toSave=repository.findByCode(kelurahan.getCode());
		if(toSave==null)
			return repository.save(kelurahan);
		else
			return toSave;
	}
	
	@Override
	public Class<Kelurahan> getTargetType() {
		return Kelurahan.class;
	}

}
