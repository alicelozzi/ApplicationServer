
package it.generation.jdbc_magazzino.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelConfiguration;
import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.Ordinazione;
import it.generation.jdbc_magazzino.model.entity.Prodotto;


public class OrdinazioneDao implements IOrdinazioneDao {
    
    private Connection dbMagazzinoConnection; 
    
    
    public OrdinazioneDao() {
        
        this.dbMagazzinoConnection = (Connection) MagazzinoModelConfiguration.getConfigurationValueFromKey("dbMagazzinoConnection");
                                                                                
    }
    
    private 
    List<Ordinazione> loadOrdinazioneByQuery(PreparedStatement preparedstatement) throws MagazzinoModelException {

        List<Ordinazione> elencoOrdinazione = new ArrayList<Ordinazione>();

        try {

            ResultSet rsSelect
                    = preparedstatement.executeQuery();

            while (rsSelect.next()) {
                              
                String codFiscale = rsSelect.getString("codice_fiscale");
                if (rsSelect.wasNull()) {codFiscale = ""; }

                String codProdotto = rsSelect.getString("codice_prodotto");
                if (rsSelect.wasNull()) {codProdotto = "";}

                LocalDate dataOrdine 
                        =rsSelect.getDate("data_ordine").toLocalDate();
                if (rsSelect.wasNull()) {dataOrdine = LocalDate.of(0, 0, 0);}
                
                int quantitaOrdine
                        = rsSelect.getInt("quantita_ordine");
                if (rsSelect.wasNull()) {quantitaOrdine = 0;}

                float prezzoAcquisto
                        = rsSelect.getInt("prezzo_acquisto");
                if (rsSelect.wasNull()) {prezzoAcquisto = 0.0f;}
                
                Ordinazione ordinazione = new Ordinazione(codFiscale, codProdotto, dataOrdine, quantitaOrdine, prezzoAcquisto);

                elencoOrdinazione.add(ordinazione);

            }

        } catch (SQLException sqlException) {

             throw new MagazzinoModelException("OrdinazioneDao -> loadOrdinazione -> " + sqlException.getMessage());

        }
        
        return elencoOrdinazione;

    }

    
    
    @Override
    public 
    Ordinazione loadOrdinazioneByPrimaryKey (String codiceFiscale, String codiceProdotto, LocalDate dataOrdine) throws MagazzinoModelException {
        
        Ordinazione ordinazione = null;
        
        try {
            List<Ordinazione> elencoOrdinazioni = new ArrayList<Ordinazione>();
            
            PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectFromOrdinazioneByPrimaryKey);

            preparedStatement.setString(1, codiceFiscale);
            preparedStatement.setString(2, codiceProdotto);
            preparedStatement.setDate(3, Date.valueOf(dataOrdine));
                      
            elencoOrdinazioni = loadOrdinazioneByQuery(preparedStatement);                                        
            if (elencoOrdinazioni.size() == 1) {
                ordinazione = elencoOrdinazioni.get(0);
            }
        } catch (SQLException sqlException) {                                  
           
            throw new MagazzinoModelException("OrdinazioneDao -> loadOrdinazioneByPrimaryKey -> " + sqlException.getMessage());
        }

        return ordinazione;

    }
    
    @Override
    public 
    List<Ordinazione> loadOrdinazioniByCodiceProdotto (String codiceProdotto) throws MagazzinoModelException {
        
        List<Ordinazione> elencoOrdinazione = new ArrayList<Ordinazione>();
        
       try (
            PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectOrdinazioniWhereCodiceProdotto);) {

            
            preparedStatement.setString(1, codiceProdotto);
           
            elencoOrdinazione = loadOrdinazioneByQuery(preparedStatement);                                        

            
        } catch (SQLException sqlException) {                                  
           
            throw new MagazzinoModelException("OrdinazioneDao -> loadAllOrdinazioni -> " + sqlException.getMessage());
        }

        return elencoOrdinazione;

    }
    
    public 
    List<Ordinazione> loadAllOrdinazione () throws MagazzinoModelException {
        
        List<Ordinazione> elencoOrdinazione = new ArrayList<Ordinazione>();
        
       try (
            PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement("SELECT * FROM ordinazione");) {
                      
            elencoOrdinazione = loadOrdinazioneByQuery(preparedStatement);                                        

            
        } catch (SQLException sqlException) {                                  
           
            throw new MagazzinoModelException("OrdinazioneDao -> loadAllOrdinazione -> " + sqlException.getMessage());
        }

        return elencoOrdinazione;

    }
    
    @Override
    public 
    void addOrdinazione(Ordinazione ordinazione) throws MagazzinoModelException {
        
        try { 
            
            this.dbMagazzinoConnection.setAutoCommit(false);
            
            Trigger.checkBeforeInsertOrdinazione(ordinazione);

            ProdottoDao prodottoDao = new ProdottoDao();        
            Prodotto prodotto = prodottoDao.loadProdottoByCodiceProdotto(ordinazione.getCodiceProdotto());
            
            PreparedStatement preparedStatement = dbMagazzinoConnection.prepareStatement(QueryCatalog.insertOrdinazione);
            PreparedStatement preparedStatement2 = dbMagazzinoConnection.prepareStatement(QueryCatalog.updateQuantitaDisponibileProdottoByCodiceProdotto);
           
            preparedStatement.setString(1, ordinazione.getCodiceFiscale());            
            preparedStatement.setString(2, ordinazione.getCodiceProdotto()); 
            preparedStatement.setInt(3, ordinazione.getQuantitaOrdine());
            preparedStatement.setFloat(4, prodotto.getPrezzo());
            
            int nuovaQuantitaDisponibileProdotto = prodotto.getQuantitaDisponibile() - ordinazione.getQuantitaOrdine();
            
            preparedStatement2.setInt(1,nuovaQuantitaDisponibileProdotto);
            preparedStatement2.setString(2,ordinazione.getCodiceProdotto());
            

            
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            
            this.dbMagazzinoConnection.commit();
            
            this.dbMagazzinoConnection.setAutoCommit(true);
    
        } catch (SQLException sqlException) {
        	
            throw new MagazzinoModelException("OrdinazioneDao -> addOrdinazione -> " + sqlException.getMessage());
            //rollback
        }
        
    }    
    
    @Override
    public 
    void removeOrdinazione(LocalDate dataOrdine, String codiceFiscale, String codiceProdotto) throws MagazzinoModelException {
        
        try (
            
            PreparedStatement preparedStatemente = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.deleteOrdinazioneWhereData);) {
        
            preparedStatemente.setDate(1, Date.valueOf(dataOrdine));
            preparedStatemente.setString(2, codiceFiscale);
            preparedStatemente.setString(3, codiceProdotto);
            
            preparedStatemente.executeUpdate();
        }
        catch (SQLException sqlException) {
                       
            throw new MagazzinoModelException("OrdinazioneDao -> removeOrdinazione -> " + sqlException.getMessage());
            
        }
        
    }
    
}
