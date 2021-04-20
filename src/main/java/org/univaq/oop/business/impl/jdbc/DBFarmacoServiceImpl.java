package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.*;
import org.univaq.oop.domain.Farmaco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBFarmacoServiceImpl implements FarmacoService {


    private static final String SELEZIONA_TUTTI_I_FARMACI = "select * from farmaco";
    private static final String SELEZIONA_FARMACO_PER_ID = "select * from farmaco where id=?";
    private static final String SELEZIONA_FARMACO_IN_ESAURIMENTO = "select * from farmaco where in_esaurimento=1";
    private static final String CANCELLA_FARMACO = "delete from farmaco where id=?";
    private static final String INSERISCI_FARMACO = "insert into farmaco ( nome,descrizione,minimo,quantita) values  (?,?,?,?) ;";
    private static final String AGGIORNA_FARMACO_PER_ID = "update farmaco set nome=?,descrizione=?,minimo=?,quantita=?  where id=?";
    private static final String UPDATE_QUANTITY_FARMACO = "update farmaco set quantita=? where id=?";

  @Override
    public List<Farmaco> trovaTuttiFarmaci() throws BusinessException {
        List<Farmaco> ll = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELEZIONA_TUTTI_I_FARMACI);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Farmaco farmaco = new Farmaco();
                farmaco.setId(rs.getLong("id"));
                farmaco.setNome(rs.getString("nome"));
                farmaco.setDescrizione(rs.getString("descrizione"));
                farmaco.setQuantita(rs.getInt("quantita"));
                farmaco.setMinimo(rs.getInt("minimo"));
                farmaco.setInEsaurimento();
                farmaco.setStatoFarmaco();
                ll.add(farmaco);
            }


        } catch (SQLException t) {
            throw new DatabaseException();
        }
        return ll;
    }

    @Override
    public void aggiungiFarmaco(Farmaco farmaco) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERISCI_FARMACO);
            statement.setString(1, farmaco.getNome());
            statement.setString(2, farmaco.getDescrizione());
            statement.setInt(3, farmaco.getMinimo());
            statement.setInt(4, farmaco.getQuantita());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throw new DatabaseException();
        }

    }

    @Override
    public void aggiornaFarmaco(Farmaco farmaco) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(AGGIORNA_FARMACO_PER_ID);
            statement.setString(1, farmaco.getNome());
            statement.setString(2, farmaco.getDescrizione());
            statement.setInt(3, farmaco.getMinimo());
            statement.setInt(4, farmaco.getQuantita());
            statement.setLong(5, farmaco.getId());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throw new DatabaseException();
        }

    }

    @Override
    public Farmaco trovaFarmacoDaId(Integer codice) throws BusinessException {
        Farmaco farmaco = new Farmaco();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELEZIONA_FARMACO_PER_ID);
            statement.setInt(1, codice);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                farmaco.setId(rs.getLong("id"));
                farmaco.setNome(rs.getString("nome"));
                farmaco.setDescrizione(rs.getString("descrizione"));
                farmaco.setQuantita(rs.getInt("quantita"));
                farmaco.setMinimo(rs.getInt("minimo"));
                farmaco.setInEsaurimento();
                farmaco.setStatoFarmaco();
            } else {
                throw new FarmacoNonTrovatoException();
            }
        } catch (SQLException throwables) {
            throw new DatabaseException();
        }
        return farmaco;


    }

    @Override
    public void eliminaFarmaco(Long codice) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CANCELLA_FARMACO);
            statement.setLong(1, codice);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new FarmacoInPrescrizioneException();

        }
    }

    @Override
    public List<Farmaco> trovaFarmaciInEsaurimento() throws BusinessException {
        List<Farmaco> ll = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELEZIONA_FARMACO_IN_ESAURIMENTO);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                Farmaco farmaco = new Farmaco();
                farmaco.setId(rs.getLong("id"));
                farmaco.setNome(rs.getString("nome"));
                farmaco.setDescrizione(rs.getString("descrizione"));
                farmaco.setQuantita(rs.getInt("quantita"));
                farmaco.setMinimo(rs.getInt("minimo"));
                farmaco.setInEsaurimento();
                farmaco.setStatoFarmaco();
                ll.add(farmaco);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ll;
    }
}
