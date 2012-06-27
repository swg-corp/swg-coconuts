package com.swg.coconuts.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;

public interface KelurahanRepository extends JpaRepository<Kelurahan, Integer>,JpaSpecificationExecutor<Kelurahan>{

	public Kelurahan findByCode(String code);
	public Kelurahan findByName(String name);
	public List<Kelurahan> findByKecamatan(Kecamatan kecamatan);
}
