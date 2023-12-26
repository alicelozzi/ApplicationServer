
package it.generation.jdbc_magazzino.model.dao;

import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.Prodotto;

public interface IProdottoDao {
    
    public 
    Prodotto loadProdottoByCodiceProdotto (String codiceProdotto) throws MagazzinoModelException;   
    
    public 
    List<Prodotto> loadProdottiByDescrizione(String descrizione) throws MagazzinoModelException;
    
    public List<Prodotto> loadProdottiAll() throws MagazzinoModelException;    
            
    public 
    void addProdotto(Prodotto prodotto) throws MagazzinoModelException;
    
    public 
    void removeProdotto(String codiceProdotto) throws MagazzinoModelException;
    
}
