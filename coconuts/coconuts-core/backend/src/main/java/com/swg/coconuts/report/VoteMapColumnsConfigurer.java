package com.swg.coconuts.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.initiator.report.ColumnConfigurer;

@Component
public class VoteMapColumnsConfigurer implements ColumnConfigurer{

	@Autowired
	private CandidateCoupleRepository coupleRepository;
	
	@Override
	public List<String> getColumnsTitle() {
		List<String> columnsTitle=new ArrayList<String>();
		List<CandidateCouple> couples=coupleRepository.findAll();
		columnsTitle.add("Abstain");
		for(CandidateCouple couple:couples){
			columnsTitle.add(couple.getNickName());
		
		}
		columnsTitle.add("Tidak Sah");
		return columnsTitle;
	}

}
