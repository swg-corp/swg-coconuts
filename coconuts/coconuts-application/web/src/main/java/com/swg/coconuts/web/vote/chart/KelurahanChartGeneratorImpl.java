package com.swg.coconuts.web.vote.chart;

import java.util.List;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.repo.TpsRepository;
import com.swg.coconuts.initiator.report.VoteMap;
import com.swg.coconuts.report.service.KelurahanReportService;
import com.swg.coconuts.web.vote.chart.ChartGenerator.KelurahanChartGenerator;

@Component
public class KelurahanChartGeneratorImpl extends AbstractChartGenerator<Kelurahan> implements KelurahanChartGenerator {

	@Autowired
	private KelurahanReportService reportService;
	@Autowired
	private TpsRepository tpsRepository;

	@Override
	public CartesianChartModel createChartModel(Kelurahan source) {
		CartesianChartModel chartModel=new CartesianChartModel();
		List<Tps> tps=tpsRepository.findByKelurahan(source);
		for(VoteMap map:reportService.getChildVoteMap(tps)){
			if(map!=null){
				ChartSeries series=createSeriesFor(map);
				chartModel.addSeries(series);
			}
		}
		return chartModel;
	}
	
	
	
	


	

}
