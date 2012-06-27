package com.swg.coconuts.backend.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.backend.init.mapper.KabupatenTemplateMapper;
import com.swg.coconuts.backend.init.mapper.KecamatanTemplateMapper;
import com.swg.coconuts.backend.init.mapper.KelurahanTemplateMapper;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.backend.repo.ProvinsiRepository;
import com.swg.coconuts.initiator.Template;
import com.swg.coconuts.initiator.TemplateMapper;
import com.swg.coconuts.initiator.xls.Column;
import com.swg.coconuts.initiator.xls.Content;
import com.swg.coconuts.initiator.xls.Header;

@Component
public class AreaTemplate extends Template implements InitializingBean{
	
	private static final Header header;
	
	static{
		header=new Header();
		header.putColumn(new Column("Nama Pemantau", 1));
		header.putColumn(new Column("No Seluler", 2));
		header.putColumn(new Column("Kode Kelurahan", 3));
		header.putColumn(new Column("Nama Kelurahan", 4));
		header.putColumn(new Column("Kode Kecamatan", 5));
		header.putColumn(new Column("Nama Kecamatan", 6));
		header.putColumn(new Column("Kode Kabupaten", 7));
		header.putColumn(new Column("Nama Kabupaten", 8));
		
	}

	@Autowired
	private KabupatenTemplateMapper kabupatenTemplateMapper;
	@Autowired
	private KelurahanTemplateMapper kelurahanTemplateMapper;
	@Autowired
	private KecamatanTemplateMapper kecamatanTemplateMapper;
	
	@Autowired
	private KabupatenRepository repository;
	
	@Autowired
	private ProvinsiRepository provinsiRepository;
	
	public AreaTemplate() {
		super(header);
	}

	@Override
	public void configureColumnMap() {
		addColumn(new Column("Nama Pemantau", 1)); 
		addColumn(new Column("No Seluler", 2));    
		addColumn(new Column("Kode Kelurahan", 3));
		addColumn(new Column("Nama Kelurahan", 4));
		addColumn(new Column("Kode Kecamatan", 5));
		addColumn(new Column("Nama Kecamatan", 6));
		addColumn(new Column("Kode Kabupaten", 7));
		addColumn(new Column("Nama Kabupaten", 8));
	}
	
	@Override
	public void doMap(Content content) {
		Kelurahan kelurahan=kelurahanTemplateMapper.getTarget(content);
		Kecamatan kecamatan=kecamatanTemplateMapper.getTarget(content);
		Kabupaten kabupaten=kabupatenTemplateMapper.getTarget(content);
		kecamatan.addVillage(kelurahan);
		kabupaten.addDistrict(kecamatan);
		Kabupaten updated=repository.saveAndFlush(kabupaten);
		List<Provinsi> provinsis=provinsiRepository.findAll();
		if(!provinsis.isEmpty()){
			Provinsi provinsi=provinsis.get(0);
			provinsi.addCity(updated);
			provinsiRepository.saveAndFlush(provinsi);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<TemplateMapper<?>> templateMappers=new ArrayList<TemplateMapper<?>>();
		templateMappers.add(kabupatenTemplateMapper);
		templateMappers.add(kecamatanTemplateMapper);
		templateMappers.add(kelurahanTemplateMapper);
		setTemplateMappers(templateMappers);
	}

}
