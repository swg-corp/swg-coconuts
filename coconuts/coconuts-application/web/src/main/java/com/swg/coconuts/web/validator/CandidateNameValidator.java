package com.swg.coconuts.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;

@FacesValidator(value="candidateNameValidator")
public class CandidateNameValidator implements Validator{

	private CandidateCoupleRepository coupleRepository;
	
	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		String name=(String)value;
		CandidateCouple couple=coupleRepository.findByNickName(name);
		if(couple!=null){
			FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kandidat sudah ada", "Kandidat dengan nama: "+name+" sudah ada");
			throw new ValidatorException(message);
		}
	}
	
	public void setCoupleRepository(CandidateCoupleRepository coupleRepository) {
		this.coupleRepository = coupleRepository;
	}

}
