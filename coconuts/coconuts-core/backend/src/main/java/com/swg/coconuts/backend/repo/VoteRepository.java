package com.swg.coconuts.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer>,JpaSpecificationExecutor<Vote>{

	List<Vote> findByVoteTpsNamaKelurahan(String namaKelurahan);
	
	Vote findByVoteTpsNomorTps(Integer nomorTps);
}
