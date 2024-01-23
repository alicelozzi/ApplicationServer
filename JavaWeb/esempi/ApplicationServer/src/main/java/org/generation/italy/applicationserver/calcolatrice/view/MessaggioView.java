/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generation.italy.applicationserver.calcolatrice.view;

import java.util.List;

import org.generation.italy.applicationserver.HtmlUtilities;
import org.generation.italy.applicationserver.calcolatrice.model.entity.CalcoloEntityBean;

/**
 *
 * @author Angelo Pasquarelli
 */
public class MessaggioView {
    
    public static final String htmlPageFilePath = "/www/calcolatrice/";
    public static final String htmlPageFileName = "messaggio.html"; 
    
    public 
    static
    byte[] generateHtmlPage(String messaggioDaPresentare) {
        
        String htmlDynamicContent = 
                String.format("<p>%s</p>", messaggioDaPresentare);
        
        byte[] htmlContentPage = 
            HtmlUtilities.mergeHtmlPageFileWithHtmlDynamicContent(MessaggioView.htmlPageFileName
                                                                , MessaggioView.htmlPageFilePath
                                                                , htmlDynamicContent);
        
        return htmlContentPage;

    }
}

