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
public class RicercaCalcoliView {
    
    public static final String htmlPageFilePath = "/www/calcolatrice/";
    public static final String htmlPageFileName = "ricercalcoliodierni.html"; 
    
    public 
    static
    byte[] generateHtmlPage(List<CalcoloEntityBean> calcoliOdierni) {
        
        String htmlDynamicContent =" ";
        
        for (CalcoloEntityBean calcolo : calcoliOdierni) {

            String formatHtmlDynamicContent = 
                String.format(
                    "<tr><td>%s</td><td>%.2f</td><td>%.2f</td><td>%s</td><td>%.2f</td></tr>"
                  , calcolo.getDataOraCalcolo()
                  , calcolo.getOperando1()
                  , calcolo.getOperando2()
                  , calcolo.getOperazione()
                  , calcolo.getRisultato());
            
            htmlDynamicContent += formatHtmlDynamicContent;  
        }

        byte[] htmlContentPage = 
            HtmlUtilities.mergeHtmlPageFileWithHtmlDynamicContent(RicercaCalcoliView.htmlPageFileName
                                                                , RicercaCalcoliView.htmlPageFilePath
                                                                , htmlDynamicContent);
        
        return htmlContentPage;

    }
}

