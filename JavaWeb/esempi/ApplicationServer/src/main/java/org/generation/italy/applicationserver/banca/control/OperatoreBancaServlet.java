package org.generation.italy.applicationserver.banca.control;

import java.util.Map;

import org.generation.italy.application.server.banca.view.FormApriContoClienteView;
import org.generation.italy.applicationserver.banca.model.BancaModelException;
import org.generation.italy.applicationserver.banca.model.TestJdbcBanca;
import org.generation.italy.applicationserver.banca.model.entity.Conto;
import org.generation.italy.applicationserver.calcolatrice.view.FormCalcoloView;

public class OperatoreBancaServlet {


	public static byte[] executeAction(String actionName, Map<String, String> parameterValueCollection) 
			throws BancaControlException {

byte[] htmlContentPage = "".getBytes();

switch (actionName) {

//http://localhost:8081/banca/operatore-banca/apri-conto-cliente?iban=ESaa0123456789012345678901234567&codice-fiscale=MRRGVN0123456789&valuta=EUR
case "apri-conto-cliente":
htmlContentPage = actionApriContoCliente(parameterValueCollection);
break;


case "form-apri-conto-cliente":
htmlContentPage = actionFormApriContoCliente(parameterValueCollection);
break;

default:;
}

return htmlContentPage;
}

private
static
byte[] actionFormApriContoCliente(Map<String, String> parameterValueCollection) {
//throws BancaControlException, BancaModelException	{
byte[] htmlContentPage = "".getBytes();

htmlContentPage = FormApriContoClienteView.generateHtmlPage();

return htmlContentPage;

}


private
static
byte[] actionApriContoCliente(Map<String, String> parameterValueCollection) 
throws BancaControlException {
//throws BancaControlException, BancaModelException	{

byte[] htmlContentPage = "".getBytes();

String ibanString = parameterValueCollection.get("iban");
String valutaString = parameterValueCollection.get("valuta");
String codiceFiscaleString = parameterValueCollection.get("codice-fiscale");


//controlli sintattici su parametri da web
if ( (valutaString.length() != 3) || (codiceFiscaleString.length() != 16) ) {
htmlContentPage = new String("Errore nel formato dei dati input!").getBytes();
//throw new BancaControlException("OperatoreBancaSErvlet -> actionApriContoCliente -> Errore nel formato dei dati input!!!");
}
/*Implementare i controlli sintattici lato html e lato servlet per il versamento sul conto corrente visto ieri 
 * (Esercizio_20240124_HtmlFormVersamento)
 */
//controlli semantici su parmetri da web

//accede alla fonte dati, istanziando TEstJdbcBanca
//che ha come attributi i riferimenti ai metodi delle classi DAO.
Conto conto = 
new Conto(ibanString
, codiceFiscaleString
, valutaString
, 0.0f); 


try {
TestJdbcBanca testJdbcBanca =  new TestJdbcBanca();
testJdbcBanca.getContoDao().addConto(conto);

htmlContentPage = new String("E' stato creato il nuovo conto per il cliente!").getBytes();
} catch (BancaModelException e) {
htmlContentPage = new String("Impossibile aprire il conto indicato: verificare che non sei già esistente!").getBytes();
//htmlContentPage = e.getMessage().getBytes();

// TODO Auto-generated catch block
e.printStackTrace();
}


return htmlContentPage;
}

}
