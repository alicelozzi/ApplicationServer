/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generation.italy.applicationserver.calcolatrice.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Angelo Pasquarelli
 */
public class CalcoloEntityBean {
    private int idCalcolo;                                                      //identificativo univoco del record di calcolo (auto-incrementante)
    private int idUtente;                                                       //identificativo dell'utente che ha svolto il calcolo 
    private LocalDateTime dataOraCalcolo;                                       //data ed ora in cui è stato effettuato il calcolo       
    private float operando1;                                                    //primo operando 
    private float operando2;                                                    //secondo operando  
    private char operazione;                                                    //operazione svolta
    private float risultato;                                                    //risultato del calcolo 
    


    public CalcoloEntityBean(int idCalcolo, int idUtente, LocalDateTime dataOraCalcolo, float operando1, float operando2, char operazione, float risultato) {
        this.idCalcolo = 0;  
        this.idUtente = idUtente;
        this.dataOraCalcolo = dataOraCalcolo;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operazione = operazione;
        this.risultato = risultato;
    }

    public CalcoloEntityBean(int idUtente, float operando1, float operando2, char operazione, float risultato) {
        this.idCalcolo = 0;  
        this.idUtente = idUtente;
        this.dataOraCalcolo = null;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operazione = operazione;
        this.risultato = risultato;
    }
    
    public int getIdCalcolo() {
        return idCalcolo;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public LocalDateTime getDataOraCalcolo() {
        return dataOraCalcolo;
    }

    public float getOperando1() {
        return operando1;
    }

    public float getOperando2() {
        return operando2;
    }

    public char getOperazione() {
        return operazione;
    }

    public float getRisultato() {
        return risultato;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Float.floatToIntBits(this.operando1);
        hash = 67 * hash + Float.floatToIntBits(this.operando2);
        hash = 67 * hash + Objects.hashCode(this.operazione);
        hash = 67 * hash + Float.floatToIntBits(this.risultato);
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
        final CalcoloEntityBean other = (CalcoloEntityBean) obj;
        if (Float.floatToIntBits(this.operando1) != Float.floatToIntBits(other.operando1)) {
            return false;
        }
        if (Float.floatToIntBits(this.operando2) != Float.floatToIntBits(other.operando2)) {
            return false;
        }
        if (Float.floatToIntBits(this.risultato) != Float.floatToIntBits(other.risultato)) {
            return false;
        }
        if (!Objects.equals(this.operazione, other.operazione)) {
            return false;
        }
        return true;
    }
    
    
}
