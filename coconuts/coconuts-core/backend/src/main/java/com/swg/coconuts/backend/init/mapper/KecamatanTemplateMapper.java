package com.swg.coconuts.backend.init.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.init.AreaTemplateMapper;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.initiator.xls.Content;

@Component
public class KecamatanTemplateMapper extends AreaTemplateMapper<Kecamatan>{

	@Autowired
	private KecamatanRepository repository;
	
	public KecamatanTemplateMapper() {
		setMappedClass(Kecamatan.class);
	}
	
	@Override
	public Kecamatan getTarget(Content content) {
		Kecamatan kecamatan=create(content);
		Kecamatan toSave=repository.findByCode(kecamatan.getCode());
		if(toSave==null)
			return repository.save(kecamatan);
		else
			return toSave;
	}
	
	@Override
	public Class<Kecamatan> getTargetType() {
		return Kecamatan.class;
	}

}
