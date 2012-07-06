package com.swg.coconuts.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.swg.coconuts.backend.domain.Tps;
import com.swg.coconuts.backend.repo.TpsRepository;

@FacesValidator(value="tpsNumberValidator")
public class TpsNumberValidator implements Validator{

	private TpsRepository tpsRepository;
	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		String numString=value.toString();
		Integer noUrut=Integer.parseInt(numString);
		if(noUrut<1){
			FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Format no urut salah", "no urut harus > 0");
			throw new ValidatorException(message);
		}
		Tps tps=tpsRepository.findByNomor(noUrut);
		if(tps!=null){
			FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kesalahan nomor urut", "TPS dengan nomor urut: "+noUrut+" sudah ada");
			throw new ValidatorException(message);
		}
		
	}
	
	public void setTpsRepository(TpsRepository tpsRepository) {
		this.tpsRepository = tpsRepository;
	}
	

}
