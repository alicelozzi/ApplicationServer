
package it.generation.jdbc_magazzino.model.dao;

import java.time.LocalDate;
import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.Ordinazione;

public interface IOrdinazioneDao {
    
    public 
    void addOrdinazione(Ordinazione ordinazione) throws MagazzinoModelException;
    
    public 
    Ordinazione loadOrdinazioneByPrimaryKey (String codiceFiscale, String codiceProdotto, LocalDate dataOrdine) throws MagazzinoModelException;
 
    public 
    List<Ordinazione> loadOrdinazioniByCodiceProdotto (String codiceProdotto) throws MagazzinoModelException;
    
    public 
    void removeOrdinazione(LocalDate dataOrdine, String codiceFiscale, String codiceProdotto) throws MagazzinoModelException;
    
}
