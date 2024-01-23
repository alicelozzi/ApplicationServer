package org.generation.italy.applicationserver.calcolatrice.model;


import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.generation.italy.applicationserver.calcolatrice.model.JdbcConnection;

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
class CalcolatriceModelConfiguration {

    private static Map<String, Object> calcolatriceKeyValue;

    static {
        
        calcolatriceKeyValue = new HashMap<String, Object>();
        calcolatriceKeyValue.put("dbConnection", JdbcConnection.getJdbcConnection("calcolatrice", "root", ""));
    }

    public 
    static
    Object getConfigurationValueFromKey(String key) {
        return calcolatriceKeyValue.get(key);
    }
    
}
