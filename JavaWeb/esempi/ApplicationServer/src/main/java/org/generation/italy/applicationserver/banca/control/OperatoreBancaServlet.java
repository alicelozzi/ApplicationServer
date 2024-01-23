package org.generation.italy.applicationserver.banca.control;

import java.util.Map;

import org.generation.italy.applicationserver.banca.model.BancaModelException;
import org.generation.italy.applicationserver.banca.model.TestJdbcBanca;
import org.generation.italy.applicationserver.banca.model.entity.Conto;

public class OperatoreBancaServlet {


	public static byte[] executeAction(String actionName, Map<String, String> parameterValueCollection) 
																				throws BancaControlException {

		byte[] htmlContentPage = "".getBytes();

		switch (actionName) {
		
			//http://localhost:8081/banca/operatore-banca/apri-conto-cliente?iban=ESaa0123456789012345678901234567&codice-fiscale=MRRGVN0123456789&valuta=EUR
			case "apri-conto-cliente":
				htmlContentPage = actionApriContoCliente(parameterValueCollection);
				break;
			
				default:;
		}
		
		return htmlContentPage;
	}
	
	
	
	private
	static
	byte[] actionApriContoCliente(Map<String, String> parameterValueCollection) {
													//throws BancaControlException, BancaModelException	{
		
		byte[] htmlContentPage = "".getBytes();
		
		String ibanString = parameterValueCollection.get("iban");
		String valutaString = parameterValueCollection.get("valuta");
		String codiceFiscaleString = parameterValueCollection.get("codice-fiscale");
		
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

