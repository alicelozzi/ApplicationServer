
package org.generation.italy.banca.model.dao;



import java.util.List;

import org.generation.italy.banca.model.BancaModelException;
import org.generation.italy.banca.model.entity.Conto;
import org.generation.italy.banca.model.entity.Movimento;


/**
 * 
 * Classe di verifica dei vincoli applicativi sulle operazioni di scrittura dei dati (TRIGGERS) nelle varie tabelle
 * 
 */
public class Trigger {
	
	public static ClienteDao clienteDao;
	public static ContoDao contoDao;
	public static MovimentoDao movimentoDao;
	
    /**
     * Metodo trigger per la verifica dei vincoli:
     * 1) un movimento di prelievo può essere effettuato su un conto solo se, sommato al saldo corrente, non supera lo scoperto
     * 
     * @param movimento oggetto contenente i dettagli del movimento 
     * @throws BancaModelException 
     */
    public static void checkBeforeInsertMovimento(Movimento movimento) 
    														throws BancaModelException {
        
        //VERIFICA DEL VINCOLO: 
    	//1) un movimento di prelievo può essere effettuato su un conto solo se il suo importo, sottratto al saldo corrente, non supera lo scoperto

    	
    	if (movimento.getTipoOperazione() == "P") {
    		
        	//legge i dati del conto per ottenerne il saldo corrente
        	Conto conto = contoDao.loadContoByPrimaryKey(movimento.getIban());

        	if (Math.abs(conto.getSaldo() - movimento.getImporto()) > conto.getScoperto()) {
        		throw new BancaModelException ("Trigger -> checkBeforeInsertMovimento -> un movimento di prelievo può essere effettuato su un conto solo se, sommato al saldo corrente, non supera lo scoperto!");
        	}
    	}
    	
    } 
    
}
