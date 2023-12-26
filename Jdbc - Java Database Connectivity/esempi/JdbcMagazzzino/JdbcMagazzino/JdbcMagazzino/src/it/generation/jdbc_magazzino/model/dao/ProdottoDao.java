package it.generation.jdbc_magazzino.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.generation.jdbc_magazzino.model.MagazzinoModelConfiguration;
import it.generation.jdbc_magazzino.model.MagazzinoModelException;
import it.generation.jdbc_magazzino.model.entity.Prodotto;

public class ProdottoDao implements IProdottoDao {

    private Connection dbMagazzinoConnection;

    public ProdottoDao() {

        this.dbMagazzinoConnection = (Connection) MagazzinoModelConfiguration.getConfigurationValueFromKey("dbMagazzinoConnection");

    }

    private List<Prodotto> loadProdottiByQuery(PreparedStatement preparedstatement) throws MagazzinoModelException {

        List<Prodotto> elencoClienti = new ArrayList<Prodotto>();

        try {

            ResultSet rsSelect
                    = preparedstatement.executeQuery();

            while (rsSelect.next()) {

                String codProdotto = rsSelect.getString("codice_prodotto");
                if (rsSelect.wasNull()) {
                    codProdotto = "";
                }

                String descrizione = rsSelect.getString("descrizione");
                if (rsSelect.wasNull()) {
                    descrizione = "";
                }

                int quantitaDisponibile = rsSelect.getInt("quantita_disponibile");
                if (rsSelect.wasNull()) {
                    quantitaDisponibile = 0;
                }

                Float prezzo = rsSelect.getFloat("prezzo");
                if (rsSelect.wasNull()) {
                    prezzo = 0.0f;
                }

                Prodotto prodotto = new Prodotto(codProdotto, descrizione, quantitaDisponibile, prezzo);

                elencoClienti.add(prodotto);

            }

        } catch (SQLException sqlException) {

            throw new MagazzinoModelException("ProdottoDao -> loadProdotto -> " + sqlException.getMessage());

        }

        return elencoClienti;

    }

    @Override
    public Prodotto loadProdottoByCodiceProdotto(String codiceProdotto) throws MagazzinoModelException {

        Prodotto prodotto = null;
        
        try {

            List<Prodotto> elencoProdotti = new ArrayList<Prodotto>();
                
            PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectFromProdottoByCodiceProdotto); 

            preparedStatement.setString(1, codiceProdotto);

            elencoProdotti = loadProdottiByQuery(preparedStatement);

            if (elencoProdotti.size() == 1) {
                prodotto = elencoProdotti.get(0);
            }
            
        } catch (SQLException sqlException) {

            throw new MagazzinoModelException("ProdottoDao -> loadProdottoByCodFiscale -> " + sqlException.getMessage());

        } 
        

        return prodotto;

    }

    @Override
    public List<Prodotto> loadProdottiByDescrizione(String descrizione) throws MagazzinoModelException {

        List<Prodotto> elencoProdotto = new ArrayList<Prodotto>();

        try (
                PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectFromProdottoByDescrizione);) {

            preparedStatement.setString(1, "%" + descrizione + "%");

            elencoProdotto = loadProdottiByQuery(preparedStatement);

        } catch (SQLException sqlException) {

            throw new MagazzinoModelException("ProdottoDao -> loadAllProdotti -> " + sqlException.getMessage());
        }

        return elencoProdotto;

    }

    @Override
    public List<Prodotto> loadProdottiAll() throws MagazzinoModelException {

        List<Prodotto> elencoProdotti = new ArrayList<Prodotto>();

        try (PreparedStatement preparedStatement = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.selectFromProdottoAll);) {

            elencoProdotti = loadProdottiByQuery(preparedStatement);

        } catch (SQLException sqlException) {
            throw new MagazzinoModelException("ProdottoDao -> loadProdottiAll -> " + sqlException.getMessage());
        }

        return elencoProdotti;

    }

    @Override
    public void addProdotto(Prodotto prodotto) throws MagazzinoModelException {

        try {

            PreparedStatement preparedStatement = dbMagazzinoConnection.prepareStatement(QueryCatalog.insertProdotto);

            preparedStatement.setString(1, prodotto.getCodiceProdotto());
            preparedStatement.setString(2, prodotto.getDescrizione());
            preparedStatement.setInt(3, prodotto.getQuantitaDisponibile());
            preparedStatement.setFloat(4, prodotto.getPrezzo());

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new MagazzinoModelException("ProdottoDao -> addProdotto -> " + sqlException.getMessage());
        }

    }

    @Override
    public void removeProdotto(String codiceProdotto) throws MagazzinoModelException {

        try (
                PreparedStatement preparedStatemente = this.dbMagazzinoConnection.prepareStatement(QueryCatalog.deleteFromProdottoByCodiceProdotto);) {

            preparedStatemente.setString(1, codiceProdotto);

            preparedStatemente.executeUpdate();
        } catch (SQLException sqlException) {

            throw new MagazzinoModelException("ProdottoDao -> removeProdotto -> " + sqlException.getMessage());

        }

    }

}
