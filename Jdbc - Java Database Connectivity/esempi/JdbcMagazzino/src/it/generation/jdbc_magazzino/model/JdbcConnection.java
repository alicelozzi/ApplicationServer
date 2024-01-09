package it.generation.jdbc_magazzino.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Imlpementazione della connessione JDBC con uso del pattern singleton.
 * (per evitare di aprire torppe connessioni verso il database, un eeprazione che richiede un certo tempo ed uso di risorse)
 * 
 * @author Angelo Pasquarelli
 */
public class JdbcConnection {

    private Connection dbConnection = null;

    private static JdbcConnection jdbcConnectionSingletonInstance=null;

    private JdbcConnection() {} // costruttore
    
    public 
    static 
    JdbcConnection readJdbcConnectionInstance(String _databaseName
			                                , String _username
			                                , String _password) {

        if(jdbcConnectionSingletonInstance==null) {

            jdbcConnectionSingletonInstance = new JdbcConnection();
            
            String jdbcUrl = "jdbc:mariadb://localhost:3306/" + _databaseName;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
                jdbcConnectionSingletonInstance.dbConnection
                        = DriverManager.getConnection(jdbcUrl,
                                                    _username,
                                                    _password);

            } catch (SQLException sqlEx) {
                jdbcConnectionSingletonInstance.dbConnection = null;
            } catch (ClassNotFoundException classExcpetion) {
                jdbcConnectionSingletonInstance.dbConnection = null;
            }
        }
        
        return jdbcConnectionSingletonInstance;
    }

    public Connection getDbConnection() {
		return dbConnection;
	}



	/** Test per verfifica implementazione connection jdbc con pattern singleton: 
     * le connessioni fanno riferimento allo stesso oggetto dbConnection di tipo Connection**/
    public static void main(String[] args) {
    	JdbcConnection jdbcConnection1 = JdbcConnection.readJdbcConnectionInstance("magazzino", "root", "");
    	JdbcConnection jdbcConnection2 = JdbcConnection.readJdbcConnectionInstance("magazzino", "root", "");
    	JdbcConnection jdbcConnection3 = JdbcConnection.readJdbcConnectionInstance("magazzino", "root", "");
    	JdbcConnection jdbcConnection4 = JdbcConnection.readJdbcConnectionInstance("magazzino", "root", "");
    	JdbcConnection jdbcConnection5 = JdbcConnection.readJdbcConnectionInstance("magazzino", "root", "");
    	JdbcConnection jdbcConnection6 = JdbcConnection.readJdbcConnectionInstance("magazzino", "root", "");
        
        System.out.println(jdbcConnection1);
        System.out.println(jdbcConnection2);
        System.out.println(jdbcConnection3);
        System.out.println(jdbcConnection4);
        System.out.println(jdbcConnection5);
        System.out.println(jdbcConnection6);
    }
    
}
