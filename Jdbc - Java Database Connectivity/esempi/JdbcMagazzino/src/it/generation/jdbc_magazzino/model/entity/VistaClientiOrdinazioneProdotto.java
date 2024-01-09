
package it.generation.jdbc_magazzino.model.entity;

import java.time.LocalDateTime;


public class VistaClientiOrdinazioneProdotto {
    
    /***********************/
    // DEFINIZIONE ATTRIBUTI
    /***********************/
    private LocalDateTime dataOrdine;
    private int quantitaOrdine;
    private String codiceFiscale;
    private String nominativo;
    private float prezzoAcquisto;
    
    /***************/
    // COSTRUTTORI
    /***************/
    
    public VistaClientiOrdinazioneProdotto(LocalDateTime dataOrdine
                                         , int quantitaOrdine
                                         , float prezzoAcquisto
                                         , String codiceFiscale
                                         , String nominativo) {
        this.dataOrdine = dataOrdine;
        this.quantitaOrdine = quantitaOrdine;
        
        this.codiceFiscale = codiceFiscale;
        this.nominativo = nominativo;
    }

    
    /********************/
    // GETTERS & SETTERS
    /********************/    

    public LocalDateTime getDataOrdine() {
        return dataOrdine;
    }

    public int getQuantitaOrdine() {
        return quantitaOrdine;
    }

    public float getPrezzoAcquisto() {
        return prezzoAcquisto;
    }
    
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNominativo() {
        return nominativo;
    }

}
