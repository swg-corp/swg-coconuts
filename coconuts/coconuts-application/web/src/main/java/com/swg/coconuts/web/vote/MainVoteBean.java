package com.swg.coconuts.web.vote;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.PieChartModel;

import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;
import com.swg.coconuts.web.VisibilityAware;

@ManagedBean
@SessionScoped
public class MainVoteBean implements VisibilityAware,Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{candidateCoupleRepository}")
	private CandidateCoupleRepository coupleRepository; 

	private boolean dataVisible;
	private PieChartModel model;
	private List<CandidateCouple> couples;
	
	@Override
	public boolean isDataVisible() {
		return dataVisible;
	}
	
	public void populate(){
		couples=coupleRepository.findAll();
		dataVisible=!couples.isEmpty();
		model=new PieChartModel();
		if(dataVisible){
			for(CandidateCouple couple:couples){
				model.set(couple.getNickName(), couple.getVote().getTotal());
			}
		
		}
	}
	
	public void setCoupleRepository(CandidateCoupleRepository coupleRepository) {
		this.coupleRepository = coupleRepository;
	}
	
	public CandidateCoupleRepository getCoupleRepository() {
		return coupleRepository;
	}
	
	public void setModel(PieChartModel model) {
		this.model = model;
	}
	
	public PieChartModel getModel() {
		if(model==null){
			populate();
		}
		return model;
	}
}
