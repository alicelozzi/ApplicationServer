/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generation.italy.applicationserver.control;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.generation.italy.applicationserver.model.CalcolatriceModelException;
import org.generation.italy.applicationserver.model.dao.CalcoloDao;
import org.generation.italy.applicationserver.model.entity.CalcoloEntityBean;
import org.generation.italy.applicationserver.view.FormCalcoloView;
import org.generation.italy.applicationserver.view.MessaggioView;
import org.generation.italy.applicationserver.view.RicercaCalcoliView;

/**
 *
 * @author Jpr2
 */
public class UtenteAnonimoServlet {

	public static byte[] executeAction(String actionName, Map<String, String> parameterValueCollection) 
																				throws CalcolatriceControlException {

		byte[] htmlContentPage = "".getBytes();

		switch (actionName) {

			case "ricerca-calcoli-indataodierna": // http://localhost:8081/calcolatrice/utenteanonimo/ricerca-calcoli-indataodierna
				htmlContentPage = actionRicercaCalcoliInDataOdierna(parameterValueCollection);
				break;

			case "ricerca-calcoli-indataspecifica": // http://localhost:8081/calcolatrice/utenteanonimo/ricerca-calcoli-indataspecifica
				htmlContentPage = actionRicercaCalcoliInDataSpecifica(parameterValueCollection);
				break;
	
			case "form-calcolo": // http://localhost:8081/calcolatrice/utenteanonimo/form-calcolo
				htmlContentPage = FormCalcoloView.generateHtmlPage();
				break;
	
			case "calcolo": // http://localhost:8081/calcolatrice/utenteanonimo/calcolo
				htmlContentPage = actionCalcolo(parameterValueCollection);
				break;
		}

		return htmlContentPage;
	}
	
	private
	static
	byte[] actionRicercaCalcoliInDataOdierna(Map<String, String> parameterValueCollection) 
								throws CalcolatriceControlException	{
		
		byte[] htmlContentPage = "".getBytes();
		
		try {
			
			CalcoloDao calcoloDao = new CalcoloDao();
			List<CalcoloEntityBean> elencoCalcoliOdierniEntityBean = calcoloDao.loadElencoCalcoliInDataOdierna();

			htmlContentPage = RicercaCalcoliView.generateHtmlPage(elencoCalcoliOdierniEntityBean);
		} catch (CalcolatriceModelException ex) {
			throw new CalcolatriceControlException("UtenteAnonimoServlet -> actionRicercaCalcoliInDataOdierna -> " + ex.getMessage());
		}

		return htmlContentPage;
	}

	
	private
	static
	byte[] actionRicercaCalcoliInDataSpecifica(Map<String, String> parameterValueCollection) 
								throws CalcolatriceControlException	{
		byte[] htmlContentPage = "".getBytes();
		
		try {
			
			// (1) istanziare la classe CalcoloDao e richiamare il metodo
			// loadElencoCalcoliInDataSpecifica

			/*** SPACCHETTO I PARAMETRI ARRIVATI DAL WEB IN FORMATO TESTUALE ****/
			String dataCalcoloString = parameterValueCollection.get("data-calcolo");

			// CONTROLLI SINTATTICI SUI PARAMETRI IN INPUT
			boolean esitoControlliSintatticiSemantici = true;

			String[] dataCalcoloStringArray = dataCalcoloString.split("-");

			String annoDataCalcoloString = "";
			String meseDataCalcoloString = "";
			String giornoDataCalcoloString = "";
			int annoDataCalcolo = -1;
			int meseDataCalcolo = -1;
			int giornoDataCalcolo = -1;

			String messaggioErorreSuControlliSintatticiSemantici = "";
			if (dataCalcoloStringArray.length != 3) {
				esitoControlliSintatticiSemantici = false;
				messaggioErorreSuControlliSintatticiSemantici = "Controllo Sintattico=> La data del calcolo non è in un formato valido!";
			} else {
				try {
					annoDataCalcoloString = dataCalcoloStringArray[0];
					meseDataCalcoloString = dataCalcoloStringArray[1];
					giornoDataCalcoloString = dataCalcoloStringArray[2];
					annoDataCalcolo = Integer.parseInt(annoDataCalcoloString);
					meseDataCalcolo = Integer.parseInt(meseDataCalcoloString);
					giornoDataCalcolo = Integer.parseInt(giornoDataCalcoloString);
				} catch (NumberFormatException ex) {
					esitoControlliSintatticiSemantici = false;
					messaggioErorreSuControlliSintatticiSemantici = "Controllo Sintattico=> La data del calcolo non è in un formato valido!";
				}

			}

			// CONTROLLI SEMANTICI SUI PARAMETRI IN INPUT
			LocalDate dataCalcolo = LocalDate.now();
			if (esitoControlliSintatticiSemantici) {
				try {
					dataCalcolo = LocalDate.of(annoDataCalcolo, meseDataCalcolo, giornoDataCalcolo);
				} catch (DateTimeException ex) {
					esitoControlliSintatticiSemantici = false;
					messaggioErorreSuControlliSintatticiSemantici = "Controllo Semantico=> La data del calcolo non è una data valida!";
				}
			}

			if (!esitoControlliSintatticiSemantici) {
				// RICHIAMO LA PAGINA HTML DI ERRORE SU CONTROLLO SINTATTICO DEI PARAMETRI
				htmlContentPage = MessaggioView.generateHtmlPage(messaggioErorreSuControlliSintatticiSemantici);
			} else {
				CalcoloDao calcoloDao = new CalcoloDao();
				List<CalcoloEntityBean> elencoCalcoliInDataSpecificaEntityBean = calcoloDao
						.loadElencoCalcoliPerData(dataCalcolo);

				// (2) richiamare la classe RicercaCalcoliView per presentare i risutlati nella
				// pagina html (merge tra html statico ed html dinamico)
				htmlContentPage = RicercaCalcoliView.generateHtmlPage(elencoCalcoliInDataSpecificaEntityBean);
			}

		} catch (CalcolatriceModelException ex) {
			throw new CalcolatriceControlException("UtenteAnonimoServlet -> actionRicercaCalcoliInDataSpecifica -> " + ex.getMessage());
		}
		
		return htmlContentPage;

	}
	
