package com.swg.coconuts.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.domain.VoteCouple;

public interface VoteCoupleRepository extends JpaRepository<VoteCouple, Integer>,JpaSpecificationExecutor<VoteCouple>{

	VoteCouple findByCandidateCouple(CandidateCouple candidateCouple);
}
