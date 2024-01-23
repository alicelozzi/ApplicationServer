package org.generation.italy.applicationserver.calcolatrice.model;
        
/**
 *
 * @author pinzo
 */
public class QueryCatalog {
    
    //Query Utente
    
    public static final String insertCalcolo = " INSERT INTO calcolo (idutente, operando1, operando2, operazione, risultato) VALUES (?, ?, ?, ?, ?) ";

    public static final String selectCalcolo = " SELECT idCalcolo, idutente, dataOraCalcolo, operando1, operando2, operazione, risultato FROM calcolo ";
    
}
