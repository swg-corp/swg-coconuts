package com.swg.coconuts.report.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.initiator.report.AbstractReportService;
import com.swg.coconuts.initiator.report.VoteMap;

@Component
public class ProvinsiReportServiceImpl extends AbstractReportService<Provinsi> implements ProvinsiReportService {

	@Autowired
	private KabupatenRepository repository;
	@Autowired
	private KabupatenReportService reportService;
	
	public ProvinsiReportServiceImpl() {
		super(Provinsi.class);
		
	}
	
	@Override
	public VoteMap getVoteFor(Provinsi object) {
		if(object!=null){
			List<Kabupaten> list=repository.findByProvinsi(object);
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
		for(Object kabupaten:object){
			if(!(kabupaten instanceof Kabupaten)){
				throw new IllegalArgumentException();
			}
			Kabupaten k=(Kabupaten)kabupaten;
			VoteMap map=reportService.getVoteFor(k);
			if(map!=null && !(map.getVoteResult().isEmpty()))
				voteMaps.add(map);
		}
		return voteMaps;	
	}

}
