package com.swg.coconuts.web.vote.chart;

import java.util.List;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.initiator.param.Area;

public interface ChartTrigger<T extends Area> {

	List<T> completeList(String query);
	
	public interface KelurahanChartTrigger extends ChartTrigger<Kelurahan>{
		
	}
	
	public interface KecamatanChartTrigger extends ChartTrigger<Kecamatan>{
		
	}
}
