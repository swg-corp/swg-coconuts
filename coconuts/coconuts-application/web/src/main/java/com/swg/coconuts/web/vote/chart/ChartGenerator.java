package com.swg.coconuts.web.vote.chart;

import org.primefaces.model.chart.CartesianChartModel;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.initiator.param.Area;

public interface ChartGenerator<T extends Area> {

	public CartesianChartModel createChartModel(T source);
	
	public interface KelurahanChartGenerator extends ChartGenerator<Kelurahan>{
		
	}
	
	public interface KecamatanChartGenerator extends ChartGenerator<Kecamatan>{
		
	}
	
	public interface KabupatenChartGenerator extends ChartGenerator<Kabupaten>{
		
	}
	
	public interface ProvinsiChartGenerator extends ChartGenerator<Provinsi>{
		
	}
}
