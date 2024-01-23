package org.generation.italy.applicationserver.banca.control;

import java.util.Map;



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

			//http://localhost:8081/banca/cliente/ricerca-movimenti-effettuati?iban=ESaa0123456789012345678901234567&tipo-operazione=P
			//http://localhost:8081/banca/cliente/ricerca-movimenti-effettuati?iban=ESaa0123456789012345678901234567&tipo-operazione=V
//			case "ricerca-movimenti-effettuati":
//				break;
	
		}
		
		return htmlContentPage;
	}
	
	
	//public actionRicercaPrelievi(String iban) {
		
	//(}
}
