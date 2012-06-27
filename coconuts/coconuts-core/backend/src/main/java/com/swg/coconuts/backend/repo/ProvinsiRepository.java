package com.swg.coconuts.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.Provinsi;

public interface ProvinsiRepository extends JpaRepository<Provinsi, Integer>,JpaSpecificationExecutor<Provinsi>{

	Provinsi findByName(String name);
}
