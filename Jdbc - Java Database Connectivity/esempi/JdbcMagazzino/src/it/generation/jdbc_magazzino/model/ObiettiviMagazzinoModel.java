/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.generation.jdbc_magazzino.model;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.generation.jdbc_magazzino.model.dao.ClienteDao;
import it.generation.jdbc_magazzino.model.dao.ProdottoDao;
import it.generation.jdbc_magazzino.model.dao.VistaClientiOrdinazioneProdottoDao;
import it.generation.jdbc_magazzino.model.dao.VistaProdottiOrdinazioneClienteDao;
import it.generation.jdbc_magazzino.model.entity.Cliente;
import it.generation.jdbc_magazzino.model.entity.Prodotto;
import it.generation.jdbc_magazzino.model.entity.VistaClientiOrdinazioneProdotto;
import it.generation.jdbc_magazzino.model.entity.VistaProdottiOrdinazioneCliente;

/**
 *
 * @author Angelo Pasquarelli
 */
public class ObiettiviMagazzinoModel {

    public static void main(String[] args) {
    	
        try {
            
            List<Prodotto> elencoProdottiPiuOrdinati = calcolaProdottoPiuOrdinato();

            if (elencoProdottiPiuOrdinati.size() > 0) {
                System.out.println("I prodotti più ordinati sono:");
                for (Prodotto prodotto : elencoProdottiPiuOrdinati) {
                    System.out.println(prodotto.toString());
                }
            }

            List<Cliente> elencoClientiConPiuSpesaEffettuata = calcolaClienteConPiuSpesaEffettuata();

            if (elencoClientiConPiuSpesaEffettuata.size() > 0) {
                System.out.println("I clienti che hanno spesso di più sono:");
                for (Cliente cliente : elencoClientiConPiuSpesaEffettuata) {
                    System.out.println(cliente.toString());
                }
            }

        } catch (MagazzinoModelException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    /**
     * Obiettivo: 1. conoscere il prodotto più ordinato
     *
     * Calcola il prodotto più ordinato: il prodotto più ordinato è dato dalla
     * somma della quantità ordinata in ogni ordine (totale delle unità del prodotto ordinate).
     * 
     * In caso di uguaglianza di due o più prodotti, in merito a totale unità ordinate,
     * il metodo ritorna entrmabi i prodotti con le quantità ordinate analoghe. 
     *
     * NOTA: lo stesso calcolo può essere fatto tramite una query SQL
     * 'articolata', ma siamo in un corso Java per cui si usa SQL nella versione
     * base e l'algoritmo per il calcolo è svolto in questo metodo.
     *
     * @return List<Prodotto>, elenco di uno o più prodotti col massimo di unità ordinate
     * @throws MagazzinoModelException
     */
    public 
    static
    List<Prodotto> calcolaProdottoPiuOrdinato() throws MagazzinoModelException {
        
        List<Prodotto> elencoProdottiPiuOrdinati = new ArrayList<Prodotto>();
        
        Map<Prodotto, Integer> keyValueProdottoTotaleunitaordinate = new HashMap<Prodotto, Integer>();

        /**
         * Legge l'elenco dei prodotti presenti nel database e calcola per ogni
         * prodotto quante volte è stato ordinato, salvando il valore
         * nell'Hashmap definito sopra (utilizza la vista: VistaClientiOrdinazioneProdotto).
         */
        ProdottoDao prodottoDao = new ProdottoDao();
        List<Prodotto> elencoProdotti = prodottoDao.loadProdottiAll();
        
        if (elencoProdotti.size() > 0) {
        
            /**
             * Scorre l'elenco dei prodotti presenti nel database e per ciascuno 
             * calcola il totale unità ordinate, popolando così l'hashmap sopra definito.
             */
            
            for (Prodotto prodotto : elencoProdotti) {

                VistaClientiOrdinazioneProdottoDao vistaClientiOrdinazioneProdottoDao = 
                                                new VistaClientiOrdinazioneProdottoDao();
                List<VistaClientiOrdinazioneProdotto> elencoClientiOrdinazioneProdotto =
                    vistaClientiOrdinazioneProdottoDao.loadVistaClientiOrdinazioneProdottoByCodiceProdotto(prodotto.getCodiceProdotto());

                int totaleUnitaOrdinate = 0;

                for (VistaClientiOrdinazioneProdotto clienteOrdinazioneProdotto : elencoClientiOrdinazioneProdotto) {
                    totaleUnitaOrdinate += clienteOrdinazioneProdotto.getQuantitaOrdine();
                }

                keyValueProdottoTotaleunitaordinate.put(prodotto, totaleUnitaOrdinate);
                
            }

            /**
             * Calcola il massimo del totale unità ordinate (scorre l'hashmap)
             */
            int maxTotaleUnitaOrdinate = 0;
            for (Map.Entry<Prodotto, Integer> elemento : keyValueProdottoTotaleunitaordinate.entrySet()) {
                if (elemento.getValue() > maxTotaleUnitaOrdinate) {
                    maxTotaleUnitaOrdinate = elemento.getValue();
                }
            } 
            
            /**
             * Popola l'elenco dei prodotti più ordinati  
             */
            for (Map.Entry < Prodotto, Integer > elemento : keyValueProdottoTotaleunitaordinate.entrySet()) {
                if (elemento.getValue() == maxTotaleUnitaOrdinate) {
                    Prodotto prodotto = elemento.getKey();
                    elencoProdottiPiuOrdinati.add(prodotto);                
                }
            } 
            
        }
        
        return elencoProdottiPiuOrdinati;
    }

    /**
     * Obiettivo: 2. conoscere il cleinte che spende di più
     *
     * Calcola il cliente che spende di più: per ogni cliente rileva il numero 
     * di ordini effettuati e per ogni ordine calcola la spesa come la quantità
     * di prodotto ordinata moltiplicata per il prezzo di acquisto (prezzo del 
     * prodotto al momento in cui è stato effettuato l'ordine).
     * 
     * In caso di uguaglianza di spesa per due o più clienti il metodo può ritornare 
     * più di un cliente che ha speso più degli altri. 
     *
     * NOTA: lo stesso calcolo può essere fatto tramite una query SQL
     * 'articolata', ma siamo in un corso Java per cui si usa SQL nella versione
     * base e l'algoritmo per il calcolo è svolto in questo metodo.
     *
     * @return List<Cliente>, elenco di uno o più clienti col massimo di spesa sostenuta
     * @throws MagazzinoModelException
     */
    public 
    static
    List<Cliente> calcolaClienteConPiuSpesaEffettuata() throws MagazzinoModelException {
        
        List<Cliente> elencoClientiConPiuSpesaEffettuata = new ArrayList<Cliente>();

        
        Map<Cliente, Float> keyValueClienteTotalespeso = new HashMap<Cliente, Float>();

        /**
         * Legge l'elenco dei prodotti presenti nel database e calcola per ogni
         * prodotto quante volte è stato ordinato, salvando il valore
         * nell'Hashmap definito sopra (utilizza la vista: VistaClientiOrdinazioneProdotto).
         */
        ClienteDao clienteDao = new ClienteDao();
        List<Cliente> elencoClienti = clienteDao.loadClientiAll();
        
        if (elencoClienti.size() > 0) {
            /**
             * Scorre l'elenco dei cleinte presenti nel database e per ciascuno 
             * calcola il totale spesa effettuata, popolando così l'hashmap sopra definito.
             */
            
            for (Cliente cliente : elencoClienti) {

                VistaProdottiOrdinazioneClienteDao vistaProdottiOrdinazioneClienteDao = 
                                                new VistaProdottiOrdinazioneClienteDao();
                List<VistaProdottiOrdinazioneCliente> elencoProdottiOrdinazioneCliente =
                    vistaProdottiOrdinazioneClienteDao.loadVistaProdottiOrdinazioneClienteByCodiceFiscale(cliente.getCodiceFiscale());

                float totaleSpesaEffettuata = 0;

                for (VistaProdottiOrdinazioneCliente prodottoOrdinazioneCliente : elencoProdottiOrdinazioneCliente) {
                    totaleSpesaEffettuata += prodottoOrdinazioneCliente.getQuantitaOrdine() * prodottoOrdinazioneCliente.getPrezzoAcquisto();
                }

                keyValueClienteTotalespeso.put(cliente, totaleSpesaEffettuata);
                
            }     
            
            /**
             * Calcola il massimo costo sostenuto da un cliente per la spesa (scorre l'hashmap)
             */
            float maxTotaleSpesaEffettuata = 0;
            for (Map.Entry<Cliente, Float> elemento : keyValueClienteTotalespeso.entrySet()) {
                if (elemento.getValue() > maxTotaleSpesaEffettuata) {
                    maxTotaleSpesaEffettuata = elemento.getValue();
                }
            } 
            
            /**
             * Popola l'elenco dei clienti con maggiore spesa effettuata
             */
            for (Map.Entry<Cliente, Float> elemento : keyValueClienteTotalespeso.entrySet()) {
                if (elemento.getValue() == maxTotaleSpesaEffettuata) {
                    Cliente cliente = elemento.getKey();
                    elencoClientiConPiuSpesaEffettuata.add(cliente);                
                }
            } 
            
        }
        
        return elencoClientiConPiuSpesaEffettuata;
        
    }    
    

}
