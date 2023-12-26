
package it.generation.jdbc_magazzino.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class VistaProdottiOrdinazioneCliente {
    
    /***********************/
    // DEFINIZIONE ATTRIBUTI
    /***********************/
    private LocalDate dataOrdine;
    private int quantitaOrdine;
    private float prezzoAcquisto;
    private String codiceProdotto;
    private String descrizione;
    private int quantitaDisponibile;
    private float prezzo;

    /***************/
    // COSTRUTTORI
    /***************/    
    public VistaProdottiOrdinazioneCliente(LocalDate dataOrdine
                                         , int quantitaOrdine
                                         , float prezzoAcquisto
                                         , String codiceProdotto
                                         , String descrizione
                                         , int quantitaDisponibile
                                         , float prezzo) {
        this.dataOrdine = dataOrdine;
        this.quantitaOrdine = quantitaOrdine;
        this.prezzoAcquisto = prezzoAcquisto;
        this.codiceProdotto = codiceProdotto;
        this.descrizione = descrizione;
        this.quantitaDisponibile = quantitaDisponibile;
        this.prezzo = prezzo;
        
    }

    /********************/
    // GETTERS & SETTERS
    /********************/ 
    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public int getQuantitaOrdine() {
        return quantitaOrdine;
    }

    public float getPrezzoAcquisto() {
        return prezzoAcquisto;
    }

    public String getCodiceProdotto() {
        return codiceProdotto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getQuantitaDisponibile() {
        return quantitaDisponibile;
    }

    public float getPrezzo() {
        return prezzo;
    }
    
    
}
