package com.swg.coconuts.report.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.domain.Vote;
import com.swg.coconuts.backend.repo.VoteRepository;
import com.swg.coconuts.initiator.report.AbstractReportService;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.AreaReportService.TpsReportService;

@Component
public class TpsReportServiceImpl extends AbstractReportService<Tps> implements TpsReportService{

	@Autowired
	private VoteRepository repository;
	
	public TpsReportServiceImpl() {
		super(Tps.class);
	}

	@Override
	public VoteMap getVoteFor(Tps area) {
		Vote vote=repository.findByVoteTpsNomorTps(area.getNomor());
		VoteMap map=null;
		if(vote!=null){
			Map<CandidateCouple, Integer> dist=vote.getVoteTps().getDistribution();
			map=new VoteMap();
			map.getVoteResult().put("Abstain", vote.getVoteTps().getAbstain());
			map.getVoteResult().put("Tidak Sah", vote.getVoteTps().getInvalid());
			map.setLastUpdate(vote.getUpdateTime());
			map.setAreaId(area.getKelurahan().getName()+" "+area.getNomor());
			for(CandidateCouple couple:dist.keySet()){
				map.getVoteResult().put(couple.getNickName(), dist.get(couple));
			}
		}
		return map;
	}

}
