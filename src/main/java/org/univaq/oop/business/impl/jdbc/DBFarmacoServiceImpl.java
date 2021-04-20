package org.univaq.oop.business.impl.jdbc;

import org.univaq.oop.business.BusinessException;
import org.univaq.oop.business.FarmacoInPrescrizioneException;
import org.univaq.oop.business.FarmacoNonTrovato;
import org.univaq.oop.business.FarmacoService;
import org.univaq.oop.domain.Farmaco;
import org.univaq.oop.domain.Prescrizione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBFarmacoServiceImpl implements FarmacoService {

    private static final String SELECT_ALL = "select * from farmaco";
    private static final String SELECT_FARMACO = "select * from farmaco where id=?";
    private static final String SELECT_FARMACO_ESAURIMENTO = "select * from farmaco where in_esaurimento=1";
    private static final String DESTROY_FARMACO = "delete from farmaco where id=?";
    private static final String INSERT_FARMACO = "insert into farmaco ( nome,descrizione,q_min,quantity) values  (?,?,?,?) ;";
    private static final String UPDATE_FARMACO = "update farmaco set nome=?,descrizione=?,q_min=?,quantity=?  where id=?";
    private static final String UPDATE_QUANTITY_FARMACO = "update farmaco set quantity=? where id=?";

    @Override
    public List<Farmaco> trovaTuttiFarmaci() throws BusinessException {
        List<Farmaco> ll = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Farmaco farmaco = new Farmaco();
                farmaco.setId(rs.getLong("id"));
                farmaco.setNome(rs.getString("nome"));
                farmaco.setDescrizione(rs.getString("descrizione"));
                farmaco.setQuantita(rs.getInt("quantity"));
                farmaco.setMinimo(rs.getInt("q_min"));
                farmaco.setOutOfStock();
                farmaco.setStatoFarmaco();
                ll.add(farmaco);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ll;
    }

    @Override
    public void aggiungiFarmaco(Farmaco farmaco) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_FARMACO);
            statement.setString(1, farmaco.getNome());
            statement.setString(2, farmaco.getDescrizione());
            statement.setInt(3, farmaco.getMinimo());
            statement.setInt(4, farmaco.getQuantita());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void aggiornaFarmaco(Farmaco farmaco) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_FARMACO);
            statement.setString(1, farmaco.getNome());
            statement.setString(2, farmaco.getDescrizione());
            statement.setInt(3, farmaco.getMinimo());
            statement.setInt(4, farmaco.getQuantita());
            statement.setLong(5, farmaco.getId());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Farmaco trovaFarmacoDaId(Integer codice) throws BusinessException {
        Farmaco farmaco = new Farmaco();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_FARMACO);
            statement.setInt(1, codice);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                farmaco.setId(rs.getLong("id"));
                farmaco.setNome(rs.getString("nome"));
                farmaco.setDescrizione(rs.getString("descrizione"));
                farmaco.setQuantita(rs.getInt("quantity"));
                farmaco.setMinimo(rs.getInt("q_min"));
                farmaco.setOutOfStock();
                farmaco.setStatoFarmaco();
            } else {
                throw new FarmacoNonTrovato();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return farmaco;


    }

    @Override
    public void eliminaFarmaco(Long codice) throws BusinessException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DESTROY_FARMACO);
            statement.setLong(1, codice);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new FarmacoInPrescrizioneException();

        }
    }

    @Override
    public void aggiornaQtaFarmaco(Prescrizione prescrizione) throws BusinessException {

    }

    @Override
    public List<String> findAllFarmaciByNome() throws BusinessException {
        return null;
    }

    @Override
    public Farmaco findFarmacoByName(String string) throws BusinessException {
        return null;
    }

    @Override
    public List<Farmaco> trovaFarmaciInEsaurimento() throws BusinessException {
        List<Farmaco> ll = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_FARMACO_ESAURIMENTO);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Farmaco farmaco = new Farmaco();
                farmaco.setId(rs.getLong("id"));
                farmaco.setNome(rs.getString("nome"));
                farmaco.setDescrizione(rs.getString("descrizione"));
                farmaco.setQuantita(rs.getInt("quantity"));
                farmaco.setMinimo(rs.getInt("q_min"));
                farmaco.setOutOfStock();
                farmaco.setStatoFarmaco();
                ll.add(farmaco);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ll;
    }
}
