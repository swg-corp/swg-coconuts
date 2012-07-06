package com.swg.coconuts.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.swg.coconuts.backend.domain.Provinsi;
import com.swg.coconuts.backend.repo.ProvinsiRepository;

@FacesConverter(value="provinsiConverter")
public class ProvinsiConverter implements Converter{

	private final Logger logger=Logger.getLogger(getClass());
	private ProvinsiRepository repository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		if(value==null || value.length()==0){
			return null;
		}
		Provinsi provinsi=repository.findByName(value);
		logger.info("get provinsi: "+provinsi.getName());
		return provinsi;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		return value instanceof Provinsi? ((Provinsi)value).getName():"";
	}
	
	public void setRepository(ProvinsiRepository repository) {
		this.repository = repository;
	}

}
