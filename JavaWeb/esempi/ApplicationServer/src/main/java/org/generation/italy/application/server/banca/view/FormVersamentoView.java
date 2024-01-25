package org.generation.italy.application.server.banca.view;

import org.generation.italy.applicationserver.HtmlUtilities;

public class FormVersamentoView {
/*Premessa: seguire come traccia quanto visto a lezione per la gestione del form-apri-conto-cliente

Richiesta: implementare un interfaccia utente per l'attore cliente che consenta di effettuare un versamento sul suo conto.

1) Nella directory /www/banca inserire il file "form-versamento.html"
2) In "ClienteServlet" gestire l'azione "form-versamento" che visualizza nel browser l'interfaccia utente "Effettua VErsamento"
(rendering di "form-versamento.html")
3) Gestire (se non fatto nell'esercizio di ieri) l'azione "versamento" con il messaggio circa l'esito dell'opeazione di versamento
*/
	public static final String htmlPageFilePath = "/www/banca/";
    public static final String htmlPageFileName = "form-versamento.html"; 
    
    public 
    static
    byte[] generateHtmlPage() {
        
        String htmlDynamicContent = "";
        
        byte[] htmlContentPage = 
            HtmlUtilities.mergeHtmlPageFileWithHtmlDynamicContent(FormVersamentoView.htmlPageFileName
                                                                , FormVersamentoView.htmlPageFilePath
                                                                , htmlDynamicContent);
        
        return htmlContentPage;

    }
	
	
}

