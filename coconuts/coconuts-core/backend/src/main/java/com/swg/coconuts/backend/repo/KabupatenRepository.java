package com.swg.coconuts.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Provinsi;

public interface KabupatenRepository extends JpaRepository<Kabupaten, Integer>,JpaSpecificationExecutor<Kabupaten>{

	public Kabupaten findByName(String name);
	
	public Kabupaten findByCode(String code);
	
	public List<Kabupaten> findByProvinsi(Provinsi provinsi);
}
