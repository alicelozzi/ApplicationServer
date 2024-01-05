
package it.generation.jdbc_magazzino.model.dao;



import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.Ordinazione;
import it.generation.jdbc_magazzino.model.entity.Prodotto;
import it.generation.jdbc_magazzino.model.entity.VistaProdottiOrdinazioneCliente;

/**
 * 
 * Classe di verifica dei vincoli applicativi sulle operazioni di scrittura dei dati
 * 
 */
public class Trigger {
    
    /**
     * Metodo trigger per la verifica dei vincoli:
     * 1) un prodotto può essere ordinato solo se vi è la quantità disponibile
     * 2) un cliente non può ordinare più di 2 prodotti (diversi) al giorno 
     * 
     * @param ordinazione oggetto contenente i dettagli dell'ordine
     * @throws MagazzinoModelException 
     */
    public static void checkBeforeInsertOrdinazione(Ordinazione ordinazione) throws MagazzinoModelException {
        
        //VERIFICA DEL VINCOLO: 1) un prodotto può essere ordinato solo se vi è la quantità disponibile
        ProdottoDao prodottoDao = new ProdottoDao();        
        Prodotto prodotto = prodottoDao.loadProdottoByCodiceProdotto(ordinazione.getCodiceProdotto());
        
        if(prodotto == null) {
            throw new MagazzinoModelException ("Trigger -> checkBeforeInsertOrdinazione -> Prodotto non presente!");
        } else {
            
            if (ordinazione.getQuantitaOrdine() > prodotto.getQuantitaDisponibile()) {
                throw new MagazzinoModelException ("Trigger -> checkBeforeInsertOrdinazione -> Quantita ordinata superiore alla quantita disponibile!");
            }
        }
        
        //VERIFICA DEL VINCOLO: 2) un cliente non può ordinare più di 2 prodotti (diversi) al giorno 
        VistaProdottiOrdinazioneClienteDao vistaProdottiOrdinazioneClienteDao =
                                                                new VistaProdottiOrdinazioneClienteDao();
        
        List<VistaProdottiOrdinazioneCliente> elencoVistaProdottiOrdinazioneCliente =
                vistaProdottiOrdinazioneClienteDao.loadVistaProdottiOrdinazioneClienteByCodiceFiscale(ordinazione.getCodiceFiscale());
        
        if (elencoVistaProdottiOrdinazioneCliente.size() >= 2) {
            throw new MagazzinoModelException ("Trigger -> checkBeforeInsertOrdinazione -> un cliente non può ordinare più di 2 prodotti (diversi) al giorno!");
        }
    } 
    
}
