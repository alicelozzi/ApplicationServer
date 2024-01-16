package org.generation.italy.banca.java_base_integrazioni.logging;

/*

    Livelli di log (fonte wikipedia)

    La seguente tabella definisce i livelli dei log e i messaggi in log4j in ordine crescente di severità. 
    La colonna di sinistra indica il livello di log designato e alla destra c'è una breve descrizione.

    Livello         Descrizione

    OFF             Il livello più alto possibile, viene usato per disattivare i log.

    (1)TRACE        Alcune informazioni dettagliate. Ci si aspetta che venga scritto esclusivamente nei file di log. È stato aggiunto nella versione 1.2.12. 
    (2)DEBUG        Usato nella fase di debug del programma. Viene riportato nel file di log.
    (3)INFO         Usato per segnalare eventi di esecuzione (esempio: startup/shutdown). Deve essere segnalato ma poi non mantenuto per tanto tempo.
    (4)WARN         Usato per ogni condizione inaspettata o anomalia di esecuzione, che però non necessariamente ha comportato un errore.
    (5)ERROR        Un errore di esecuzione o una condizione imprevista. Anche questo deve essere immediatamente segnalato.
    (6)FATAL        Errore importante che causa un prematuro termine dell'esecuzione. Ci si aspetta che questo sia visibile immediatamente all'operatore.


*/


// Import log4j classes.
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 *
 * @author Jpr2
 */
public class Calcolatrice {
    
    // Define a static logger variable so that it references the
    // Logger instance named "MyApp".
    private static final Logger logger = LogManager.getLogger(Calcolatrice.class);


    public
    static
    int calcolo(int operando1, int operando2, char operazione) {
        
    	logger.trace("Esecuzione del metodo di calcolo!!!");
    	
        int risultatoCalcolo = 0;
        
        switch (operazione) {
            case '+':
                risultatoCalcolo = somma(operando1, operando2);
                break;
            case '-':
                risultatoCalcolo = sottrazione(operando1, operando2);
                break;
            case '*':
                risultatoCalcolo = prodotto(operando1, operando2);
                break;
            case '/':
                risultatoCalcolo = divisione(operando1, operando2);
                break;
            default:
                risultatoCalcolo=0;
        }

        logger.debug(String.format("Calcolo: operando1->{%s} | operando2->{%s} | operazione->{%s} | risultato->{%s} ", operando1, operando2, operazione, risultatoCalcolo));
        
        return risultatoCalcolo;
    }

    private 
    static 
    int somma(int op1, int op2) {
        return op1+op2;
    }
    
    private 
    static 
    int sottrazione(int op1, int op2) {
        return op1-op2;
    }

    private 
    static 
    int prodotto(int op1, int op2) {
        return op1*op2;
    }

    private 
    static 
    int divisione(int op1, int op2) {
        
        int risultatoDivisione = 0;
                
        try {
            risultatoDivisione = op1/op2;
        }
        catch (Exception oEx) {
            //logger.error("La divisione con divisore 0 non è ammessa!");
            logger.error(oEx.getMessage());
        }

        return risultatoDivisione;
    }
    
}
