package com.swg.coconuts.web.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.swg.coconuts.backend.init.UploadExcelService;
import com.swg.coconuts.initiator.util.XlsContext;
import com.swg.coconuts.initiator.util.XlsLoader;

@ManagedBean
@RequestScoped
public class FileUploadBean implements Serializable{
	private static final long serialVersionUID = 852814508212961331L;
	
	@ManagedProperty("#{simpleUploadExcelService}")
	private UploadExcelService excelService;
	
	public void handle(FileUploadEvent event){
		UploadedFile file=event.getFile();
		XlsLoader loader=XlsContext.getLoader();
		InputStream inputStream=null;
		FacesMessage facesMessage;
		try{
			inputStream=file.getInputstream();
			Workbook workbook=loader.loadWorkbook(inputStream);
			excelService.processUpload(workbook);
			facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO,"upload success "+file.getFileName(), null);
		}catch(IOException e){
			facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR,"upload failed ", null);
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public String uploadForm(){
		return "uploadForm";
	}
	
	public UploadExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(UploadExcelService excelService) {
		this.excelService = excelService;
	}

}
