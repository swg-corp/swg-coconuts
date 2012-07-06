package com.swg.coconuts.web.vote.chart;

import java.util.List;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.backend.repo.KabupatenRepository;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.ProvinsiReportService;
import com.swg.coconuts.web.vote.chart.ChartGenerator.ProvinsiChartGenerator;

@Component
public class ProvinsiChartGeneratorImpl extends AbstractChartGenerator<Provinsi> implements ProvinsiChartGenerator{

	@Autowired
	private ProvinsiReportService reportService;
	@Autowired
	private KabupatenRepository repository;
	
	@Override
	public CartesianChartModel createChartModel(Provinsi source) {
		CartesianChartModel chartModel=new CartesianChartModel();
		List<Kabupaten> kabupatens=repository.findByProvinsi(source);
		for(VoteMap map:reportService.getChildVoteMap(kabupatens)){
			if(map!=null){
				ChartSeries series=createSeriesFor(map);
				chartModel.addSeries(series);
			}
		}
		return chartModel;
	}

}
