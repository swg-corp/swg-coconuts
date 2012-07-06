package com.swg.coconuts.web.vote.chart;

import java.util.Map;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.swg.coconuts.initiator.param.Area;
import com.swg.coconuts.initiator.report.VoteMap;

public abstract class AbstractChartGenerator<T extends Area> implements ChartGenerator<T> {

	public PieChartModel createOne(VoteMap voteMap){
		PieChartModel model=new PieChartModel();
		Map<String, Integer> result=voteMap.getVoteResult();
		for(String s:result.keySet()){
			model.set(s, result.get(s));
		}
		return model;
	}
	
	public ChartSeries createSeriesFor(VoteMap map){
		ChartSeries series=new ChartSeries();
		series.setLabel(map.getAreaId());
		Map<String, Integer> result=map.getVoteResult();
		for(String key:result.keySet()){
			series.set(key, result.get(key));
		}
		return series;
	}
}
