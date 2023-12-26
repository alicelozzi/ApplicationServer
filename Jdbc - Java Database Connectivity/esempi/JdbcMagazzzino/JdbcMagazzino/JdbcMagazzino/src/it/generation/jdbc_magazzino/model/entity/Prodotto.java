
package it.generation.jdbc_magazzino.model.entity;

import java.util.Objects;


public class Prodotto {
    
    /***********************/
    // DEFINIZIONE ATTRIBUTI
    /***********************/
    private String codiceProdotto;
    private String descrizione;
    private float prezzo;
    private int quantitaDisponibile;

    /***************/
    // COSTRUTTORI
    /***************/
    
    public Prodotto(String codiceProdotto, String descrizione, int quantitaDisponibile, float prezzo) {
        this.codiceProdotto = codiceProdotto;
        this.descrizione=descrizione;
        this.quantitaDisponibile = quantitaDisponibile;
        this.prezzo = prezzo;
    }


    /********************/
    // GETTERS & SETTERS
    /********************/    
    public String getCodiceProdotto() {
        return codiceProdotto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public int getQuantitaDisponibile() {
        return quantitaDisponibile;
    }

    

    /***********************************************************************/
    // METODI DERIVATI DALLA CLASSE OBJECT: toString(), equals(), hashCode()
    /***********************************************************************/ 
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.codiceProdotto);
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
        final Prodotto other = (Prodotto) obj;
        if (!Objects.equals(this.codiceProdotto, other.codiceProdotto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Prodotto{" + "codiceProdotto=" + codiceProdotto + ", descrizione=" + descrizione + ", prezzo=" + prezzo + ", quantitaDisponibile=" + quantitaDisponibile + '}';
    }
 



    

}
