package com.swg.coconuts.messaging;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.domain.Vote;
import com.swg.coconuts.backend.domain.VoteTps;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.backend.repo.TpsRepository;
import com.swg.coconuts.backend.repo.VoteRepository;

@Component
public class VoteCounterServiceImpl implements VoteCounterService {

	private final Logger logger=Logger.getLogger(getClass());
	
	@Autowired
	private TpsRepository tpsRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private CandidateCoupleRepository coupleRepository;
	
	@Override
	public void update(Map<String, Integer> dist,Integer kode,String kelurahan) {
		logger.info("update suara di tps: "+kelurahan);
		VoteTps voteTps=new VoteTps();
		Tps dTps=tpsRepository.findByNomorAndKelurahanName(kode, kelurahan);
		voteTps.setNomorTps(dTps.getNomor());
		voteTps.setNamaKelurahan(dTps.getKelurahan().getName());
		Map<CandidateCouple, Integer> map2=new HashMap<CandidateCouple, Integer>();
		for(String s:dist.keySet()){
			if(s.equalsIgnoreCase("invalid"))
				voteTps.setInvalid(dist.get(s));
			else if(s.equalsIgnoreCase("abstain")){
				voteTps.setAbstain(dist.get(s));
			}else{
				CandidateCouple couple=new CandidateCouple();
				couple.setNickName(s);
				map2.put(couple, dist.get(s));
			}
		}
		voteTps.setDistribution(map2);
		Vote vote=null;
		Vote tosave=voteRepository.findByVoteTpsNomorTps(voteTps.getNomorTps());
		if(tosave!=null){
			logger.info("update vote");
			tosave.setVoteTps(voteTps);
			tosave.setUpdateTime(Calendar.getInstance().getTime());
			vote=voteRepository.saveAndFlush(tosave);
		}else{
			logger.info("create vote");
			tosave=new Vote();
			tosave.setVoteTps(voteTps);
			tosave.setUpdateTime(Calendar.getInstance().getTime());
			vote=voteRepository.save(tosave);
		}
		
		logger.info("hasil update: "+vote.getVoteTps().getDistribution());
	//	updateCandidate();
	}

	@Scheduled(fixedDelay=35000)
	public void updateCandidate(){
		logger.info("updating vote result for candidate");
		List<CandidateCouple> candidateCouples=coupleRepository.findAll();
		for(CandidateCouple c:candidateCouples){
			c.getVote().setTotal(0);
			coupleRepository.save(c);
		}
		List<Vote> votes=voteRepository.findAll();
		for(Vote vote:votes){
			Map<CandidateCouple, Integer> dist=vote.getVoteTps().getDistribution();
			doUpdateCandidate(dist);
		}
	}
	
	void doUpdateCandidate(Map<CandidateCouple, Integer> dist){
		for(CandidateCouple couple:dist.keySet()){
			int total=0;
			CandidateCouple toUpdate=ensure(couple);
			total=dist.get(couple)+toUpdate.getVote().getTotal();
			logger.info("update candidate: "+toUpdate.getNickName()+", suara: "+total);
			toUpdate.getVote().setTotal(total);
			coupleRepository.save(toUpdate);
		}
	}
	
	CandidateCouple ensure(CandidateCouple couple){
		CandidateCouple toUpdate=coupleRepository.findByNickName(couple.getNickName());
		return toUpdate;
	}

}
