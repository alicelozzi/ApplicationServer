
package it.generation.jdbc_magazzino.model.dao;

import it.generation.jdbc_magazzino.model.MagazzinoModelConfiguration;
import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDao implements IClienteDao {
    
    private Connection dbMagazzinoConnection; 
    
    public ClienteDao() {
        this.dbMagazzinoConnection = (Connection) MagazzinoModelConfiguration.getConfigurationValueFromKey("dbMagazzinoConnection");
    }
    
    
    private 
    List<Cliente> loadClientiByQuery(PreparedStatement preparedstatement) throws MagazzinoModelException {

        List<Cliente> elencoClienti = new ArrayList<Cliente>();

        try {

            ResultSet rsSelect
                    = preparedstatement.executeQuery();

            while (rsSelect.next()) {

                String codFiscale = rsSelect.getString("codice_fiscale");
                if (rsSelect.wasNull()) {
                    codFiscale = "";
                }

                String nominativo
                        = rsSelect.getString("nominativo");
                if (rsSelect.wasNull()) {
                    nominativo = "";
                }

                Cliente cliente = new Cliente(codFiscale, nominativo);

                elencoClienti.add(cliente);

            }

        } catch (SQLException sqlException) {

            throw new MagazzinoModelException("ClienteDao -> loadCliente -> " + sqlException.getMessage());

        }
        
        return elencoClienti;

    }

    
    @Override
    public 
    Cliente loadClienteByCodiceFiscale (String codiceFiscale) 
    											throws MagazzinoModelException {
        
        Cliente cliente = null;
        
        try {
            List<Cliente> elencoClienti = new ArrayList<Cliente>();
                
            PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectFromClienteByCodiceFiscale);

            preparedStatement.setString(1, codiceFiscale);
                      
            elencoClienti = loadClientiByQuery(preparedStatement);                                        

            if (elencoClienti.size() == 1) {
                cliente = elencoClienti.get(0);
            }
        } catch (SQLException sqlException) {                                  
           
            throw new MagazzinoModelException("ClienteDao -> loadCittadinoByCodFiscale -> " + sqlException.getMessage());
        }

        return cliente;

    }
    
    @Override
    public 
    List<Cliente> loadClienteByNominativo (String nominativo) throws MagazzinoModelException {
        
        List<Cliente> elencoClienti = new ArrayList<Cliente>();
        
       try (
             PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectFromClienteByNominativo);) {

             preparedStatement.setString(1,"%" + nominativo + "%");
           
            elencoClienti = loadClientiByQuery(preparedStatement);                                        

            
        } catch (SQLException sqlException) {                                  
           
            throw new MagazzinoModelException("ClienteDao -> loadClienteByNominativo -> " + sqlException.getMessage());
        }

        return elencoClienti;

    }
    
    public 
    List<Cliente> loadClientiAll() throws MagazzinoModelException {
        
        List<Cliente> elencoClienti = new ArrayList<Cliente>();
        
       try (
             PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement("SELECT * FROM cliente");) {

            elencoClienti = loadClientiByQuery(preparedStatement);                                        

            
        } catch (SQLException sqlException) {                                  
           
            throw new MagazzinoModelException("ClienteDao -> loadAllCliente -> " + sqlException.getMessage());
        }

        return elencoClienti;

    }
    
    
    @Override
    public 
    void addCliente(Cliente cliente) throws MagazzinoModelException {
        
        try {           
            
            PreparedStatement preparedStatement = dbMagazzinoConnection.prepareStatement(QueryCatalog.insertCliente);
            
            preparedStatement.setString(1, cliente.getCodiceFiscale());
            preparedStatement.setString(2, cliente.getNominativo());            
            
            preparedStatement.executeUpdate();
    
        }catch (SQLException sqlException) {
        	
            throw new MagazzinoModelException("ClienteDao -> addCliente -> " + sqlException.getMessage());
            
        }
        
    }
        
    @Override
    public 
    void removeCliente(String codiceFiscale) throws MagazzinoModelException {
        
        try (
            
            PreparedStatement preparedStatemente = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.deleteFromClienteByCodiceFiscale);) {
        
            preparedStatemente.setString(1, codiceFiscale);
            
            preparedStatemente.executeUpdate();
        }
        catch (SQLException sqlException) {
                       
            throw new MagazzinoModelException("ClienteDao -> removeCliente -> " + sqlException.getMessage());
            
        }
        
    }
	
    
}
