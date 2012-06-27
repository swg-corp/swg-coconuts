package com.swg.coconuts.backend.init.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.init.AreaTemplateMapper;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.initiator.xls.Content;

@Component
public class KabupatenTemplateMapper extends AreaTemplateMapper<Kabupaten> {

	@Autowired
	private KabupatenRepository kabupatenRepository;
	
	public KabupatenTemplateMapper() {
		setMappedClass(Kabupaten.class);
	}
	
	@Override
	public Kabupaten getTarget(Content content) {
		Kabupaten kabupaten=create(content);
		Kabupaten toSave=kabupatenRepository.findByCode(kabupaten.getCode());
		if(toSave==null)
			return kabupatenRepository.save(kabupaten);
		else
			return toSave;
		
	}
	
	@Override
	public Class<Kabupaten> getTargetType() {
		return Kabupaten.class;
	}

}
