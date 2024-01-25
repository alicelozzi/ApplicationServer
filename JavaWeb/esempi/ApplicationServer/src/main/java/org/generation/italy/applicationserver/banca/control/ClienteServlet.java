package org.generation.italy.applicationserver.banca.control;

import java.util.Map;

import org.generation.italy.application.server.banca.view.FormVersamentoView;
import org.generation.italy.applicationserver.banca.model.BancaModelException;
import org.generation.italy.applicationserver.banca.model.TestJdbcBanca;
import org.generation.italy.applicationserver.banca.model.entity.Movimento;




public class ClienteServlet {

	public static byte[] executeAction(String actionName, Map<String, String> parameterValueCollection) 
																				throws BancaControlException {

		byte[] htmlContentPage = "".getBytes();

		switch (actionName) {
			
			//http://localhost:8081/banca/cliente/prelievo?iban=ITff0123456789012345678901234567&importo=555
			case "prelievo":
				//actionPrelievo();
				break;
			
			//http://localhost:8081/banca/cliente/versamento?iban=ESaa0123456789012345678901234567&importo=780
			case "versamento":
				htmlContentPage = actionAggiungiVersamento(parameterValueCollection);
				//actionVersamento();
				break;
			
			//http://localhost:8081/banca/cliente/ricerca-prelievi?iban=ESaa0123456789012345678901234567
			case "ricerca-prelievi":
				System.out.println("AZIONE=>" + "ricerca-prelievi");
				//actionRicercaPrelievi()
				break;

			//http://localhost:8081/banca/cliente/ricerca-versamenti?iban=ESaa0123456789012345678901234567
			case "ricerca-versamenti":
				//actionRicercaVersamenti()
				break;
				
			case "form-versamento":
				htmlContentPage = actionFormVersamento(parameterValueCollection);
				System.out.println("Versamento effettuato");
				break;

			//http://localhost:8081/banca/cliente/ricerca-movimenti-effettuati?iban=ESaa0123456789012345678901234567&tipo-operazione=P
			//http://localhost:8081/banca/cliente/ricerca-movimenti-effettuati?iban=ESaa0123456789012345678901234567&tipo-operazione=V
//			case "ricerca-movimenti-effettuati":
//				break;
	
		}
		
		return htmlContentPage;
	}
	private
	static
	byte[] actionFormVersamento(Map<String, String> parameterValueCollection) {
													//throws BancaControlException, BancaModelException	{
		byte[] htmlContentPage = "".getBytes();
		
		htmlContentPage = FormVersamentoView.generateHtmlPage();
		
		return htmlContentPage;
		
	}
	
	
	
	
	private static byte [] actionAggiungiVersamento(Map<String,String> parameterValueCollection)
																throws BancaControlException {
		
		byte [] htmlContentPage = "".getBytes();
		
		String ibanString = parameterValueCollection.get("iban");
		String importoString = parameterValueCollection.get("importo");
		Float importo = Float.parseFloat(importoString);
		Movimento movimento=new Movimento(ibanString,importo, "V");
		
		try {
			TestJdbcBanca testJdbcBanca =  new TestJdbcBanca();
			testJdbcBanca.getMovimentoDao().addMovimento(movimento);
			
			htmlContentPage = new String("E' stato creato il nuovo conto per il cliente!").getBytes();
		} catch (BancaModelException e) {
			htmlContentPage = new String("Impossibile aprire il conto indicato: verificare che non sei già esistente!").getBytes();
			//htmlContentPage = e.getMessage().getBytes();

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return htmlContentPage;
		
	}
	
	//public actionRicercaPrelievi(String iban) {
		
	//(}
}
