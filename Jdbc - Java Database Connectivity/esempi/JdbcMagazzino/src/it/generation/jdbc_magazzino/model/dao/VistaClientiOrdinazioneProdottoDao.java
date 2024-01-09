
package it.generation.jdbc_magazzino.model.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelConfiguration;
import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.VistaClientiOrdinazioneProdotto;


public class VistaClientiOrdinazioneProdottoDao implements IVistaClientiOrdinazioneProdottoDao {
    
    private Connection dbMagazzinoConnection; 
    
    public VistaClientiOrdinazioneProdottoDao() {
        this.dbMagazzinoConnection = (Connection) MagazzinoModelConfiguration.getConfigurationValueFromKey("dbMagazzinoConnection");
                                                                                
    }
    
    @Override
    public 
    List<VistaClientiOrdinazioneProdotto> 
        loadVistaClientiOrdinazioneProdottoByCodiceProdotto(String codiceProdotto) 
                                                throws MagazzinoModelException {

        List<VistaClientiOrdinazioneProdotto> 
                elencoVistaClientiOrdinazioneProdotto = new ArrayList<VistaClientiOrdinazioneProdotto>();

        try (PreparedStatement preparedStatement = 
                this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectVistaClientiOrdinazioneProdottoByCodiceProdotto);) {

            preparedStatement.setString(1, codiceProdotto);

            ResultSet rsSelect = preparedStatement.executeQuery();

            while (rsSelect.next()) {

                Timestamp data = rsSelect.getTimestamp("data_ordine");
                
                LocalDateTime dataOrdine;
                if (rsSelect.wasNull()) {                    
                    dataOrdine = LocalDateTime.of(0, 0, 0, 0, 0, 0);
                } else {
                    dataOrdine =data.toLocalDateTime();
                }            
                
                int quantitaOrdine = rsSelect.getInt("quantita_ordine");
                if (rsSelect.wasNull()) { quantitaOrdine = 0; }

                float prezzoAcquisto = rsSelect.getInt("prezzo_acquisto");
                if (rsSelect.wasNull()) { prezzoAcquisto = 0.0f; }                

                String codiceFiscale = rsSelect.getString("codice_fiscale");
                if (rsSelect.wasNull()) { codiceFiscale = ""; }
                
                String nominativo = rsSelect.getString("nominativo");
                if (rsSelect.wasNull()) { nominativo = ""; }

                VistaClientiOrdinazioneProdotto clienteOrdinazioneProdotto 
                    = new VistaClientiOrdinazioneProdotto(dataOrdine, quantitaOrdine, prezzoAcquisto, codiceFiscale, nominativo);
            
                
                elencoVistaClientiOrdinazioneProdotto.add(clienteOrdinazioneProdotto);
                
            } 
            
        } catch (SQLException sqlException) {

            throw new MagazzinoModelException
                  ("VistaClientiOrdinazioneProdottoDao -> loadVistaClientiOrdinazioneProdottoByCodiceProdotto -> " + sqlException.getMessage());
            
        }

        return elencoVistaClientiOrdinazioneProdotto;

    }
    
    
}
