package com.swg.coconuts.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.swg.coconuts.backend.domain.CandidateCouple;
import com.swg.coconuts.backend.repo.CandidateCoupleRepository;

@FacesValidator(value="candidateNumberValidator")
public class CandidateNumberValidator implements Validator {

	private CandidateCoupleRepository coupleRepository;
	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		String numString=value.toString();
		Integer noUrut=Integer.parseInt(numString);
		if(noUrut<1){
			FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Format no urut salah", "no urut harus > 0");
			throw new ValidatorException(message);
		}
		CandidateCouple couple=coupleRepository.findByNomorUrut(noUrut);
		if(couple!=null){
			FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kandidat sudah ada", "Kandidat dengan nomor urut: "+noUrut+" sudah ada");
			throw new ValidatorException(message);
		}
	}
	
	public void setCoupleRepository(CandidateCoupleRepository coupleRepository) {
		this.coupleRepository = coupleRepository;
	}

}
