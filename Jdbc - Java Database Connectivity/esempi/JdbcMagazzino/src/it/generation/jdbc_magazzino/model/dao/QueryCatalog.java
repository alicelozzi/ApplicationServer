
package it.generation.jdbc_magazzino.model.dao;


public class QueryCatalog {
    
    //***************** Query per Crud su tabella: cliente **********************
    
    public static final String selectFromClienteByCodiceFiscale=
            "SELECT codice_fiscale, nominativo                  "
            + "FROM cliente                                     "
            + "WHERE cliente.codice_fiscale LIKE ?              ";
    
    public static final String selectFromClienteByNominativo =
            "SELECT codice_fiscale, nominativo          "
            + "FROM cliente WHERE nominativo LIKE ?     ";
    
    
    public static final String insertCliente =
            "INSERT INTO cliente (codice_fiscale, nominativo) VALUES (?, ?) ";
    
    public static final String deleteFromClienteByCodiceFiscale=
             "DELETE FROM cliente WHERE codice_fiscale = ? ";
    
    //******************* Query per Crud su tabella: prodotto **********************
    
    public static final String insertProdotto=
            "INSERT INTO prodotto "
            + "(codice_prodotto, descrizione, quantita_disponibile, prezzo) "
            + "VALUES (?, ?, ?, ?)";
    
    public static final String updateQuantitaDisponibileProdottoByCodiceProdotto=
            "UPDATE prodotto SET quantita_disponibile = ? WHERE codice_prodotto = ?";
    
    public static final String selectFromProdottoByCodiceProdotto=
    		"SELECT * FROM prodotto WHERE prodotto.codice_prodotto LIKE ?";
    
    public static final String selectFromProdottoByDescrizione=
    		"SELECT * FROM prodotto WHERE prodotto.descrizione LIKE ?";

    public static final String selectFromProdottoAll = "SELECT * FROM prodotto ";
    
    public static final String deleteFromProdottoByCodiceProdotto =
             "DELETE FROM magazzino.prodotto WHERE codice_prodotto= ? ";
    
     //**************** Query per Crud su tabella: ordinazione **********************
    
    public static final String insertOrdinazione =
            "INSERT INTO ordinazione "
          + "       (codice_fiscale, codice_prodotto, quantita_ordine, prezzo_acquisto) "
          + "VALUES ( ?, ?, ?, ?)";
    
    public static final String selectFromOrdinazioneByPrimaryKey =
              "  SELECT * FROM ordinazione  "
            + "   WHERE codice_fiscale = ?  "
            + "     AND codice_prodotto = ? "
            + "     AND data_ordine = ?      ";
    
    public static final String selectOrdinazioniWhereCodiceProdotto=
    		"SELECT * FROM ordinazione WHERE cliente.codice_prodotto LIKE ?";
    
    public static final String deleteOrdinazioneWhereData=
             " DELETE FROM ordinazione "
            + " WHERE data_ordine= ? AND codice_fiscale AND codice_prodotto";
     
    //**************** QUERY SELECT per viste (JOIN tra più tabelle)*************
    
    public static final String selectVistaProdottiOrdinazioneClienteByCodiceFiscale =
              " SELECT o.data_ordine, o.quantita_ordine, o.prezzo_acquisto, p.codice_prodotto, p.descrizione, p.quantita_disponibile, p.prezzo "
            + "   FROM ordinazione AS o                                                                         "
            + "                 INNER JOIN cliente AS c ON o.codice_fiscale = c.codice_fiscale                  "
            + "                 INNER JOIN prodotto AS p ON p.codice_prodotto = o.codice_prodotto               "
            + "  WHERE c.codice_fiscale = ?                                                                     ";

    public static final String selectVistaClientiOrdinazioneProdottoByCodiceProdotto =
              " SELECT o.data_ordine, o.quantita_ordine, o.prezzo_acquisto, c.codice_fiscale, c.nominativo  "
            + "   FROM ordinazione AS o                                                                     "
            + "                 INNER JOIN cliente AS c ON o.codice_fiscale = c.codice_fiscale              "
            + "                 INNER JOIN prodotto AS p ON p.codice_prodotto = o.codice_prodotto           "
            + "  WHERE p.codice_prodotto = ?                                                                ";
    
}  
