/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generation.italy.applicationserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Angelo Pasquarelli
 */
public 
class HtmlUtilities {
    
    
    public 
    static
    byte[] mergeHtmlPageFileWithHtmlDynamicContent(String htmlPageFileName, String htmlPageFilePath, String htmlDynamicContent) {

        byte[] htmlPageContent = "".getBytes();
        
        Path htmlPagePathFileName = readHtmlPagePathFileName(htmlPageFileName, htmlPageFilePath); 
        
        if (Files.exists(htmlPagePathFileName)) {
            
            try {
            
                // file exist
                String htmlPageFileContentType = Files.probeContentType(htmlPagePathFileName);              //imposta il content-type header della response

                byte[] contentAsByteArray = Files.readAllBytes(htmlPagePathFileName);

                String htmlPageContentString = new String(contentAsByteArray, StandardCharsets.UTF_8);
                htmlPageContentString = htmlPageContentString.replace("%htmldynamiccontent%", htmlDynamicContent);
                
                htmlPageContent = htmlPageContentString.getBytes();
            }
            catch (IOException ex) {
                
            }
            
        }
        
        return htmlPageContent;
    }
            








    /* 
        PRELEVA LA RISORSA RICHIESTA (ATTUALMENTE GESTISCE FILE HTML - PAGINA WEB)
     */
    private static Path readHtmlPagePathFileName(String htmlPageFileName, String htmlPageWebAppPath) {
                                                                                //preleva il file risorsa richiesto, se esistente (pagina web) 

//        if ("/".equals(path)) {
//            htmlPageFileName = "/index.html";
//        }

        Path currentDir = Paths.get(""); 
        Path fullPath = currentDir.toAbsolutePath(); 

        return Paths.get(fullPath.toString(), htmlPageWebAppPath, htmlPageFileName);

    }
}

