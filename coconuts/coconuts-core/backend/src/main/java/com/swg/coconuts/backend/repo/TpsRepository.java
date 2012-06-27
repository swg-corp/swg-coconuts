package com.swg.coconuts.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Tps;

public interface TpsRepository extends JpaRepository<Tps, Integer>,JpaSpecificationExecutor<Tps>{
	
	List<Tps> findByKelurahan(Kelurahan kelurahan);
	Tps findByNomor(Integer nomor);
	Tps findByNomorAndKelurahanName(Integer nomor,String name);
	Tps findByNomorAndKelurahanCode(Integer nomor,String code);

}
