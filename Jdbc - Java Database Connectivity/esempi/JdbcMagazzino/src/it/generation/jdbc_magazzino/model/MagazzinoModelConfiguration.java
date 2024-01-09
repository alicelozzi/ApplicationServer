package it.generation.jdbc_magazzino.model;



import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jpr2
 */
public 
class MagazzinoModelConfiguration {

    private static Map<String, Object> magazzinoKeyValue;

    static {
        
        magazzinoKeyValue = new HashMap<String, Object>();
        magazzinoKeyValue.put("dbMagazzinoConnection", JdbcConnection.readJdbcConnectionInstance("magazzino", "root", "").getDbConnection());
    }

    public 
    static
    Object getConfigurationValueFromKey(String key) {
        return magazzinoKeyValue.get(key);
    }
    
}
