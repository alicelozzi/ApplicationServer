package org.generation.italy.jdbc_magazzino.model.entity;


import java.util.Objects;


/**
 * Classe entity-bean Cliente che effettua il mapping del record della tabella Cliente
 * 
 * @author Angelo Pasquarelli
 */

public class Cliente {
    
    /***********************/
    // DEFINIZIONE ATTRIBUTI
    /***********************/
    private String codiceFiscale;
    private String nominativo;

    /***************/
    // COSTRUTTORI
    /***************/
    
    public Cliente(String codiceFiscale, String nominativo) {
        this.codiceFiscale = codiceFiscale;
        this.nominativo = nominativo;
    }

    /********************/
    // GETTERS & SETTERS
    /********************/
    
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNominativo() {
        return nominativo;
    }

    
    /***********************************************************************/
    // METODI DERIVATI DALLA CLASSE OBJECT: toString(), equals(), hashCode()
    /***********************************************************************/  
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.codiceFiscale);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.codiceFiscale, other.codiceFiscale)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "codiceFiscale=" + codiceFiscale + ", nominativo=" + nominativo + '}';
    }

    
  

}
