
package it.generation.jdbc_magazzino.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelConfiguration;
import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.VistaProdottiOrdinazioneCliente;


public class VistaProdottiOrdinazioneClienteDao implements IVistProdottiOrdinazioneClienteDao {
    
    private Connection dbMagazzinoConnection; 
    
    public VistaProdottiOrdinazioneClienteDao() {
        this.dbMagazzinoConnection = (Connection) MagazzinoModelConfiguration.getConfigurationValueFromKey("dbMagazzinoConnection");
                                                                                
    }
    
    @Override
    public 
    List<VistaProdottiOrdinazioneCliente> 
        loadVistaProdottiOrdinazioneClienteByCodiceFiscale(String codiceFiscale) 
                                                throws MagazzinoModelException {

        List<VistaProdottiOrdinazioneCliente> 
                elencoVistaProdottiOrdinazioneCliente = new ArrayList<VistaProdottiOrdinazioneCliente>();

        try (PreparedStatement preparedStatement = 
                this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectVistaProdottiOrdinazioneClienteByCodiceFiscale);) {

            preparedStatement.setString(1, codiceFiscale);

            ResultSet rsSelect = preparedStatement.executeQuery();

            while (rsSelect.next()) {

                Timestamp data = rsSelect.getTimestamp("data_ordine");
                
                LocalDate dataOrdine;
                if (rsSelect.wasNull()) {                    
                    dataOrdine = LocalDateTime.of(0, 0, 0, 0, 0, 0).toLocalDate();
                } else {
                    dataOrdine = data.toLocalDateTime().toLocalDate();
                }            
                
                int quantitaOrdine = rsSelect.getInt("quantita_ordine");
                if (rsSelect.wasNull()) {quantitaOrdine = 0;}

                float prezzoAcquisto = rsSelect.getFloat("prezzo_acquisto");
                if (rsSelect.wasNull()) { prezzoAcquisto = 0.0f; }                

                String codiceProdotto = rsSelect.getString("codice_prodotto");
                if (rsSelect.wasNull()) {codiceProdotto = "";}
                
                String descrizione = rsSelect.getString("descrizione");
                if (rsSelect.wasNull()) {descrizione = "";}

                int quantitaDisponibile = rsSelect.getInt("quantita_disponibile");
                if (rsSelect.wasNull()) {quantitaDisponibile = 0;}
                
                float prezzo = rsSelect.getFloat("prezzo");
                if (rsSelect.wasNull()) { prezzo = 0.0f; }                

                VistaProdottiOrdinazioneCliente prodottoOrdinazioneCliente 
                    = new VistaProdottiOrdinazioneCliente(dataOrdine, quantitaOrdine, prezzoAcquisto, codiceProdotto, descrizione, quantitaDisponibile, prezzo);
            
                
                elencoVistaProdottiOrdinazioneCliente.add(prodottoOrdinazioneCliente);
                
            } 
            
        } catch (SQLException sqlException) {

            throw new MagazzinoModelException
                  ("VistaProdottiOrdinazioneClienteDao -> "
                + "loadVistaTuttiProdottiOrdinatiDaUnCliente -> " 
                + sqlException.getMessage());
            
        }

        return elencoVistaProdottiOrdinazioneCliente;

    }
    
}
