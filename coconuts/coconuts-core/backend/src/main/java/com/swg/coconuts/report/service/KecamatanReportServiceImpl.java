package com.swg.coconuts.report.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.initiator.report.AbstractReportService;
import com.swg.coconuts.initiator.report.VoteMap;

@Component
public class KecamatanReportServiceImpl extends AbstractReportService<Kecamatan> implements KecamatanReportService{
	
	@Autowired
	private KelurahanRepository repository;
	@Autowired
	private KelurahanReportService reportService;
	
	public KecamatanReportServiceImpl() {
		super(Kecamatan.class);
	}
	
	@Override
	public VoteMap getVoteFor(Kecamatan object) {
		if(object!=null){
			List<Kelurahan> list=repository.findByKecamatan(object);
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
		for(Object kelurahan:object){
			if(!(kelurahan instanceof Kelurahan)){
				throw new IllegalArgumentException();
			}
			Kelurahan k=(Kelurahan)kelurahan;
			VoteMap map=reportService.getVoteFor(k);
			if(map!=null && !(map.getVoteResult().isEmpty())){
				voteMaps.add(map);
			}
		}
		return voteMaps;	
	}

}
