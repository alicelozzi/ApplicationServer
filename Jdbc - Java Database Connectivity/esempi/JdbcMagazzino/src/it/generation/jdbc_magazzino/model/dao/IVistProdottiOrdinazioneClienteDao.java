
package it.generation.jdbc_magazzino.model.dao;



import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.VistaProdottiOrdinazioneCliente;


public interface IVistProdottiOrdinazioneClienteDao {
    
    public 
    List<VistaProdottiOrdinazioneCliente> 
        loadVistaProdottiOrdinazioneClienteByCodiceFiscale(String codiceFiscale) 
                                                            throws MagazzinoModelException;
    
}
