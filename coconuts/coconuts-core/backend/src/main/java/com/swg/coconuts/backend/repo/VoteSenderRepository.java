package com.swg.coconuts.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.swg.coconuts.backend.domain.VoteSender;

public interface VoteSenderRepository extends JpaRepository<VoteSender, Integer>, JpaSpecificationExecutor<VoteSender> {
	
	public VoteSender findByCellularNumber(String cellularNumber);
	public VoteSender findByNameAndCellularNumber(String name,String cellularNumber);
	public VoteSender findByName(String name);
	public List<VoteSender> findByCellularNumberLike(String cellularNumber);

}
