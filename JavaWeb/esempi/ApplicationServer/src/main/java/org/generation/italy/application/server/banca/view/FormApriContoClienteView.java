package org.generation.italy.application.server.banca.view;

import org.generation.italy.applicationserver.HtmlUtilities;

public class FormApriContoClienteView  {
	    
	    public static final String htmlPageFilePath = "/www/banca/";
	    public static final String htmlPageFileName = "form-versamento.html"; 
	    
	    public 
	    static
	    byte[] generateHtmlPage() {
	        
	        String htmlDynamicContent = "";
	        
	        byte[] htmlContentPage = 
	            HtmlUtilities.mergeHtmlPageFileWithHtmlDynamicContent(FormApriContoClienteView.htmlPageFileName
	                                                                , FormApriContoClienteView.htmlPageFilePath
	                                                                , htmlDynamicContent);
	        
	        return htmlContentPage;

	    }
	}

