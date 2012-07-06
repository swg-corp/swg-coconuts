package com.swg.coconuts.web.vote.chart;

import java.util.List;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.repo.KecamatanRepository;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KabupatenReportService;
import com.swg.coconuts.web.vote.chart.ChartGenerator.KabupatenChartGenerator;

@Component
public class KabupatenChartGeneratorImpl extends AbstractChartGenerator<Kabupaten> implements KabupatenChartGenerator{
	
	@Autowired
	private KabupatenReportService reportService;
	@Autowired
	private KecamatanRepository repository;
	
	@Override
	public CartesianChartModel createChartModel(Kabupaten source) {
		CartesianChartModel chartModel=new CartesianChartModel();
		List<Kecamatan> kecamatans=repository.findByKabupaten(source);
		for(VoteMap map:reportService.getChildVoteMap(kecamatans)){
			if(map!=null){
				ChartSeries series=createSeriesFor(map);
				chartModel.addSeries(series);
			}
		}
		return chartModel;
	}

}
