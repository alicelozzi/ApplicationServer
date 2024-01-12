package org.generation.italy.banca.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.generation.italy.banca.model.dao.ClienteDao;
import org.generation.italy.banca.model.dao.ContoDao;
import org.generation.italy.banca.model.dao.MovimentoDao;
import org.generation.italy.banca.model.dao.QueryCatalog;
import org.generation.italy.banca.model.dao.Trigger;
import org.generation.italy.banca.model.entity.Cliente;
import org.generation.italy.banca.model.entity.Conto;
import org.generation.italy.banca.model.entity.Movimento;

public class TestJdbcBanca {

	Connection dbConnection;
	private ClienteDao clienteDao;
	private ContoDao contoDao;
	private MovimentoDao movimentoDao;
	
	
	public TestJdbcBanca() 
					throws BancaModelException {

		this.dbConnection = 
				JdbcConnection
					.readJdbcConnectionInstance("mariadb", "org.mariadb.jdbc.Driver", "localhost", "3306", "banca", "root", "")
						.getDbConnection();
		
		this.clienteDao = new ClienteDao(this.dbConnection); 
		this.contoDao = new ContoDao(this.dbConnection); 
		this.movimentoDao = new MovimentoDao(this.dbConnection); 
		
		Trigger.clienteDao = this.clienteDao;
		Trigger.movimentoDao = this.movimentoDao;
		Trigger.contoDao = this.contoDao;
		
	}

	/**
	 * Viene inizializzato il database rimuovendo eventuali dati presenti nelle tabelle
	 * 
	 * Le query qui presenti sono potenzialmente dannose ed è per questo che non sono presenti nel query catalog (classe QueryCatalog).
	 * 
	 * Sono qui presenti solo ed esclusivametne ai fini della predisposizone del test di funzionamento del componente model che approfondiremo nelle prossime lezioni
	 * 
	 * I comandi SQL-DELETE sono svolti a partire dalla tabella movimento. Infatti, questa è tabella correlata (join) con la tabella conto sul campo iban.
	 * A seguire, anche conto è tabella correlata con cliente sul campo codice_fiscale, per cui si elimineranno prima i record della tabella conto e successivamente quelli di cliente.
	 *    
	 */
	
	private 
	void clearDatabase() 
					throws BancaModelException {

       try {           
            
   			//DELETE FROM movimento
            PreparedStatement preparedStatement1 = 
        		this.dbConnection.prepareStatement(" DELETE FROM movimento ");
            preparedStatement1.executeUpdate();
            
   			//DELETE FROM conto
            PreparedStatement preparedStatement2 = 
            		this.dbConnection.prepareStatement(" DELETE FROM conto ");
            preparedStatement2.executeUpdate();

   			//DELETE FROM cliente
            PreparedStatement preparedStatement3 = 
            		this.dbConnection.prepareStatement(" DELETE FROM cliente ");
            preparedStatement3.executeUpdate();
            
        } catch (SQLException sqlException) {
            throw new BancaModelException("TestJdbcBanca -> clearDatabase -> " + sqlException.getMessage());
        }
		
		
		//DELETE FROM conto 
		//DELETE FROM cliente 
	}
	
