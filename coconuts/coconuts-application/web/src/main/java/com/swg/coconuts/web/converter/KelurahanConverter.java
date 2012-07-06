package com.swg.coconuts.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.swg.coconuts.backend.domain.Kelurahan;
import com.swg.coconuts.backend.repo.KelurahanRepository;

@FacesConverter(value="kelurahanConverter")
public class KelurahanConverter implements Converter{
	
	private KelurahanRepository repository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if(value==null || value.length()==0){
			return null;
		}
		Kelurahan kelurahan=repository.findByName(value);
		return kelurahan;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return value instanceof Kelurahan? ((Kelurahan)value).getName():"";
	}
	
	public void setRepository(KelurahanRepository repository) {
		this.repository = repository;
	}

}
