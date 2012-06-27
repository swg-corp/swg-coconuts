package com.swg.coconuts.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;

public interface KecamatanRepository extends JpaRepository<Kecamatan, Integer>,JpaSpecificationExecutor<Kecamatan>{

	public Kecamatan findByName(String name);
	public Kecamatan findByCode(String code);
	public List<Kecamatan> findByKabupaten(Kabupaten kabupaten);
}
