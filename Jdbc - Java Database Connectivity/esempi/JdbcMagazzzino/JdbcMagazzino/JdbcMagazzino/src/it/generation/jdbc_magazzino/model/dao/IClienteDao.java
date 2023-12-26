
package it.generation.jdbc_magazzino.model.dao;



import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.Cliente;


public interface IClienteDao {
    
    public 
    Cliente loadClienteByCodiceFiscale (String codiceFiscale) throws MagazzinoModelException;

    public 
    List<Cliente> loadClienteByNominativo (String nominativo) throws MagazzinoModelException;

    public 
    void addCliente(Cliente cliente) throws MagazzinoModelException;
    
    public 
    void removeCliente(String codiceFiscale) throws MagazzinoModelException;

}
