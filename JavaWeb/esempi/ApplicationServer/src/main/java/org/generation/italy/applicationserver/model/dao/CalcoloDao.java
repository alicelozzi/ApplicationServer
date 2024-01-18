/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generation.italy.applicationserver.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;


import org.generation.italy.applicationserver.model.CalcolatriceModelConfiguration;
import org.generation.italy.applicationserver.model.CalcolatriceModelException;
import org.generation.italy.applicationserver.model.QueryCatalog;
import org.generation.italy.applicationserver.model.entity.CalcoloEntityBean;

/**
 *
 * @author Angelo Pasquarelli
 */

/* operazioni CRUD ammesse sulla tabella 'calcolo' del datbase 'calcolatrice' */

public class CalcoloDao  implements ICalcoloDao {
    
    //public static enum OperazioniStandard {addizione, sottrazione, moltiplicazione, divisione};
    Connection dbConnection;
    
    private static final char[] elencoOperazioniStandard = {'+', '-', '*', '/'};     
    
    public
    CalcoloDao() {
        dbConnection = (Connection) CalcolatriceModelConfiguration.getConfigurationValueFromKey("dbConnection");
    }
    
    
    
    
    
    @Override
    public 
    void addCalcolo(CalcoloEntityBean calcolo) throws CalcolatriceModelException {
        try {

            PreparedStatement preparedStatement = dbConnection.prepareStatement(QueryCatalog.insertCalcolo);  
            
            preparedStatement.setInt(1, calcolo.getIdUtente());
            preparedStatement.setFloat(2, calcolo.getOperando1());
            preparedStatement.setFloat(3, calcolo.getOperando2());
            preparedStatement.setString(4, new Character(calcolo.getOperazione()).toString());
            preparedStatement.setFloat(5, calcolo.getRisultato());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException sqlException) {                                   //cattura eccezione SQLException
            throw new CalcolatriceModelException("");
        }
         
    }
    
    @Override
    public
    List<CalcoloEntityBean> loadElencoCalcoliInDataOdierna() throws CalcolatriceModelException {
        return loadElencoCalcoliPerData(LocalDate.now());
    }

    
    @Override
   public
    List<CalcoloEntityBean> loadElencoCalcoliPerUtenteInDataOdierna(int idUtente) throws CalcolatriceModelException {
        
        String whereCondition = String.format(" WHERE idUtente = %d ", idUtente);  
        whereCondition += String.format(" AND DATE(dataOraCalcolo) = '%s' ", LocalDate.now().toString());

        return loadElencoCalcoli(whereCondition);
    }

    
    @Override
   public
    List<CalcoloEntityBean> loadElencoCalcoliPerData(LocalDate dataCalcolo) throws CalcolatriceModelException {

        String whereCondition = 
            String.format(" WHERE DATE(dataOraCalcolo) = '%s'", dataCalcolo.toString());  // \"2021-12-08\"";

        return loadElencoCalcoli(whereCondition);
    }

    private
    List<CalcoloEntityBean> loadElencoCalcoli(String whereCondition) throws CalcolatriceModelException {

        List<CalcoloEntityBean> elencoCalcoli = new ArrayList<CalcoloEntityBean>();

        try {
            
            Statement sqlStmt = dbConnection.createStatement();

            String formatSelectCalcolo
                    = String.format(QueryCatalog.selectCalcolo + whereCondition);

            ResultSet rsSelectCalcolo = sqlStmt.executeQuery(formatSelectCalcolo);

            while (rsSelectCalcolo.next()) {

                int idCalcolo = rsSelectCalcolo.getInt(1);
                if (rsSelectCalcolo.wasNull()) { idCalcolo = 0; }

                int idUtente = rsSelectCalcolo.getInt(2);
                if (rsSelectCalcolo.wasNull()) { idUtente = 0; }

                Timestamp dataOraCalcoloTimeStamp = rsSelectCalcolo.getTimestamp(3);
                LocalDateTime dataOraCalcolo = LocalDateTime.ofInstant(dataOraCalcoloTimeStamp.toInstant(), ZoneOffset.ofHours(0));
                if (rsSelectCalcolo.wasNull()) {dataOraCalcolo = null;}

                float operando1 = rsSelectCalcolo.getFloat(4);
                if (rsSelectCalcolo.wasNull()) { operando1 = 0.0f; }

                float operando2 = rsSelectCalcolo.getFloat(5);
                if (rsSelectCalcolo.wasNull()) { operando2 = 0.0f; }

                char operazione = rsSelectCalcolo.getString(6).charAt(0);
                if (rsSelectCalcolo.wasNull()) { operazione = ' '; }

                float risultato = rsSelectCalcolo.getFloat(7);
                if (rsSelectCalcolo.wasNull()) { operando2 = 0.0f; }

                CalcoloEntityBean calcolo = new CalcoloEntityBean(idCalcolo, idUtente, dataOraCalcolo, operando1, operando2, operazione, risultato);

                elencoCalcoli.add(calcolo);

            }
        } catch (SQLException sqlException) {
            throw new CalcolatriceModelException("CalcoloDao->loadElencoCalcoli->" + sqlException.getMessage());
        }
        
        return elencoCalcoli;
    }
    
}
