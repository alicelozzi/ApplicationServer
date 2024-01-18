/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generation.italy.applicationserver.view;

import java.util.List;

import org.generation.italy.applicationserver.HtmlUtilities;
import org.generation.italy.applicationserver.model.entity.CalcoloEntityBean;

/**
 *
 * @author Angelo Pasquarelli
 */
public class FormCalcoloView {
    
    public static final String htmlPageFilePath = "/www/calcolatrice/";
    public static final String htmlPageFileName = "formcalcolo.html"; 
    
    public 
    static
    byte[] generateHtmlPage() {
        
        String htmlDynamicContent = "";
        
        byte[] htmlContentPage = 
            HtmlUtilities.mergeHtmlPageFileWithHtmlDynamicContent(FormCalcoloView.htmlPageFileName
                                                                , FormCalcoloView.htmlPageFilePath
                                                                , htmlDynamicContent);
        
        return htmlContentPage;

    }
}

