
package it.generation.jdbc_magazzino.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


public class Ordinazione {
    
    
    /***********************/
    // DEFINIZIONE ATTRIBUTI
    /***********************/
    private LocalDate dataOrdine;
    private String codiceFiscale;
    private String codiceProdotto;
    private int quantitaOrdine;
    private float prezzoAcquisto;

    /***************/
    // COSTRUTTORI
    /***************/
    
    /**
     * Costruttore usato per la insert su tabella ordinaizone (vedi metodo addOrdinazione)
     * La data dell'ordine è inserita automaticamente dal DBMS ed il prezzo di acquisto 
     * è analogo al prezzo del prodotto al momento dell'ordine: il prezzo del prodotto può 
     * variare nel corso del tempo ed è bene sapere il suo prezzo di acquisto alla data dell'ordine.
     * 
     * @param codiceFiscale
     * @param codiceProdotto
     * @param quantitaOrdine 
     */
    public 
    Ordinazione(String codiceFiscale, String codiceProdotto, int quantitaOrdine) {   
        this.codiceFiscale = codiceFiscale;
        this.codiceProdotto = codiceProdotto;
        this.quantitaOrdine = quantitaOrdine;
        this.dataOrdine = LocalDate.now();
        this.prezzoAcquisto = 0.0f;
    }

    /**
     * Costruttore usato nella lettura degli ordini (metodi loadOrdinazioni 
     * con relative SQL-Select su tabella ordinazione).
     * 
     * @param codiceFiscale
     * @param codiceProdotto
     * @param dataOrdine
     * @param quantitaOrdine
     * @param prezzoAcquisto 
     */
    public 
    Ordinazione(String codiceFiscale, String codiceProdotto, LocalDate dataOrdine, int quantitaOrdine, float prezzoAcquisto) {
        this.dataOrdine = dataOrdine;
        this.codiceFiscale = codiceFiscale;
        this.codiceProdotto = codiceProdotto;
        this.quantitaOrdine = quantitaOrdine;
        this.prezzoAcquisto = prezzoAcquisto;
    }

    /********************/
    // GETTERS & SETTERS
    /********************/ 
    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getCodiceProdotto() {
        return codiceProdotto;
    }

    public int getQuantitaOrdine() {
        return quantitaOrdine;
    }

    public float getPrezzoAcquisto() {
        return prezzoAcquisto;
    }
    
    
    /***********************************************************************/
    // METODI DERIVATI DALLA CLASSE OBJECT: toString(), equals(), hashCode()
    /***********************************************************************/ 
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.dataOrdine);
        hash = 59 * hash + Objects.hashCode(this.codiceFiscale);
        hash = 59 * hash + Objects.hashCode(this.codiceProdotto);
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
        final Ordinazione other = (Ordinazione) obj;
        if (!Objects.equals(this.codiceFiscale, other.codiceFiscale)) {
            return false;
        }
        if (!Objects.equals(this.codiceProdotto, other.codiceProdotto)) {
            return false;
        }
        if (!Objects.equals(this.dataOrdine, other.dataOrdine)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ordinazione{" + "dataOrdine=" + dataOrdine + ", codiceFiscale=" + codiceFiscale + ", codiceProdotto=" + codiceProdotto + ", quantitaOrdine=" + quantitaOrdine + '}';
    }
    
    
}
