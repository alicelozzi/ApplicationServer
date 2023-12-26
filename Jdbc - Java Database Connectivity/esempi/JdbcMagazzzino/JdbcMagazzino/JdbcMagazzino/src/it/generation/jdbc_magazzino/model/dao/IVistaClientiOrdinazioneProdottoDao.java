//
package it.generation.jdbc_magazzino.model.dao;

import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.VistaClientiOrdinazioneProdotto;


public interface IVistaClientiOrdinazioneProdottoDao {
       
    public 
    List<VistaClientiOrdinazioneProdotto> 
        loadVistaClientiOrdinazioneProdottoByCodiceProdotto(String codiceProdotto) 
                                                            throws MagazzinoModelException;
}
