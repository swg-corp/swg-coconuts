package com.swg.coconuts.report.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.initiator.report.AbstractReportService;
import com.swg.coconuts.initiator.report.VoteMap;

@Component
public class KabupatenReportServiceImpl extends AbstractReportService<Kabupaten> implements KabupatenReportService{

	@Autowired
	private KecamatanRepository repository;
	@Autowired
	private KecamatanReportService reportService;
	public KabupatenReportServiceImpl() {
		super(Kabupaten.class);
	}
	
	@Override
	public VoteMap getVoteFor(Kabupaten object) {
		if(object!=null){
			List<Kecamatan> list=repository.findByKabupaten(object);
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
		for(Object kecamatan:object){
			if(!(kecamatan instanceof Kecamatan)){
				throw new IllegalArgumentException();
			}
			Kecamatan k=(Kecamatan)kecamatan;
			VoteMap map=reportService.getVoteFor(k);
			if(map!=null && !(map.getVoteResult().isEmpty()))
				voteMaps.add(map);
		}
		return voteMaps;	
	}

}