	private
	static
	byte[] actionCalcolo(Map<String, String> parameterValueCollection) 
					throws CalcolatriceControlException	{	
		
		byte[] htmlContentPage = "".getBytes();
		
		try {
			/*** SPACCHETTO I PARAMETRI ARRIVATI DAL WEB IN FORMATO TESTUALE ****/
			String operando1String = parameterValueCollection.get("operando1");
			String operando2String = parameterValueCollection.get("operando2");
			String operazioneString = parameterValueCollection.get("operazione");
			float operando1 = Float.parseFloat(operando1String);
			float operando2 = Float.parseFloat(operando2String);

			// ESEGUO IL CALCOLO
			float risultato = 0.0f;
			char operazione = '+';
			switch (operazioneString) {
			case "somma":
				operazione = '+';
				risultato = operando1 + operando2;
				break;
			case "sottrazione":
				operazione = '-';
				risultato = operando1 - operando2;
				break;
			case "divisione":
				operazione = '/';
				risultato = operando1 / operando2;
				break;
			case "prodotto":
				operazione = '*';
				risultato = operando1 * operando2;
				break;
			}

			// ARCHIVIO IL RISULTATO SUL DATABASE (Tabella Calcolo)
			CalcoloEntityBean calcoloEffettuato = new CalcoloEntityBean(2, operando1, operando2, operazione,
					risultato);
			CalcoloDao calcoloDao = new CalcoloDao();
			calcoloDao.addCalcolo(calcoloEffettuato);

			// RICHIAMO LA PAGINA HTML DI PRESENTAZIONE DEL RISULTATO (MESSAGGIO CIRCA ESITO
			// OPERAZIONE)
			htmlContentPage = MessaggioView.generateHtmlPage("Il risultato del calcolo Ã¨: " + risultato);

		} catch (CalcolatriceModelException ex) {
		}

		
		return htmlContentPage;
		
	}
}
	
