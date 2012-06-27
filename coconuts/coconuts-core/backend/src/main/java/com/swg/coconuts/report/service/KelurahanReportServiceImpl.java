package com.swg.coconuts.report.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.repo.TpsRepository;
import com.swg.coconuts.initiator.report.AbstractReportService;
import com.swg.coconuts.initiator.report.VoteMap;

@Component
public class KelurahanReportServiceImpl extends AbstractReportService<Kelurahan> implements KelurahanReportService{

	@Autowired
	private TpsRepository repository;
	
	@Autowired
	private TpsReportService tpsReportService;
	
	public KelurahanReportServiceImpl() {
		super(Kelurahan.class);
	}
	
	@Override
	public VoteMap getVoteFor(Kelurahan object) {
		if(object!=null){
			List<Tps> list=repository.findByKelurahan(object);
			VoteMap map=null;
			if(list!=null){
				map=new VoteMap();
				map.setAreaId(object.getName());
				Collection<VoteMap> maps=getChildVoteMap(list);
				//Map<String, Integer> resultMap=new HashMap<String, Integer>();
				for(VoteMap vm:maps){
					map.append(vm);
				}
				map.setLastUpdate(Calendar.getInstance().getTime());
			}
			
			return map;
		}else{
			return null;
		}
	}

	@Override
	public Collection<VoteMap> getChildVoteMap(List<?> object) {
		List<VoteMap> voteMaps=new ArrayList<VoteMap>();
		for(Object tps:object){
			if(!(tps instanceof Tps)){
				throw new IllegalArgumentException();
			}
			Tps t=(Tps)tps;
			VoteMap map=tpsReportService.getVoteFor(t);
			if(map!=null && !(map.getVoteResult().isEmpty()))
				voteMaps.add(map);
		}
		return voteMaps;
	}

}
