/**
 * Classe per operazioni CRUD (Create/Read/Update/Delete) sul database, invio di comandi SQL
 * 
 * @author Angelo Pasquarelli
 *
 */

import java.sql.Connection;														//classe istanziata per mantenere il riferimento alla connessione stabilita verso il database 
import java.sql.DriverManager;													//classe di riferimento per l'uso del JDBC driver installato come dipendenza nel pom.xml di Maven										
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;													//imposta classe SqlException per gestione errori nella comunicazione col database
import java.util.ArrayList;
import java.util.List;


public class JdbcCrudMain {

	public static void main(String[] args) {
		
		
		// Connessione al database
		String databaseName = "magazzino";										//nome del database a cui connettersi
		String dbmsUserName = "root";											//nome utente configurato nel dbms
		String dbmsPassword = "";												//password utente configurato nel dbms
		String jdbcUrl = "jdbc:mariadb://localhost:3306/" + databaseName;		
		
		try {																	//prova ad eseguire le istruzioni interne al blocco try-catch

			/****************************************************************************/
			/*					CONNESSIONE AL DATABASE									*/
			/****************************************************************************/
			Connection jdbcConnectionToDatabase = 								//esegue la connessione al dbms con riferimento al database, se fallisce genera eccezzione SQLException (effettuare il debugging per verificarlo)
					DriverManager.getConnection(jdbcUrl
											  , dbmsUserName
											  , dbmsPassword);
			
			System.out.println("Connessione al database magazzino riuscita!");	//visualizza messaggio per avvenuta connessione al database
			

//			/*************************************************************************************/
//			/* 	    ESECUZIONE DELLA QUERY DI SELECT CON CONDIZIONE WHERE SU CHIAVE PRIMARIA (=) */
//			/*************************************************************************************/
//			
//		    String selectFromClienteByCodiceFiscale =							//imposta il testo del comando SQL da eseguire
//		    			" SELECT codice_fiscale, nominativo "
//		              + "   FROM cliente                    "
//		              + "  WHERE cliente.codice_fiscale = ? ";
//
//		    String parametroCodiceFiscale = "BRNGRD0123456789";					//imposta il valore del parametro codice fiscale
//		    
//            PreparedStatement preparedStatement =								//predispone JDBC per l'invio al database del comando SQL  
//            		jdbcConnectionToDatabase.prepareStatement(selectFromClienteByCodiceFiscale);
//            preparedStatement.setString(1, parametroCodiceFiscale);				//imposta il valore del parametro di ricerca codice fiscale (parametro String)
//            
//            ResultSet rsSelect
//	            = preparedStatement.executeQuery();								//esegue la query di SELECT e si predisone a leggere i risutlati presenti in memoria nel DBMS
//
//            Cliente cliente= null;												//istanza dell'entity-bean di tipo classe Cliente
//            
//		    if (rsSelect.next()) {												//fino a che ci sono risutalti da leggere
//		
//		        String codFiscale = rsSelect.getString("codice_fiscale");		//lettura del valore del campo codice_fiscale
//		        if (rsSelect.wasNull()) {
//		            codFiscale = "";
//		        }
//		
//		        String nominativo												//lettura del valore del campo 'nominativo'
//		                = rsSelect.getString("nominativo");
//		        if (rsSelect.wasNull()) {
//		            nominativo = "";
//		        }
//		
//		        cliente = new Cliente(codFiscale, nominativo);					//istanzia un oggetto di tipo classe Cliente inizializzandolo con i valori letti dal record
//		
//		    }            
//		    
//		    if (cliente != null) {
//		    	System.out.println("Dati del cliente letto=> " + cliente.toString());
//		    }
//		    else {
//		    	System.out.println("Il cliente ricercato non è presente!!!");
//		    }
//
//		    
//			/***********************************************************************/
//			/* 	    ESECUZIONE DELLA QUERY DI SELECT CON CONDIZIONE WHERE CON LIKE */
//			/***********************************************************************/
//		    
//		    String selectFromClienteByNominativo =								//imposta il testo del comando SQL da eseguire
//	    			" SELECT codice_fiscale, nominativo "
//	              + "   FROM cliente                    "
//	              + "  WHERE cliente.nominativo LIKE ? 	";
//
//		    String parametroNominativo = "%Pers%";								//imposta il valore del parametro codice fiscale
//		    
//	        PreparedStatement preparedStatementLike =								//predispone JDBC per l'invio al database del comando SQL  
//	        		jdbcConnectionToDatabase.prepareStatement(selectFromClienteByNominativo);
//	        preparedStatementLike.setString(1, parametroNominativo);//imposta il valore del parametro di ricerca codice fiscale (parametro String)
//	        
//	        ResultSet rsSelectLike
//	            = preparedStatementLike.executeQuery();							//esegue la query di SELECT e si predisone a leggere i risutlati presenti in memoria nel DBMS
//	
//	        List<Cliente> elencoClienti = new ArrayList<>();					//istanza dell'elenco di entity-bean di tipo classe Cliente
//	        
//		    while (rsSelectLike.next()) {										//fino a che ci sono risutalti da leggere
//		
//		        String codFiscale = rsSelectLike.getString("codice_fiscale");	//lettura del valore del campo codice_fiscale
//		        if (rsSelectLike.wasNull()) {
//		            codFiscale = "";
//		        }
//		
//		        String nominativo												//lettura del valore del campo 'nominativo'
//		                = rsSelectLike.getString("nominativo");
//		        if (rsSelectLike.wasNull()) {
//		            nominativo = "";
//		        }
//		
//		        Cliente clienteLetto = new Cliente(codFiscale, nominativo);		//istanzia un oggetto di tipo classe Cliente inizializzandolo con i valori letti dal record
//		        elencoClienti.add(clienteLetto);								//aggiunge all'elenco l'oggetto istanziato
//		    }            
//		    
//		    if (elencoClienti.size() != 0) {									//clienti trovati? 
//		    	System.out.println("Dati dei clienti trovati=> \n " + elencoClienti.toString());
//	    																		//visualizza il contenuto dell'elenco 
//		    }
//		    else {
//		    	System.out.println("Nessun cliente trovato!!!");
//		    																	//visualizza il messaggio per elenco vuoto 
//		    }
		    

			/************************************************************************/
			/* 	    		ESECUZIONE DELLA QUERY DI INSERT CLIENTE 				*/
			/************************************************************************/		    

		    Cliente clienteToInsert = new Cliente("GVNFRN0123456789", "Giovanni Furino");
		    
		    String insertCliente =
		            "INSERT INTO cliente (codice_fiscale, nominativo) VALUES (?, ?) ";

	        PreparedStatement preparedStatementInsertCliente =								//predispone JDBC per l'invio al database del comando SQL  
	        		jdbcConnectionToDatabase.prepareStatement(insertCliente);
		    
            preparedStatementInsertCliente.setString(1, clienteToInsert.getCodiceFiscale());
            preparedStatementInsertCliente.setString(2, clienteToInsert.getNominativo());            
            
            preparedStatementInsertCliente.executeUpdate();
		    
	    	System.out.println("Cliente inserito=> " + clienteToInsert.toString());
            
		} catch (SQLException e) {												//errore di tipo classe SQLException
			// TODO Auto-generated catch block
			//e.printStackTrace();												//stampa la pila (stack) degli errori, dal più recente al meno recente
			System.out.println(e.getMessage()); 								//stampa lo sepcifico messaggio di errore
			//throw new JdbcMagazzinoException();
		}
		
//		} catch (NullPointerException e1) {
//			throw new JdbcMagazzinoException();
//		}

		
	}
	
	
}
