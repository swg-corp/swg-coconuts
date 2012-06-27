package com.swg.coconuts.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.CandidateCouple;

public interface CandidateCoupleRepository extends JpaRepository<CandidateCouple, Integer>,JpaSpecificationExecutor<CandidateCouple>{

	public CandidateCouple findByNickName(String nickName);
	public CandidateCouple findByNomorUrut(Integer nomorUrut);
}
