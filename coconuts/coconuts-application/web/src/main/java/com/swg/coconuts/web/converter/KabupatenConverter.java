package com.swg.coconuts.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.swg.coconuts.backend.domain.Kabupaten;
import com.swg.coconuts.backend.repo.KabupatenRepository;

@FacesConverter(value="kabupatenConverter")
public class KabupatenConverter implements Converter{

	private final Logger logger=Logger.getLogger(getClass());
	

	private KabupatenRepository repository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		if(value==null || value.length()==0){
			return null;
		}
		Kabupaten kabupaten=repository.findByName(value);
		logger.info("get kabupaten: "+kabupaten.getName());
		return kabupaten;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		return value instanceof Kabupaten? ((Kabupaten)value).getName():"";
	}
	
	public void setRepository(KabupatenRepository repository) {
		this.repository = repository;
	}

}
