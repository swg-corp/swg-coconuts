package com.swg.coconuts.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.swg.coconuts.backend.domain.Kecamatan;
import com.swg.coconuts.backend.repo.KecamatanRepository;

@FacesConverter(value="kecamatanConverter")
public class KecamatanConverter implements Converter {

	private KecamatanRepository repository;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if(value==null || value.length()==0){
			return null;
		}
		Kecamatan kecamatan=repository.findByName(value);
		
		return kecamatan;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return value instanceof Kecamatan? ((Kecamatan)value).getName():"";
	}
	
	public void setRepository(KecamatanRepository repository) {
		this.repository = repository;
	}

}
