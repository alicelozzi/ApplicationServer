/**
 * Classe per operazioni CRUD (Create/Read/Update/Delete) sul database, invio di comandi SQL
 * 
 * @author Angelo Pasquarelli
 *
 */

import java.sql.Connection;														//classe istanziata per mantenere il riferimento alla connessione stabilita verso il database 
import java.sql.DriverManager;													//classe di riferimento per l'uso del JDBC driver installato come dipendenza nel pom.xml di Maven										
import java.sql.SQLException;													//imposta classe SqlException per gestione errori nella comunicazione col database

public class JdbcCrud {

	
	public static void main(String[] args) {									//metodo di accesso all'applicazione da parte della Java Virtual Machine (vedi slide architettura Java)
		
		
		// Connessione al database
		String databaseName = "magazzino";										//nome del database a cui connettersi
		String dbmsUserName = "root";											//nome utente configurato nel dbms
		String dbmsPassword = "";												//password utente configurato nel dbms
		String jdbcUrl = "jdbc:mariadb://localhost:3306/" + databaseName;		
		
		try {																	//prova ad eseguire le istruzioni interne al blocco try-catch
			
			Connection jdbcConnectionToDatabase = 								//esegue la connessione al dbms con riferimento al database, se fallisce genera eccezzione SQLException (effettuare il debugging per verificarlo)
					DriverManager.getConnection(jdbcUrl
											  , dbmsUserName
											  , dbmsPassword);
			
			System.out.println("Connessione al database magazzino riuscita!");	//visualizza messaggio per avvenuta connessione al database
			
	
		} catch (SQLException e) {												//errore di tipo classe SQLException
			// TODO Auto-generated catch block
			e.printStackTrace();												//stampa la pila (stack) degli errori, dal più recente al meno recente
		}

		
	}
	
	
}
