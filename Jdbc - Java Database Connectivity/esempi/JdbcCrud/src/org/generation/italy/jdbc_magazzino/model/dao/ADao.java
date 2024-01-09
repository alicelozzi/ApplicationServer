package org.generation.italy.jdbc_magazzino.model.dao;

import java.sql.Connection;
/**
 * Classe derivata dalla generalizzazione dell'attributo jdbcConnectionToDatabase
 */
public 
abstract
class ADao {
	
	protected Connection jdbcConnectionToDatabase;

	public ADao(Connection jdbcConnectionToDatabase) {
		this.jdbcConnectionToDatabase = jdbcConnectionToDatabase;
	}
}
