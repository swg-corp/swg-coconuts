package com.swg.coconuts.web.vote.chart;

import java.util.List;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.repo.KelurahanRepository;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KecamatanReportService;
import com.swg.coconuts.web.vote.chart.ChartGenerator.KecamatanChartGenerator;

@Component
public class KecamatanChartGeneratorImpl extends AbstractChartGenerator<Kecamatan> implements KecamatanChartGenerator{

	@Autowired
	private KecamatanReportService reportService;
	@Autowired
	private KelurahanRepository repository;
	
	@Override
	public CartesianChartModel createChartModel(Kecamatan source) {
		CartesianChartModel chartModel=new CartesianChartModel();
		List<Kelurahan> kelurahans=repository.findByKecamatan(source);
		for(VoteMap map:reportService.getChildVoteMap(kelurahans)){
			if(map!=null){
				ChartSeries series=createSeriesFor(map);
				chartModel.addSeries(series);
			}
		}
		return chartModel;
	}

}
