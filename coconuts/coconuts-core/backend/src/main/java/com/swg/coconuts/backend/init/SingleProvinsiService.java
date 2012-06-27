package com.swg.coconuts.backend.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.backend.repo.ProvinsiRepository;

@Component
public class SingleProvinsiService implements ProvinceService {

	@Autowired
	private ProvinsiRepository repository;
	
	@Autowired
	private KabupatenRepository kabupatenRepository;
	
	@Override
	public void save(Provinsi provinsi) {
		Provinsi saved=repository.save(provinsi);
		List<Kabupaten> kabupatens=kabupatenRepository.findAll();
		append(saved, kabupatens);
	}
	
	void append(Provinsi provinsi,List<Kabupaten> kabupatens){
		if(!kabupatens.isEmpty()){
			for(Kabupaten kabupaten:kabupatens){
				provinsi.addCity(kabupaten);
			}
		}
		repository.saveAndFlush(provinsi);
	}

	@Override
	public Provinsi findSingle() {
		List<Provinsi> provinsis=repository.findAll();
		if(provinsis.isEmpty()){
			return null;
		}
		return provinsis.get(0);
	}

}
