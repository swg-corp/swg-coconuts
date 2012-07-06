package com.swg.coconuts.web.area;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.backend.init.ProvinceService;
import com.swg.coconuts.web.Manager;

@ManagedBean
@SessionScoped
public class ProvinsiBean implements Serializable,Manager{

	private static final long serialVersionUID = 4018260880841025254L;

	private Provinsi provinsi;
	@ManagedProperty("#{singleProvinsiService}")
	private ProvinceService service;
	private boolean dataVisible;
	private boolean dialogVisible;

	public String findOne(){
		provinsi=service.findSingle();
		dataVisible=(provinsi!=null);
		return null;
	}
	
	@Override
	public String displayList() {
		findOne();
		return "listProvinsi?faces-redirect=true";
	}

	@Override
	public boolean isDataVisible() {
		return dataVisible;
	}
	
	public boolean isDialogVisible() {
		return dialogVisible;
	}

	@Override
	public String save() {
		service.save(provinsi);
		clear();
		return displayList();
	}

	@Override
	public void clear() {
		provinsi=null;
		dialogVisible=false;
	}

	@Override
	public String delete() {
        clear();
		return findOne();
	}

	@Override
	public String displayNew() {
		provinsi=new Provinsi();
		dialogVisible=true;
		return null;
	}

	@Override
	public String displayEdit() {
		dialogVisible=true;
		return null;
	}

	public Provinsi getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(Provinsi provinsi) {
		this.provinsi = provinsi;
	}

	public ProvinceService getService() {
		return service;
	}

	public void setService(ProvinceService service) {
		this.service = service;
	}

	public void setDataVisible(boolean dataVisible) {
		this.dataVisible = dataVisible;
	}

	public void setDialogVisible(boolean dialogVisible) {
		this.dialogVisible = dialogVisible;
	}
	
	

}
