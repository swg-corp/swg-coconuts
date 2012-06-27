package com.swg.coconuts.messaging.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.action.AbstractAction;
import com.swg.coconuts.action.ActionException;
import com.swg.coconuts.action.Keyword;
import com.swg.coconuts.action.PayloadType;
import com.swg.coconuts.action.SimpleKeyword;
import com.swg.coconuts.action.format.Format;
import com.swg.coconuts.action.format.SimpleFormat;
import com.swg.coconuts.action.param.MapParameter;
import com.swg.coconuts.action.param.NumberParameter;
import com.swg.coconuts.action.param.Parameter;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.backend.repo.TpsRepository;
import com.swg.coconuts.messaging.VoteCounterService;

@Component
public class InputVoteAction extends AbstractAction{
	
	private static final Keyword KEYWORD=new SimpleKeyword("suara");
	private static final Format FORMAT=new SimpleFormat("{keyword}{parameter:map:suaramap}", PayloadType.MAPPED);
	
	private final String NO_TPS="value2";
	
	@Autowired
	private CandidateCoupleRepository repository;
	@Autowired
	private TpsRepository tpsRepository;
	@Autowired
	private VoteCounterService voteCounterService;
	
	public InputVoteAction() {
		super(KEYWORD, FORMAT);
	}

	@Override
	protected void configureParameterTypeMap(
			Map<String, Class<? extends Parameter<?>>> parametersTypeMap2) {
		parametersTypeMap2.put("suaramap", MapParameter.class);
		
	}

	@Override
	public void execute() throws ActionException {
		if(getParameter("suaramap")!=null){
			logger.info("+++++++++++ Start of Execution Input Vote Action +++++++++++");
			MapParameter parameter=(MapParameter) getParameter("suaramap");
			logger.info("yang dikirim user:");
			Map<String, Parameter<?>> map=parameter.getValue();
			Integer noTps=(Integer) map.get(NO_TPS).getValue();
			Tps tps=tpsRepository.findByNomor(noTps);
			if(tps!=null){
				String namaKelurahan=tps.getKelurahan().getName();
				logger.info("Nama kelurahan: "+namaKelurahan);
				logger.info("No TPS: "+noTps);
				Map<String, Integer> dist=new HashMap<String, Integer>();
				int totalParam=map.keySet().size();
				int tsAndAbs=4;
				int distSize=totalParam-tsAndAbs;
				Integer abstain=(Integer) map.get("value"+(totalParam-2)).getValue();
				Integer invalid=(Integer)map.get("value"+totalParam).getValue();
				for(int i=3;i<=distSize;i++){
					if(i%2!=0){
						NumberParameter nomor=(NumberParameter) map.get("value"+i);
						NumberParameter jumlah=(NumberParameter) map.get("value"+(i+1));
						String candidateName=repository.findByNomorUrut((Integer) nomor.getValue()).getNickName();
						logger.info("suara untuk: "+candidateName+", jumlah suara: "+jumlah.getValue());
						dist.put(candidateName, (Integer) jumlah.getValue());
					}else{
						
					}
				}
				logger.info("suara abstain: "+abstain);
				logger.info("suara tidak sah: "+invalid);
				dist.put("abstain", abstain);
				dist.put("invalid", invalid);
				logger.info(dist);
				voteCounterService.update(dist, noTps, namaKelurahan);
			}
			logger.info("+++++++++++ End of Execution Input Vote Action +++++++++++");
		}
		
	}

}
