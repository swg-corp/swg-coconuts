package com.swg.coconuts.web.util;

import java.io.File;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public final class ServletUtil {

	public static String getDownloadPath(String value){
		ServletContext context=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
		String path=context.getRealPath("")+File.separator+"download"+File.separator+value;
		return path;
	}
	
	public static String getDownloadDir(){
		ServletContext context=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
		String downloadDir=context.getRealPath("")+File.separator+"download"+File.separator;
		return downloadDir;
	}
}