	/**
	 * Metodo usato per popolare il database con valori scelti dal programmatore ai fini del test.
	 * Richiama clearDatabase() per garantirsi che il database sia senza dati
	 */
	public 
	void popolaDatabase() 
			throws BancaModelException {

		clearDatabase();
		
		//popolamento tabella cliente
		clienteDao.addCliente(new Cliente("BNCFRL0123456789","Bianca Fiorelli"));
		clienteDao.addCliente(new Cliente("GTNBGD0123456789","Gaetano Badalamenti"));
		clienteDao.addCliente(new Cliente("MRRGVN0123456789","Giovanni Marrone"));
		clienteDao.addCliente(new Cliente("SVCNGB0123456789","Anselmo Persichetti"));
		clienteDao.addCliente(new Cliente("VLDBCC0123456789","Vladimiro Bocci"));
		
		//popolamento tabella conto
		contoDao.addConto(new Conto("DEaa0123456789012345678901234567","MRRGVN0123456789", "GBP", 345.0f));
		contoDao.addConto(new Conto("DEbb0123456789012345678901234567", "MRRGVN0123456789", "GBP",523.0f));
		contoDao.addConto(new Conto("ESaa0123456789012345678901234567","VLDBCC0123456789", "EUR",255.0f));
		contoDao.addConto(new Conto("EScc0123456789012345678901234567", "GTNBGD0123456789", "EUR",150.0f));
		contoDao.addConto(new Conto ("ESdd0123456789012345678901234567", "SVCNGB0123456789", "EUR",345.0f));
		contoDao.addConto(new Conto ("ESee0123456789012345678901234567", "SVCNGB0123456789", "USD", 150.0f));
		contoDao.addConto(new Conto ("FRaa0123456789012345678901234567", "MRRGVN0123456789", "GBP", 987.0f));
		
		contoDao.addConto(new Conto ("ITbb0123456789012345678901234567", "VLDBCC0123456789", "EUR",500.0f));
		
		contoDao.addConto(new Conto ("ITdd0123456789012345678901234567","VLDBCC0123456789", "EUR",955.0f));
		contoDao.addConto(new Conto ("ITee0123456789012345678901234567", "GTNBGD0123456789", "EUR", 230.0f));
		contoDao.addConto(new Conto("ITff0123456789012345678901234567", "SVCNGB0123456789", "EUR",1000.0f));

		//popolamento tabella movimento

		movimentoDao.addMovimento(new Movimento("ITbb0123456789012345678901234567", 850.0f, "P"));
		
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 450.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ITff0123456789012345678901234567", 555.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ITff0123456789012345678901234567", 555.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESee0123456789012345678901234567", 3650.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ITff0123456789012345678901234567", 9807.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 1200.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ITbb0123456789012345678901234567", 789.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESdd0123456789012345678901234567", 660.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 1345.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESee0123456789012345678901234567", 9800.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 65.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ITee0123456789012345678901234567", 1876.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 598.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 1234.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ITff0123456789012345678901234567", 110.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESee0123456789012345678901234567", 2400.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 450.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ITff0123456789012345678901234567", 555.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ITff0123456789012345678901234567", 555.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESee0123456789012345678901234567", 3650.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ITff0123456789012345678901234567", 9807.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 1200.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ITbb0123456789012345678901234567", 789.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESdd0123456789012345678901234567", 660.0f, "P"));
//		movimentoDao.addMovimento(new Movimento( "ESaa0123456789012345678901234567", 1345.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESee0123456789012345678901234567", 9800.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 65.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ITee0123456789012345678901234567", 1876.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 598.0f, "V"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 1234.0f, "P"));
//		movimentoDao.addMovimento(new Movimento("ITff0123456789012345678901234567", 110.0f,"V"));
//		movimentoDao.addMovimento(new Movimento("ESee0123456789012345678901234567", 2400.0f,"P"));
//		movimentoDao.addMovimento(new Movimento("ESaa0123456789012345678901234567", 225.0f,"V"));
//		movimentoDao.addMovimento(new Movimento("DEaa0123456789012345678901234567", 678.0f,"V"));
//		movimentoDao.addMovimento(new Movimento("DEaa0123456789012345678901234567", 543.0f,"V"));
//		movimentoDao.addMovimento(new Movimento("DEaa0123456789012345678901234567", 963.0f,"V"));
//		movimentoDao.addMovimento(new Movimento("DEaa0123456789012345678901234567", 367.0f,"P"));
//		movimentoDao.addMovimento(new Movimento("DEaa0123456789012345678901234567", 1543.0f,"P"));
//		movimentoDao.addMovimento(new Movimento("DEbb0123456789012345678901234567", 78.0f,"V"));
//		movimentoDao.addMovimento(new Movimento("DEbb0123456789012345678901234567", 133.0f,"V"));
//		movimentoDao.addMovimento(new Movimento("DEbb0123456789012345678901234567", 6923.0f,"V"));
//		movimentoDao.addMovimento(new Movimento("DEbb0123456789012345678901234567", 635.0f,"P"));
//		movimentoDao.addMovimento(new Movimento("DEbb0123456789012345678901234567", 4990.0f,"P"));		
		
		
	}		

	
	
	public static void main(String[] args) {
		
		
		try {
			TestJdbcBanca testJdbcBanca =  new TestJdbcBanca();
			testJdbcBanca.popolaDatabase();
			
			System.out.println("Database 'banca' popolato corretamente");
			
			
			
			
//			//stampare elenco movimenti attuale pre-eliminazione
//			List<Movimento> elencoMovimentiPreEliminazione =
//					testJdbcBanca.movimentoDao.loadMovimentoByIban("DEbb0123456789012345678901234567");
//			System.out.println("elenco movimenti pre-eliminazione");
//			System.out.println(elencoMovimentiPreEliminazione.toString());
//
//			//effettuare l'eliminazione
//			testJdbcBanca.movimentoDao.removeMovimentoByIban("DEbb0123456789012345678901234567");
//			
//			//stampare elenco movimenti post-eliminazione
//			//stampare elenco movimeti dopo eliminazine conti senza movimento
//			List<Movimento> elencoMovimentiPostEliminazione =
//					testJdbcBanca.movimentoDao.loadMovimentoByIban("DEbb0123456789012345678901234567");
//			
//			System.out.println("elenco movimenti post-eliminazione");
//			System.out.println(elencoMovimentiPostEliminazione.toString());
			
		} catch (BancaModelException bancaModelException) {
			System.out.println(bancaModelException.getMessage());
		} 
	}
}
